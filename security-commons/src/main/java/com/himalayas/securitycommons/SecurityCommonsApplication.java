package com.himalayas.securitycommons;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SecurityCommonsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityCommonsApplication.class, args);
	}

}
