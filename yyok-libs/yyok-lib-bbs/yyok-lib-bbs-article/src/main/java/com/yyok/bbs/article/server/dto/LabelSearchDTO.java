package com.yyok.bbs.article.server.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class LabelSearchDTO implements Serializable {
    /**
     * 标签编号
     */
    private Integer id;

    /**
     * 标签名字
     */
    private String labelName;

    /**
     * 逻辑删除(0正常,1删除)
     */
    private Boolean isDeleted;

    /**
     * 当前页
     */
    private Integer currentPage;

    /**
     * 每页条数
     */
    private Integer pageSize;

    private static final long serialVersionUID = 1L;

}
