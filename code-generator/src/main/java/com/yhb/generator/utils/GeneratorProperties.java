package com.yhb.generator.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "generator")
@Data
public class GeneratorProperties {
	private Boolean toZip;
	private String packageName;
	private String moduleName;
	private String extendName;
	private String author;
	private String tablePrefix;
	private String[] ignorecolumns;
	private List<Template> template;
	@Data
	public static class Template {
		private String templateName;
		private String templatePath;
	}
	private Map<String, String[]> dataType;
}
