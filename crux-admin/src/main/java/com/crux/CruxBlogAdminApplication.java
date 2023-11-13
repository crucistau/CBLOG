package com.crux;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author crucistau
 **/
@SpringBootApplication
@MapperScan("com.crux.mapper")
public class CruxBlogAdminApplication {
    public static void main(String[] args){
        SpringApplication.run(CruxBlogAdminApplication.class, args);
    }
}
