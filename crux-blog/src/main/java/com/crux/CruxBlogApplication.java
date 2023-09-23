package com.crux;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author crucistau
 **/
@MapperScan("com.crux.mapper")
@SpringBootApplication
public class CruxBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(CruxBlogApplication.class, args);
    }
}
