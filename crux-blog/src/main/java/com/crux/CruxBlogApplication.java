package com.crux;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

/**
 * @author crucistau
 **/
@MapperScan("com.crux.mapper")
@SpringBootApplication
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class CruxBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(CruxBlogApplication.class, args);
    }
}
