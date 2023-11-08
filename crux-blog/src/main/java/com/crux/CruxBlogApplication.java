package com.crux;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author crucistau
 **/
@MapperScan("com.crux.mapper")
@SpringBootApplication
@EnableScheduling
@EnableSwagger2
public class CruxBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(CruxBlogApplication.class, args);
    }
}
