package com.yhb.feign.fallback;

import org.springframework.stereotype.Component;

import com.yhb.dao.entity.Item;
import com.yhb.feign.ItemFeignClient;
import com.yhb.vo.ResponseVO;

@Component
public class ItemFeignClientFallback implements ItemFeignClient {

	@Override
	public ResponseVO<Item> queryItemById(String id) {
		ResponseVO<Item> response = new ResponseVO<Item>();
		response.setCode("1001");
		response.setData(null);
		response.setMsg("服务异常");
		return response;
	}

}
