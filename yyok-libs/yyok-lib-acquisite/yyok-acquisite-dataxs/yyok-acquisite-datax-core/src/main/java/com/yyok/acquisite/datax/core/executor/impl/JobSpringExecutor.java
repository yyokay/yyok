package com.yyok.acquisite.datax.core.executor.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.yyok.acquisite.datax.core.executor.JobExecutor;
import com.yyok.acquisite.datax.core.glue.GlueFactory;
import com.yyok.acquisite.datax.core.handler.IJobHandler;
import com.yyok.acquisite.datax.core.handler.annotation.JobHandler;
import com.yyok.acquisite.datax.core.executor.JobExecutor;
import com.yyok.acquisite.datax.core.glue.GlueFactory;
import com.yyok.acquisite.datax.core.handler.IJobHandler;
import com.yyok.acquisite.datax.core.handler.annotation.JobHandler;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Map;

/**
 * xxl-job executor (for spring)
 *
 */
public class JobSpringExecutor extends JobExecutor implements ApplicationContextAware, SmartInitializingSingleton, DisposableBean {


    // start
    @Override
    public void afterSingletonsInstantiated() {

        // init JobHandler Repository
        initJobHandlerRepository(applicationContext);

        // refresh GlueFactory
        GlueFactory.refreshInstance(1);


        // super start
        try {
            super.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // destroy
    @Override
    public void destroy() {
        super.destroy();
    }

    private void initJobHandlerRepository(ApplicationContext applicationContext) {
        if (applicationContext == null) {
            return;
        }

        // init job handler action
        Map<String, Object> serviceBeanMap = applicationContext.getBeansWithAnnotation(JobHandler.class);

        if (CollectionUtil.isNotEmpty(serviceBeanMap)) {
            for (Object serviceBean : serviceBeanMap.values()) {
                if (serviceBean instanceof IJobHandler) {
                    String name = serviceBean.getClass().getAnnotation(JobHandler.class).value();
                    IJobHandler handler = (IJobHandler) serviceBean;
                    if (loadJobHandler(name) != null) {
                        throw new RuntimeException("datax-web jobhandler[" + name + "] naming conflicts.");
                    }
                    registJobHandler(name, handler);
                }
            }
        }
    }

    // ---------------------- applicationContext ----------------------
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        JobSpringExecutor.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

}
