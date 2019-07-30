package com.yhb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.yhb.dao")
@EnableTransactionManagement//开启事务管理
@ServletComponentScan
public class OrderServiceStart extends SpringBootServletInitializer {
	public static void main(String[] args) {
        SpringApplication.run(OrderServiceStart.class,args);
    }
}
