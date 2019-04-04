package com.project;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@ImportResource(locations = {"classpath:applicationContext.xml"})//加载xml配置文件
@SpringBootApplication
@MapperScan("com.project.mapper")
public class AdminApplication {


    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class,args);
    }
}
