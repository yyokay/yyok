package com.yyok.share.redission.annotation;

import com.yyok.share.redission.configuration.MQConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Target({java.lang.annotation.ElementType.TYPE})
@Documented
@Import(MQConfiguration.class)
@Configuration
public @interface EnableMQ {

}
