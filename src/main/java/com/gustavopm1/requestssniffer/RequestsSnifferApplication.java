package com.gustavopm1.requestssniffer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableFeignClients
@SpringBootApplication
@EnableAsync
public class RequestsSnifferApplication {

	public static void main(String[] args) {
		SpringApplication.run(RequestsSnifferApplication.class, args);
	}

}

