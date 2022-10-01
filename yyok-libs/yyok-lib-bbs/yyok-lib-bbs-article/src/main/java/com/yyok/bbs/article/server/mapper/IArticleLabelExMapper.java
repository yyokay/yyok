package com.yyok.bbs.article.server.mapper;

public interface IArticleLabelExMapper {
    /**
     * 获取标签使用数量
     *
     * @param labelId
     * @return
     */
    long countByLabelId(Integer labelId);

}