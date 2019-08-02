package com.yhb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.yhb.dao")
@EnableTransactionManagement//开启事务管理
@ServletComponentScan
public class ProductServiceStart {
	public static void main(String[] args) {
        SpringApplication.run(ProductServiceStart.class,args);
    }
}
