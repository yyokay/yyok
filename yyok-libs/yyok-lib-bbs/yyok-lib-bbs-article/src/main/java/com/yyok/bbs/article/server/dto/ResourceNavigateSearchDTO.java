package com.yyok.bbs.article.server.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResourceNavigateSearchDTO implements Serializable {
    /**
     * 类别
     */
    private String category;

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
