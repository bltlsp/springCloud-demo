package com.yhb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yhb.dao.entity.Order;
import com.yhb.service.IOrderService;
import com.yhb.utils.ResponseUtil;
import com.yhb.vo.ResponseVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "订单服务")
public class OrderContorller {
	@Autowired
	private IOrderService orderService;
	
	@PostMapping(value = "item/{id}")
	@ApiOperation(value = "根据ID获取订单", notes = "-----")
	public ResponseVO<Order> queryOrderById(@PathVariable("id") String id) {
		return ResponseUtil.success(orderService.findById(id));
	}
}
