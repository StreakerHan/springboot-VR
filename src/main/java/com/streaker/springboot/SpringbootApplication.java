package com.streaker.springboot;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration(exclude={DruidDataSourceAutoConfigure.class})
@SpringBootApplication
@MapperScan("com.streaker.dao")
@ComponentScan(basePackages = {"com.streaker.shiro","com.streaker.utils","com.streaker.controller","com.streaker.service"})
public class SpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootApplication.class, args);
	}
}
