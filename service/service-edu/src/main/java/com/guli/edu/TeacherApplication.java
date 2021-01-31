package com.guli.edu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Author Alan_
 * @create 2020/12/5 9:48
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.guli"})
@MapperScan("com.guli.edu.mapper")
@EnableDiscoveryClient
@EnableFeignClients
@EnableTransactionManagement
public class TeacherApplication {
    public static void main(String[] args) {
        SpringApplication.run(TeacherApplication.class);
    }
}
