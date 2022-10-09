package com.yyok.bbs.article.server.mapper;

import com.yyok.bbs.article.domain.ArticleLabel;
import com.yyok.bbs.article.domain.ArticleLabelExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IArticleLabelMapper {
    /**
     * countByExample
     *
     * @param example
     * @return
     */
    long countByExample(ArticleLabelExample example);

    /**
     * deleteByExample
     *
     * @param example
     * @return
     */
    int deleteByExample(ArticleLabelExample example);

    /**
     * deleteByPrimaryKey
     *
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * insert
     *
     * @param record
     * @return
     */
    int insert(ArticleLabel record);

    /**
     * insertSelective
     *
     * @param record
     * @return
     */
    int insertSelective(ArticleLabel record);

    /**
     * selectByExample
     *
     * @param example
     * @return
     */
    List<ArticleLabel> selectByExample(ArticleLabelExample example);

    /**
     * selectByPrimaryKey
     *
     * @param id
     * @return
     */
    ArticleLabel selectByPrimaryKey(Integer id);

    /**
     * updateByExampleSelective
     *
     * @param record
     * @param example
     * @return
     */
    int updateByExampleSelective(@Param("record") ArticleLabel record, @Param("example") ArticleLabelExample example);

    /**
     * updateByExample
     *
     * @param record
     * @param example
     * @return
     */
    int updateByExample(@Param("record") ArticleLabel record, @Param("example") ArticleLabelExample example);

    /**
     * updateByPrimaryKeySelective
     *
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(ArticleLabel record);

    /**
     * updateByPrimaryKey
     *
     * @param record
     * @return
     */
    int updateByPrimaryKey(ArticleLabel record);
}