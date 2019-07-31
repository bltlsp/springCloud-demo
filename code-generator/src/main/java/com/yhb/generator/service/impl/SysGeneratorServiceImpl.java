package com.yhb.generator.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yhb.generator.mapper.SysGeneratorMapper;
import com.yhb.generator.service.SysGeneratorService;
import com.yhb.generator.utils.GenUtils;
import com.yhb.generator.utils.GeneratorProperties;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author JasonLiu
 */
@Slf4j
@Service
public class SysGeneratorServiceImpl implements SysGeneratorService {
    @Autowired
    private SysGeneratorMapper sysGeneratorMapper;
	@Autowired
	private GeneratorProperties generatorProperties;

    @Override
    public List<Map<String, Object>> queryList(Map<String, Object> map) {
        return sysGeneratorMapper.queryList(map);
    }

    @Override
    public Map<String, String> queryTable(String tableName) {
        return sysGeneratorMapper.queryTable(tableName);
    }

    @Override
    public List<Map<String, String>> queryColumns(String tableName) {
        return sysGeneratorMapper.queryColumns(tableName);
    }

    @Override
    public byte[] generatorCodeToZip(String[] tableNames) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		try (ZipOutputStream zip = new ZipOutputStream(outputStream)) {
            for (String tableName : tableNames) {
                //查询表信息
                Map<String, String> table = queryTable(tableName);
                //查询列信息
                List<Map<String, String>> columns = queryColumns(tableName);
                //生成代码
				GenUtils.generatorCodeZip(generatorProperties, table, columns, zip);
            }
        } catch (IOException e) {
            log.error("generatorCode-error: ", e);
        }
        return outputStream.toByteArray();
    }

	@Override
	public void generatorCodeToFiles(String[] tableNames) {
		try {
			for (String tableName : tableNames) {
				//查询表信息
				Map<String, String> table = queryTable(tableName);
				//查询列信息
				List<Map<String, String>> columns = queryColumns(tableName);
				//生成代码
				GenUtils.generatorCodeFile(generatorProperties, table, columns);
			}
		} catch (Exception e) {
			log.error("generatorCode-error: ", e);
		}
	}

}
