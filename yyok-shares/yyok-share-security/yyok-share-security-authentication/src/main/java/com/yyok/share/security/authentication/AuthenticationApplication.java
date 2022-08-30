package com.yyok.share.security.authentication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 认证服务
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.yyok")
public class AuthenticationApplication {

    public static void main(String[] args) {

        SpringApplication.run(AuthenticationApplication.class, args);
    }

}
