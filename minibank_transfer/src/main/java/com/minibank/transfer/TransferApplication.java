package com.minibank.transfer;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//TODO : EurekaClient 활성 어노테이션 추가
@EnableEurekaClient
@EnableFeignClients
@SpringBootApplication
public class TransferApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransferApplication.class, args);
	}
}
