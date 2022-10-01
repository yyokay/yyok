package com.yyok.bbs.article.server.mapper;

import com.yyok.bbs.article.domain.Article;
import com.yyok.bbs.article.domain.ArticleExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IArticleMapper {
    /**
     * countByExample
     * 
     * @param example
     * @return 
     */
    long countByExample(ArticleExample example);

    /**
     * deleteByExample
     * 
     * @param example
     * @return 
     */
    int deleteByExample(ArticleExample example);

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
    int insert(Article record);

    /**
     * insertSelective
     * 
     * @param record
     * @return 
     */
    int insertSelective(Article record);

    /**
     * selectByExample
     * 
     * @param example
     * @return 
     */
    List<Article> selectByExample(ArticleExample example);

    /**
     * selectByPrimaryKey
     * 
     * @param id
     * @return 
     */
    Article selectByPrimaryKey(Integer id);

    /**
     * updateByExampleSelective
     * 
     * @param record
     * @param example
     * @return 
     */
    int updateByExampleSelective(@Param("record") Article record, @Param("example") ArticleExample example);

    /**
     * updateByExample
     * 
     * @param record
     * @param example
     * @return 
     */
    int updateByExample(@Param("record") Article record, @Param("example") ArticleExample example);

    /**
     * updateByPrimaryKeySelective
     * 
     * @param record
     * @return 
     */
    int updateByPrimaryKeySelective(Article record);

    /**
     * updateByPrimaryKey
     * 
     * @param record
     * @return 
     */
    int updateByPrimaryKey(Article record);
}