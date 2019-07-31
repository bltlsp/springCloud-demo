package com.yhb.generator.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import com.yhb.generator.model.ColumnEntity;
import com.yhb.generator.model.TableEntity;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 代码生成器工具类
 *
 * @Author JasonLiu
 */
@Slf4j
public class GenUtils {
    private GenUtils() {
        throw new IllegalStateException("Utility class");
    }
    /**
     * 生成代码
     */
	public static void generatorCodeZip(GeneratorProperties generatorProperties, Map<String, String> table,
                                     List<Map<String, String>> columns, ZipOutputStream zip) {
		//表信息
		TableEntity tableEntity = new TableEntity();
		//封装模板数据
		Map<String, Object> map = new HashMap<>();
		generatorCode(tableEntity, map, generatorProperties, table, columns);
		VelocityContext context = new VelocityContext(map);
		//获取模板列表
		List<GeneratorProperties.Template> templates = generatorProperties.getTemplate();
		for (GeneratorProperties.Template template : templates) {
			//渲染模板
			try (StringWriter sw = new StringWriter()) {
				Template tpl = Velocity.getTemplate("template/" + template.getTemplateName(), "UTF-8");
				tpl.merge(context, sw);
				//添加到zip
				//zip.putNextEntry(new ZipEntry(getAllFileName(template, tableEntity.getClassName(), config.getString(PACKAGE), config.getString(MODULE_NAME), config.getString(EXTEND_NAME))));
				zip.putNextEntry(new ZipEntry(getZipFileName(template, tableEntity.getClassName())));
				IOUtils.write(sw.toString(), zip, StandardCharsets.UTF_8);
				zip.closeEntry();
			} catch (IOException e) {
				log.error("generatorCode-error", e);
			}
		}
	}

	/**
	 * 生成代码
	 */
	public static void generatorCodeFile(GeneratorProperties generatorProperties, Map<String, String> table,
			List<Map<String, String>> columns) {
		//表信息
		TableEntity tableEntity = new TableEntity();
		//封装模板数据
		Map<String, Object> map = new HashMap<>();
		generatorCode(tableEntity, map, generatorProperties, table, columns);
		VelocityContext context = new VelocityContext(map);
		//获取模板列表
		List<GeneratorProperties.Template> templates = generatorProperties.getTemplate();
		for (GeneratorProperties.Template template : templates) {
			//渲染模板
			try (StringWriter sw = new StringWriter()) {
				Template tpl = Velocity.getTemplate("template/" + template.getTemplateName(), "UTF-8");
				tpl.merge(context, sw);

				String filePath = System.getProperty("user.dir");
				if (StringUtils.isNotBlank(template.getTemplatePath())) {
					filePath += File.separator + template.getTemplatePath().replace(".", File.separator);
				}
				String fileName = File.separator +getFileName(generatorProperties, template, tableEntity.getClassName());
				File file = new File(filePath+fileName);
				Path path = Paths.get(filePath);
				// 判断目录是否存在
				if(!Files.exists(path)){
					Files.createDirectories(path);
				}
				OutputStream outputStream = new FileOutputStream(file);
				IOUtils.write(sw.toString(), outputStream, StandardCharsets.UTF_8);
				outputStream.close();

			} catch (IOException e) {
				log.error("generatorCode-error", e);
			}
		}
	}

	public static void generatorCode(TableEntity tableEntity, Map<String, Object> map,
			GeneratorProperties generatorProperties, Map<String, String> table, List<Map<String, String>> columns) {
		boolean hasBigDecimal = false;
		boolean hasDate = false;
		boolean hasCreateUpdateDate = false;
		tableEntity.setTableName(table.get("tableName"));
		tableEntity.setComments(table.get("tableComment"));
		//表名转换成Java类名
		String className = tableToJava(tableEntity.getTableName(), generatorProperties.getTablePrefix());
		tableEntity.setClassName(className);
		tableEntity.setClassname(StringUtils.uncapitalize(className));

		//列信息
		List<ColumnEntity> allColumsList = new ArrayList<>();
		List<ColumnEntity> columsList = new ArrayList<>();
		for (Map<String, String> column : columns) {
			String[] ignorecolumns = generatorProperties.getIgnorecolumns();
			boolean isSet=true;
			if(ignorecolumns!=null){
				for(String igorecolumn:ignorecolumns){
					if(column.get("columnName").equals(igorecolumn)){
						isSet=false;
						break;
					}
				}
			}
			ColumnEntity columnEntity = new ColumnEntity();
			columnEntity.setColumnName(column.get("columnName"));
			columnEntity.setDataType(column.get("dataType"));
			columnEntity.setComments(column.get("columnComment"));
			columnEntity.setExtra(column.get("extra"));

			//列名转换成Java属性名
			String attrName = columnToJava(columnEntity.getColumnName());
			columnEntity.setAttrName(attrName);
			columnEntity.setAttrname(StringUtils.uncapitalize(attrName));

			//列的数据类型，转换成Java类型
			String[] configTypes = generatorProperties.getDataType().get(columnEntity.getDataType());
			String attrType=configTypes[0];
			String jdbcType=configTypes[1];
			columnEntity.setAttrType(attrType);
			columnEntity.setJdbcType(jdbcType);
			if(ignorecolumns!=null) {
				if (!hasBigDecimal && attrType.equals("BigDecimal")) {
					hasBigDecimal = true;
				} else if (!hasCreateUpdateDate && (column.get("columnName").equals("create_date") || column.get("columnName").equals("update_date"))) {
					hasCreateUpdateDate = true;
				} else if (!hasDate && attrType.equals("Date") && !column.get("columnName").equals("create_date") && !column.get("columnName").equals("update_date")) {
					hasDate = true;
				}
			}
			//是否主键
			if ("PRI".equalsIgnoreCase(column.get("columnKey")) && tableEntity.getPk() == null) {
				tableEntity.setPk(columnEntity);
			}
			if(isSet){
				columsList.add(columnEntity);
			}
			allColumsList.add(columnEntity);
		}
		tableEntity.setColumns(columsList);
		tableEntity.setAllColumns(allColumsList);
		//没主键，则第一个字段为主键
		if (tableEntity.getPk() == null) {
			tableEntity.setPk(tableEntity.getColumns().get(0));
		}

		//设置velocity资源加载器
		Properties prop = new Properties();
		prop.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		Velocity.init(prop);
		//判断参数
		map.put("hasBigDecimal", hasBigDecimal);
		map.put("hasCreateUpdateDate", hasCreateUpdateDate);
		map.put("hasDate", hasDate);
		//表参数
		map.put("tableName", tableEntity.getTableName());
		map.put("comments", tableEntity.getComments());
		map.put("pk", tableEntity.getPk());
		map.put("className", tableEntity.getClassName());
		map.put("classname", tableEntity.getClassname());
		map.put("allColumns", tableEntity.getAllColumns());
		map.put("columns", tableEntity.getColumns());
		map.put("packageName", generatorProperties.getPackageName());
		map.put("moduleName", generatorProperties.getModuleName());
		map.put("extendName", generatorProperties.getExtendName());
		//注释参数
		Properties props = System.getProperties();
		String userName=props.getProperty("user.name");
		map.put("author", StringUtils.isNotBlank(userName) ? userName : generatorProperties.getAuthor());
		map.put("datetime", DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
	}
    /**
     * 列名转换成Java属性名
     */
    public static String columnToJava(String columnName) {
        return WordUtils.capitalizeFully(columnName, new char[]{'_'}).replace("_", "");
    }

    /**
     * 表名转换成Java类名
     */
    public static String tableToJava(String tableName, String tablePrefix) {
        if (StringUtils.isNotBlank(tablePrefix)) {
            tableName = tableName.substring(tablePrefix.length());
        }
        return columnToJava(tableName);
    }

	/**
	 * 获取文件名
	 */
	public static String getFileName(GeneratorProperties generatorProperties, GeneratorProperties.Template template,
			String className) {
		if (template.getTemplateName().contains("Model.java")) {
			return className + ".java";
		} else if (template.getTemplateName().contains("Dao.java")) {
			return className + "Dao.java";
		} else if (template.getTemplateName().contains("Service.java")) {
			return "I" + className + "Service.java";
		} else if (template.getTemplateName().contains("ServiceImpl.java")) {
			return className + "ServiceImpl.java";
		} else if (template.getTemplateName().contains("Dao.xml")) {
			return className + "Dao.xml";
		} else if (template.getTemplateName().contains("Controller.java")) {
			return className + "Controller.java";
		}
		return null;
	}

    /**
     * 获取文件名
     */
	public static String getZipFileName(GeneratorProperties.Template template, String className) {
		if (template.getTemplateName().contains("Model.java")) {
			return "model" + File.separator + className + ".java";
		} else if (template.getTemplateName().contains("Dao.java")) {
			return "dao" + File.separator + className + "Dao.java";
		} else if (template.getTemplateName().contains("Service.java")) {
			return "service" + File.separator + "I" + className + "Service.java";
		} else if (template.getTemplateName().contains("ServiceImpl.java")) {
			return "service" + File.separator + "impl" + File.separator + className + "ServiceImpl.java";
		} else if (template.getTemplateName().contains("Dao.xml")) {
			return "mapperXml" + File.separator + className + "Dao.xml";
		} else if (template.getTemplateName().contains("Controller.java")) {
			return "controller" + File.separator + className + "Controller.java";
		}
        return null;
    }
}
