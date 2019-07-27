package com.lrowy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.lrowy.dao") //扫描的mapper
@SpringBootApplication
public class LrowyApplication {

    public static void main(String[] args) {
        SpringApplication.run(LrowyApplication.class, args);
    }

}
