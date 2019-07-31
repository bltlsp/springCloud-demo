package com.yhb.generator.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yhb.generator.service.SysGeneratorService;
import com.yhb.generator.utils.GeneratorProperties;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @Author: JasonLiu
 */
@RestController
@Api(tags = "代码生成器")
@RequestMapping("/com/kmhealthcloud/generator")
public class SysGeneratorController extends BaseController {
	@Autowired
	private SysGeneratorService sysGeneratorService;
	@Autowired
	private GeneratorProperties generatorProperties;

	@GetMapping("/code")
	@ApiOperation("生成代码FileUtil")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "tables", value = "表名(多表英文,隔开)", required = false, paramType = "query", dataType = "string")})
	public String makeCode(@RequestParam(required = false) String tables, HttpServletResponse response) {
		String msg="失败";
		try {
			if(generatorProperties.getToZip()){
				byte[] data = sysGeneratorService.generatorCodeToZip(tables.split(","));
				response.reset();
				response.setHeader("Content-Disposition", "attachment; filename=\"generator.zip\"");
				response.addHeader("Content-Length", "" + data.length);
				response.setContentType("application/octet-stream; charset=UTF-8");
				IOUtils.write(data, response.getOutputStream());
			}else{
				sysGeneratorService.generatorCodeToFiles(tables.split(","));
			}
			msg = "代码已生成";
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return msg;
	}
}
