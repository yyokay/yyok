package com.yyok;

import com.yyok.share.framework.annotation.AnonymousAccess;
import com.yyok.share.framework.utils.SpringContextHolder;
import io.swagger.annotations.Api;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author yyok
 * @date 2022/11/15 9:20:19
 */
@EnableAsync
@RestController
@SpringBootApplication
@EnableTransactionManagement
@CrossOrigin
@MapperScan(basePackages = {"com.yyok.share.admin.*.service.mapper", "com.yyok.share.*.service.mapper", "com.yyok.share.*.config"})
@Api(tags = "系统：系统启管理")
public class YYOKApplicationBootstrap {

    public static void main(String[] args) throws Exception{

        ApplicationContext application = SpringApplication.run(YYOKApplicationBootstrap.class, args);

        Environment env = application.getEnvironment();
        String ip;
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        String port = env.getProperty("server.port");
        String appName = env.getProperty("spring.application.name");
        String path = env.getProperty("server.servlet.context-path");

        String msg = "\n---------------------------------------------------------------------------"
                + "\n\tApplication "+ appName + " is running! Access URLs:"
                + "\n\tLocal: \t\thttp://localhost:" + port
                + "\n\tExternal: \thttp://" + ip + ":" + port
                + "\n\tDocs: \thttp://" + ip + ":" + port+"/doc.html"
                + "\n-----------------------------------启动成功------------------------------------"
                ;
        System.out.println(msg);
    }

    @Bean("springContextHolders")
    @Primary
    public SpringContextHolder springContextHolders() {
        return new SpringContextHolder();
    }

    @Bean
    public ServletWebServerFactory webServerFactory() {
        TomcatServletWebServerFactory fa = new TomcatServletWebServerFactory();
        fa.addConnectorCustomizers(
                connector -> connector.setProperty("relaxedQueryChars", "[]{}")
        );
        return fa;
    }

    /**
     * 访问首页提示
     * @return /
     */
    @GetMapping("/")
    @AnonymousAccess
    public String index() throws IOException {
        return "Backend service started successfully";
    }
}
