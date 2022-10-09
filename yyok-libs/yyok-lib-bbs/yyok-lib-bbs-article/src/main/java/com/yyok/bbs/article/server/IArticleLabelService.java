package com.yyok.bbs.article.server;

import com.yyok.bbs.article.server.dto.ArticleLabelDTO;

import java.util.List;

/**
 * @author root
 */
public interface IArticleLabelService {

    /**
     * 新增文件标签关系信息
     *
     * @param labelIds
     * @param articleId
     * @param accountCode
     * @return
     */
    Boolean create(List<Integer> labelIds, Integer articleId, String accountCode);

    /**
     * 更新文件标签关系信息
     *
     * @param labelIds
     * @param articleId
     * @param currentAccount
     * @return
     */
    Boolean update(List<Integer> labelIds, Integer articleId, List<String> currentAccount);

    /**
     * 根据标签id集合获取文章标签信息
     *
     * @param labelIds
     * @return
     */
    List<ArticleLabelDTO> getByLabelIds(List<Integer> labelIds);

    /**
     * 根据文章id集合获取文章标签信息
     *
     * @param articleIds
     * @return
     */
    List<ArticleLabelDTO> getByArticleIds(List<Integer> articleIds);

    /**
     * 获取标签使用数量
     *
     * @param labelId
     * @return
     */
    Long getCountByLabelId(Integer labelId);

}
