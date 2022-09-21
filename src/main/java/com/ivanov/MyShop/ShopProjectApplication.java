package com.ivanov.MyShop;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;

@SpringBootApplication

public class ShopProjectApplication {



	@Bean
	public MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		factory.setMaxFileSize(DataSize.parse("10MB"));
		factory.setMaxRequestSize(DataSize.parse("10MB"));
		return factory.createMultipartConfig();

	}


	public static void main(String[] args) {
		SpringApplication.run(ShopProjectApplication.class, args);
	}

}
