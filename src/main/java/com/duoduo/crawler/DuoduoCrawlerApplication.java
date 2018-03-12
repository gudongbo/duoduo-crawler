package com.duoduo.crawler;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan(basePackages = "com.duoduo.crawler.dao", markerInterface = MyMapper.class)
public class DuoduoCrawlerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DuoduoCrawlerApplication.class, args);
	}
}
