package com.himalayas.schoolservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication(
        scanBasePackages = {
                "com.himalayas.schoolservice",
                "com.himalayas.securitycommons"
        }
)
@EnableCaching
public class SchoolServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(SchoolServiceApplication.class, args);
  }

}
