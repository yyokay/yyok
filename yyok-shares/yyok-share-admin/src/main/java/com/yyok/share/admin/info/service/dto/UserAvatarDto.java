package com.yyok.share.admin.info.service.dto;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author yyok
 * @date 2020-05-14
 */
@Data
public class UserAvatarDto implements Serializable {

    private String coder;

    /**
     * 真实文件名
     */
    private String realName;

    /**
     * 路径
     */
    private String path;

    /**
     * 大小
     */
    private String size;

    /**
     * 创建时间
     */
    private Timestamp createTime;
}
