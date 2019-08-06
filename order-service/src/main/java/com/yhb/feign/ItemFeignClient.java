package com.yhb.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yhb.dao.entity.Item;
import com.yhb.feign.fallback.ItemFeignClientFallback;
import com.yhb.vo.ResponseVO;

@FeignClient(value = "product-service",fallback = ItemFeignClientFallback.class)
public interface ItemFeignClient {
	 @RequestMapping(value = "/item/queryById/{id}", method = RequestMethod.GET)
	 ResponseVO<Item> queryItemById(@PathVariable("id") String id);
}
