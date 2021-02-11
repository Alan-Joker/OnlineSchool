package com.guli.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author Alan_
 * @create 2021/2/8 18:02
 */
@SpringBootApplication
@MapperScan("com.guli.user.mapper")
@ComponentScan("com.guli")
public class UcenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(UcenterApplication.class,args);
    }
}
