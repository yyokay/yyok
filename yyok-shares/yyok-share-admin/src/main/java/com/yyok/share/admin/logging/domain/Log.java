package com.yyok.share.admin.logging.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yyok.share.framework.domain.Domain;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yyok
 * @date 2022-05-24
 */
@Data
@TableName("sys_log")
@NoArgsConstructor
public class Log extends Domain {

    /**
     * 操作用户
     */
    private String username;

    @TableField(exist = false)
    private String nickname;

    /**
     * 方法名
     */
    private String method;

    private String accountCode;

    /**
     * 参数
     */
    private String params;

    /**
     * 日志类型
     */
    private String logType;

    /**
     * 请求ip
     */
    private String requestIp;

    /**
     * 地址
     */
    private String address;

    /**
     * 浏览器
     */
    private String browser;

    /**
     * 请求耗时
     */
    private Long time;

    /**
     * 异常详细
     */
    private byte[] exceptionDetail;

    public Log(String logType, Long time) {
        this.logType = logType;
        this.time = time;
    }
}
