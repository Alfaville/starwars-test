package com.kuber.starwarstest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication(scanBasePackages = "com.kuber.starwarstest")
public class StarwarsTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(StarwarsTestApplication.class, args);
	}

}
