package com.himalayas.securitycommons;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EntityScan(basePackages = "com.himalayas.shareddomain.entities")
@EnableCaching
public class SecurityCommonsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityCommonsApplication.class, args);
	}

}
