package com.gustavopm1.requestssniffer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class RequestsSnifferApplication {

	public static void main(String[] args) {
		SpringApplication.run(RequestsSnifferApplication.class, args);
	}

}

