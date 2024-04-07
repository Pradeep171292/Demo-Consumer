package com.boa.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
@ComponentScan("com.boa.*")
//@EnableSwagger2
@EnableDiscoveryClient
public class BoaConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoaConsumerApplication.class, args);
	}
	/**
	 * @return
	 * Enables us to use swagger for application
	 */
	/*
	 * @Bean public Docket apiDocket() { return new
	 * Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any
	 * ()) .paths(PathSelectors.any()).build(); }
	 */
}
