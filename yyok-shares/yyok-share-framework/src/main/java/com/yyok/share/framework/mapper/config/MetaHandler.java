package com.yyok.share.framework.mapper.config;

/**
 * @author ：Linqinghong
 * @date ：Created in 2020-05-10
 * @description：自动注入时间处理
 * @modified By：
 * @version:
 */

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.google.gson.Gson;
import com.yyok.share.framework.utils.CodeUtil;
import com.yyok.share.framework.utils.SecurityUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Optional;

/**
 * 处理新增和更新的基础数据填充，配合BaseEntity和MyBatisPlusConfig使用
 */
@Slf4j
@Component
@AllArgsConstructor
public class MetaHandler implements MetaObjectHandler {

    private final KafkaTemplate kafkaTemplate;

    /**
     * 新增数据执行
     *
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        try {
            Timestamp time = new Timestamp(System.currentTimeMillis());
            if (metaObject.hasSetter("createTime")) {
                log.debug("自动插入 createTime");
                this.setFieldValByName("createTime", time, metaObject);
            }
         /*   if (metaObject.hasSetter("updateTime")) {
                log.debug("自动插入 updateTime");
                this.setFieldValByName("updateTime", time, metaObject);
            }
*/
            if (metaObject.hasSetter("createBy")) {
                log.debug("自动插入 createBy");
                if (metaObject.getValue("createBy") == null) {
                    metaObject.setValue("createBy", getCurrentUser(metaObject));
                }
                this.setFieldValByName("createBy", getCurrentUser(metaObject), metaObject);
            }
            /*if (metaObject.hasSetter("updateBy")) {
                log.debug("自动插入 updateBy");
                if(metaObject.getValue("updateBy")==null){
                    metaObject.setValue("updateBy", getCurrentUser(metaObject));
                }
                this.setFieldValByName("updateBy", metaObject.getValue("updateBy"), metaObject);
            }*/

            if (metaObject.hasSetter("groupBy")) {
                log.debug("自动插入 groupBy");
                if (metaObject.getValue("groupBy") == null)
                    this.setFieldValByName("groupBy", "", metaObject);
            }

            if (metaObject.hasSetter("tenantBy")) {
                log.debug("自动插入 tenantBy");
                if (metaObject.getValue("tenantBy") == null)
                    this.setFieldValByName("tenantBy", metaObject.getValue("tenantBy"), metaObject);
            }

            if (metaObject.hasSetter("systemBy")) {
                log.debug("自动插入 systemBy");
                if (metaObject.getValue("systemBy") == null)
                    this.setFieldValByName("systemBy", metaObject.getValue("systemBy"), metaObject);
            }

            if (metaObject.hasSetter("coder")) {
                log.debug("自动插入 coder");
                this.setFieldValByName("coder", CodeUtil.md5dh16CtmUuid(), metaObject);
            }

            if (metaObject.hasSetter("isDel")) {
                log.debug("自动插入 isDel");
                if (metaObject.getValue("isDel") == null)
                    this.setFieldValByName("isDel", metaObject.getValue("isDel"), metaObject);
            }

            if (metaObject.hasSetter("typer")) {
                log.debug("自动插入 typer");
                if (metaObject.getValue("typer") == null)
                    this.setFieldValByName("typer", metaObject.getValue("typer"), metaObject);
            }

            if (metaObject.hasSetter("remark")) {
                log.debug("自动插入 remark");
                this.setFieldValByName("remark", metaObject.getValue("remark"), metaObject);
            }

            if (metaObject.hasSetter("status")) {
                log.debug("自动插入 status");
                if (metaObject.getValue("status") == null)
                    this.setFieldValByName("status", metaObject.getValue("status"), metaObject);
            }

            if (metaObject.hasSetter("enabled")) {
                log.debug("自动插入 enabled");
                if (metaObject.getValue("enabled") == null)
                    this.setFieldValByName("enabled", metaObject.getValue("enabled"), metaObject);
            }

            if (metaObject.hasSetter("sorter")) {
                log.debug("自动插入 sorter");
                if (metaObject.getValue("sorter") == null)
                    this.setFieldValByName("sorter", metaObject.getValue("sorter"), metaObject);
            }

            if (metaObject.hasSetter("level")) {
                log.debug("自动插入 level");
                if (metaObject.getValue("level") == null)
                    this.setFieldValByName("level", metaObject.getValue("level"), metaObject);
            }

        } catch (Exception e) {
            log.error("自动注入失败:{}", e);
        }finally {
            kafkaTemplate.send("logs",new Gson().toJson(metaObject.getOriginalObject()));
        }
    }

    /**
     * 更新数据执行
     *
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        try {
            Timestamp time = new Timestamp(System.currentTimeMillis());

            if (metaObject.hasSetter("updateTime")) {
                log.debug("自动插入 updateTime");
                this.setFieldValByName("updateTime", time, metaObject);
            }

            if (metaObject.hasSetter("updateBy")) {
                log.debug("自动插入 updateBy");
                this.setFieldValByName("updateBy", getCurrentUser(metaObject), metaObject);
            }

        } catch (Exception e) {
            log.error("自动注入失败:{}", e);
        }
    }


    private String getCurrentUser(MetaObject metaObject) {
        String admin = "";
        try {
            admin = SecurityUtils.getAccountCode();
        } catch (Exception e) {
            //e.printStackTrace();
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (Optional.ofNullable(authentication).isPresent()) {
                admin = authentication.getPrincipal().toString();
                return admin;
            }else {
                log.error(new Timestamp(System.currentTimeMillis()) + "-- java.lang.NullPointerException MetaHandler.java Line 98");
                return "no admin";
            }

        }
        return admin;
    }

}
