package com.yyok;

import io.swagger.annotations.Api;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * @author yyok
 * @date 2022/11/15 9:20:19
 */
@SpringBootApplication
@Api(tags = "系统：系统启管理")
public class YYOKApplicationClientBootstrap {

    public static void main(String[] args) throws Exception{

        SpringApplication.run(YYOKApplicationClientBootstrap.class, args);
    }

}
