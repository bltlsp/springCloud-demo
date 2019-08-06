package com.yhb.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yhb.dao.entity.Order;
import com.yhb.feign.ItemFeignClient;
import com.yhb.service.ItemService;
import com.yhb.service.OrderService;
import com.yhb.utils.QueryUtiL;
import com.yhb.utils.ResponseUtil;
import com.yhb.vo.QueryVO;
import com.yhb.vo.ResponseVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "订单服务")
@RequestMapping("order")
public class OrderContorller {
	@Autowired
	private OrderService orderService;
	@Autowired
	private ItemService itemService;
	@Autowired
	private ItemFeignClient itemFeignClient;
	
	private Logger logger = LoggerFactory.getLogger(OrderContorller.class);
	
	
	@PostMapping(value = "queryById/{id}")
	@ApiOperation(value = "根据ID获取订单", notes = "-----")
	public Order queryOrderById(@PathVariable("id") String id) {
		Order order = orderService.findById(id);
		order.setItem(itemFeignClient.queryItemById("111").getData());
		return order;
	}
	
	
	/*@PostMapping(value = "queryById/{id}")
	@ApiOperation(value = "根据ID获取订单", notes = "-----")
	public ResponseVO<Order> queryOrderById(@PathVariable("id") String id) {
		Order order = orderService.findById(id);
		order.setItem(itemService.queryItemById("111"));
		return ResponseUtil.success(order);
	}*/
	
	/*@PostMapping(value = "addOrUpdate")
	@ApiOperation(value = "根据ID获取订单", notes = "-----")
	public ResponseVO<List<Order>> addOrUpdateOrder(@RequestBody QueryVO<Order> queryVo) {
		List<Order> list = null;
		try {
			list = orderService.findByParams(QueryUtiL.convertMap(queryVo), queryVo.getOrderBy(), queryVo.getPageNum(), queryVo.getPageSize());
		} catch (Exception e) {
			logger.error("查询异常:", e);
		} 
		return ResponseUtil.success(list);
	}
	
	
	@PostMapping(value = "delById/{id}")
	@ApiOperation(value = "根据ID删除订单", notes = "-----")
	public ResponseVO<Order> delById(@PathVariable("id") String id) {
		return ResponseUtil.success(orderService.findById(id));
	}*/
}
