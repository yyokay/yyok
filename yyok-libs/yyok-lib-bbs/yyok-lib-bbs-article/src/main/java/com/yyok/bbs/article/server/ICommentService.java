package com.yyok.bbs.article.server;

import com.github.pagehelper.PageInfo;
import com.yyok.bbs.article.server.dto.CommentDTO;
import com.yyok.bbs.article.server.dto.CommentSearchDTO;

import java.util.List;

public interface ICommentService {
    /**
     * 获取文章的评论信息
     *
     * @param articleId
     * @return
     */
    List<CommentDTO> getCommentByArticleId(Integer articleId, String currentAccount);

    /**
     * 获取最新评论信息
     *
     * @param commentSearchDTO
     * @return
     */
    PageInfo<CommentDTO> getLatestComment(CommentSearchDTO commentSearchDTO);

    /**
     * 获取文章的评论数量
     *
     * @param articleId
     * @return
     */
    Long getCommentCountByArticle(Integer articleId);

    /**
     * 获取评论数量
     *
     * @return
     */
    Long getTotal();

    /**
     * 创建评论
     *
     * @param commentDTO
     * @param currentAccount
     * @return
     */
    Boolean create(CommentDTO commentDTO, String currentAccount);

    /**
     * 删除评论
     *
     * @param commentId
     * @return
     */
    Boolean delete(Integer commentId);

}
