package com.yyok.share.admin.quartz.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author yyok
 * @date 2020-05-13
 */

@Data
@TableName("sys_quartz_job")
public class QuartzJob implements Serializable {

    public static final String JOB_KEY = "JOB_KEY";

    /**
     * 定时任务ID
     */
    @TableId
    private String coder;


    /**
     * Spring Bean名称
     */
    private String beanName;


    /**
     * cron 表达式
     */
    private String cronExpression;


    /**
     * 状态：1暂停、0启用
     */
    private Boolean isPause;


    /**
     * 任务名称
     */
    private String jobName;


    /**
     * 方法名称
     */
    private String methodName;

    /**
     * 参数
     */
    private String paramIn;

    /**
     * 参数
     */
    private String paramOut;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Timestamp createTime;

    public void copy(QuartzJob source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
