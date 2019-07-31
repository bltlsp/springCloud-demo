package com.yhb.generator.controller;

import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class BaseController {
	/**
	 * 列表排序处理
	 * @param request
	 * @param deforderByStr 默认排序字段 如果前端设置了默认值，以前端为准
	 * @param deforderBy 默认排序标识
	 */
	public String getPageHelperOrderBy(HttpServletRequest request, String deforderByStr, String deforderBy) {
		String sorts[];
		String orders[];
		if (StringUtils.isNotBlank(request.getParameter("sort")) && StringUtils.isNotBlank(request.getParameter("order"))) {
			sorts = request.getParameter("sort").split(",");
			orders = request.getParameter("order").split(",");
		} else {
			sorts = deforderByStr.split(",");
			orders = deforderBy.split(",");
		}
		String orderByStr = "";
		for (int i = 0; i < sorts.length; i++) {
			orderByStr += sorts[i] + " " + orders[i];
			if (i < (sorts.length - 1)) {
				orderByStr += ",";
			}
		}
		return orderByStr;
	}
}
