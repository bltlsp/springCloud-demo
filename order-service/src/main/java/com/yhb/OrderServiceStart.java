package com.yhb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.spring4all.swagger.EnableSwagger2Doc;

import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.yhb.dao")
@EnableTransactionManagement//开启事务管理
@ServletComponentScan
@EnableSwagger2Doc
public class OrderServiceStart extends SpringBootServletInitializer {
	public static void main(String[] args) {
        SpringApplication.run(OrderServiceStart.class,args);
      //按照关键字匹配度排序
		//name:*  name:*金银*^10 
		//String condition = " ( name:* name:*"+prodNew.getSortName()+"*^10) ";
    }
}
