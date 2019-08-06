package com.yhb.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yhb.dao.entity.Item;
import com.yhb.service.ItemService;
import com.yhb.utils.ResponseUtil;
import com.yhb.vo.ResponseVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "商品服务")
@RequestMapping("item")
public class ItemController {
	@Autowired
	private ItemService itemService;
	
	private Logger logger = LoggerFactory.getLogger(ItemController.class);
	
	@GetMapping(value = "queryById/{id}")
	@ApiOperation(value = "根据ID获取订单", notes = "-----")
	public ResponseVO<Item> queryOrderById(@PathVariable("id") String id) {
		return ResponseUtil.success(itemService.findById(id));
	}
}
