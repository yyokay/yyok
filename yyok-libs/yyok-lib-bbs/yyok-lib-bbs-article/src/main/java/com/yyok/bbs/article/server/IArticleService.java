package com.yyok.bbs.article.server;

import com.github.pagehelper.PageInfo;
import com.yyok.bbs.article.server.dto.*;

import java.util.List;

public interface IArticleService {
    /**
     * 获取文章
     *
     * @param articleSearchDTO
     * @param currentAccount
     * @param articleStateType
     * @return
     */
    PageInfo<ArticleDTO> getList(ArticleSearchDTO articleSearchDTO, String currentAccount, String articleStateType);
    /**
     * 获取待审核的文章
     *
     * @param articleSearchDTO
     * @param currentAccount
     * @return
     */
    PageInfo<ArticleDTO> getPendingReviewArticles(ArticleSearchDTO articleSearchDTO, String currentAccount);

    /**
     * 获取禁用的文章
     *
     * @param articleSearchDTO
     * @param currentAccount
     * @return
     */
    PageInfo<ArticleDTO> getDisabledArticles(ArticleSearchDTO articleSearchDTO, String currentAccount);

    /**
     * 修改文章审批状态
     *
     * @param articleDTO
     * @param currentAccount
     * @return
     */
    Boolean updateState(ArticleDTO articleDTO, String currentAccount);

    /**
     * 获取点赞过的文章
     *
     * @param likeSearchDTO
     * @param currentAccount
     * @return
     */
    PageInfo<ArticleDTO> getLikesArticle(LikeSearchDTO likeSearchDTO, String currentAccount);

    /**
     * 通过文章id集合获取文章信息
     *
     * @param ids
     * @param isPv 是否增加文章浏览数量
     * @param currentAccount
     * @return
     */
    List<ArticleDTO> getByIds(List<Integer> ids, Boolean isPv, String currentAccount);

    /**
     * 通过文章id集合获取文章信息(最基础的信息)
     *
     * @param ids
     * @return
     */
    List<ArticleDTO> getBaseByIds(List<Integer> ids, String articleStateType);

    /**
     * 撰写文章（无配图）
     *
     * @param articleDTO
     * @param currentAccount
     */
    Boolean create(ArticleDTO articleDTO, List<Integer> labelIds, String currentAccount);

    /**
     * 更新文章（无配图）
     *
     * @param articleDTO
     * @param currentAccount
     */
    Boolean update(ArticleDTO articleDTO, List<Integer> labelIds, String currentAccount);

    /**
     * 撰写文章
     *
     * @param bytes
     * @param sourceFileName
     * @param articleDTO
     * @param currentAccount
     */
    Boolean create(byte[] bytes, String sourceFileName, ArticleDTO articleDTO, List<Integer> labelIds, String currentAccount);

    /**
     * 更新文章
     *
     * @param bytes
     * @param sourceFileName
     * @param articleDTO
     * @param currentAccount
     */
    Boolean update(byte[] bytes, String sourceFileName, ArticleDTO articleDTO, List<Integer> labelIds, String currentAccount);

    /**
     * 上传图片（一张）- mavonEditor
     *
     * @param bytes
     * @param sourceFileName
     * @return
     */
    String uploadPicture(byte[] bytes, String sourceFileName);

    /**
     * 获取文章评论访问总数
     *
     * @return
     */
    TotalDTO getArticleCommentVisitTotal();

    /**
     * 获取文章数量
     *
     * @return
     */
    Long getTotal();

    /**
     * 获取用户阅读数量
     *
     * @param userIds
     * @return
     */
    List<ArticleReadDTO> getUserReadCount(List<Long> userIds);

    /**
     * 获取文章一些统计数据
     *
     * @param id
     * @param currentAccount
     * @return
     */
    ArticleCountDTO getCountById(Integer id, String currentAccount);

    /**
     * 增加文章浏览数量
     *
     * @param articleDTO
     */
    Boolean updatePv(ArticleDTO articleDTO);

    /**
     * 通过用户获取文章信息
     *
     * @param userId
     * @return
     */
    List<ArticleDTO> getByUserId(Long userId);

}
