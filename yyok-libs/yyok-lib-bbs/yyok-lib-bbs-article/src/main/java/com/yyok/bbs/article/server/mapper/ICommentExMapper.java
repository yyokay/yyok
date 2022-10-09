package com.yyok.bbs.article.server.mapper;

import com.yyok.bbs.article.domain.Comment;

import java.util.List;


public interface ICommentExMapper {

    /**
     * 获取最新评论信息
     *
     * @param content
     * @param commentUser
     * @return
     */
    List<Comment> selectLatestComments(String content, Long commentUser);

}