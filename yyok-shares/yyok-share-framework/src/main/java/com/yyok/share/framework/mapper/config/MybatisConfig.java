package com.yyok.share.framework.mapper.config;

/**
 * @author ：Linqinghong
 * @date ：Created in 2020-04-10 15:11
 * @description：MybatisConfig
 * @modified By：
 * @version:
 */

import com.github.pagehelper.PageHelper;
import com.yyok.share.framework.mapper.datascope.aspectj.DataScopeAspect;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Properties;

@Configuration
@MapperScan("com.yyok.**.**.service.mapper")
@EnableTransactionManagement
public class MybatisConfig {
    /**
     * 配置mybatis的分页插件pageHelper
     *
     * @return
     */
    @Bean
    public PageHelper pageHelper() {
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("offsetAsPageNum", "true");
        properties.setProperty("rowBoundsWithCount", "true");
        properties.setProperty("reasonable", "true");
        //配置mysql数据库的方言
        properties.setProperty("dialect", "mysql");
        pageHelper.setProperties(properties);
        return pageHelper;
    }

    /**
     * 分页插件
     *
     * @return PaginationInterceptor
     */
    //@Bean
    //public PaginationInterceptor paginationInterceptor() {
    //    return new PaginationInterceptor();
    //}

    /**
     * 数据权限插件
     *
     * @return DataScopeInterceptor
     */
   /* @Bean
    public DataScopeInterceptor dataScopeInterceptor() {
        return new DataScopeInterceptor();
    }*/

    /**
     * 数据过滤处理（基于注解式）
     *
     * @return dataScopeAspect
     */
    @Bean
    public DataScopeAspect dataScopeAspect() {
        return new DataScopeAspect();
    }

    /**
     * 自定义 SqlInjector
     * 里面包含自定义的全局方法|q
     */
    //@Bean
    // public DataLogicSqlInjector myLogicSqlInjector() {
    //    return new DataLogicSqlInjector();
    //}

}
