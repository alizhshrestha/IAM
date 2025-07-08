package com.himalayas.schoolservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication(
        scanBasePackages = {
                "com.himalayas.schoolservice",
                "com.himalayas.securitycommons"
        }
)
@MapperScan("com.himalayas.schoolservice.mapper")
@EnableCaching
@EntityScan(basePackages = "com.himalayas.shareddomain.entities")
public class SchoolServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(SchoolServiceApplication.class, args);
  }

}
