package com.himalayas.shareddomain;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.himalayas.shareddomain.mapper")
public class SharedDomainApplication {

  public static void main(String[] args) {
    SpringApplication.run(SharedDomainApplication.class, args);
  }

}
