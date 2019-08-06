package com.yhb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

import com.spring4all.swagger.EnableSwagger2Doc;

import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.yhb.dao")
@EnableTransactionManagement//开启事务管理
@ServletComponentScan
@EnableSwagger2Doc
@EnableEurekaClient
@EnableHystrix
@EnableFeignClients(basePackages ="com.yhb.feign")
public class OrderServiceStart extends SpringBootServletInitializer {
	public static void main(String[] args) {
        SpringApplication.run(OrderServiceStart.class,args);
      //按照关键字匹配度排序
		//name:*  name:*金银*^10 
		//String condition = " ( name:* name:*"+prodNew.getSortName()+"*^10) ";
    }
	
	@Bean
	@LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate(new OkHttp3ClientHttpRequestFactory());
    }
}
