package com.yyok.bbs.article.server.mapper;

import com.yyok.bbs.article.server.dto.ArticleReadDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IArticleExMapper {
    /**
     * 获取用户点赞数量
     *
     * @param userIds
     * @return
     */
    List<ArticleReadDTO> selectUserReadCount(@Param("userIds") List<Long> userIds);

}