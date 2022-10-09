package com.yyok.bbs.article.server.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ArticleCountDTO implements Serializable {
    /**
     * 是否点赞
     */
    private Boolean isLike;

    /**
     * 点赞数量
     */
    private Long likeCount;

    /**
     * 评论数量
     */
    private Long commentCount;

    /**
     * 是否关注
     */
    private Boolean isFollow;

    /**
     * 等级（Lv6）
     */
    private String level;

    private static final long serialVersionUID = 1L;

}
