package com.yhb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.yhb.dao.entity.Item;
import com.yhb.feign.ItemFeignClient;
import com.yhb.vo.ResponseVO;
@Service
public class ItemService {
	@Autowired
	private ItemFeignClient itemFeignClient;
	@Autowired
    private RestTemplate restTemplate;
	@Value("${myspcloud.item.url}")
	private String itemUrl;

	@HystrixCommand(fallbackMethod = "queryItemByIdFallbackMethod")
    public ResponseVO<Item> queryItemById(String id) {
		return itemFeignClient.queryItemById(id);
    	/*String url = "http://product-service/product-service/item/queryById/{id}";
         return this.restTemplate.getForObject(url, Item.class, id);*/
         
         /**
          * 
          * String json = "[{},...]";
Type type= new TypeReference <ESResponse<Model>>() {}.getType();
ESResponse<Model> respionse= JSON.parseObject(json, type);
          */
    }
	public Item queryItemByIdFallbackMethod(String id) {
	    Item item =  new Item();
	    item.setName("服务异常");
	    return item;
	}
}
