package com.yyok.share.framework.dozer.config;

import org.dozer.spring.DozerBeanMapperFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

/**
 * @author ：Linqinghong
 * @date ：Created in 2022/05/09 10:30
 * @description：Dozer转换
 * @modified By：
 * @version: 1.0
 */
@Configuration
public class DozerMapperConfig {
    @Bean
    public DozerBeanMapperFactoryBean dozerBeanMapperFactoryBean(@Value("classpath*:dozer/*.xml") Resource[] resources) throws Exception {
        final DozerBeanMapperFactoryBean dozerBeanMapperFactoryBean = new DozerBeanMapperFactoryBean();
        dozerBeanMapperFactoryBean.setMappingFiles(resources);
        return dozerBeanMapperFactoryBean;
    }

 /*   @Bean
    public IGenerator ejbGenerator() {
        return new EJBGenerator();
    }*/
}
