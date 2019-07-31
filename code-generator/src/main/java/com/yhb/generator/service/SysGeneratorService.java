package com.yhb.generator.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author JasonLiu
 */
@Service
public interface SysGeneratorService {
	List<Map<String, Object>> queryList(Map<String, Object> map);

	Map<String, String> queryTable(String tableName);

	List<Map<String, String>> queryColumns(String tableName);

	byte[] generatorCodeToZip(String[] tableNames);

	void generatorCodeToFiles(String[] tableNames);
}
