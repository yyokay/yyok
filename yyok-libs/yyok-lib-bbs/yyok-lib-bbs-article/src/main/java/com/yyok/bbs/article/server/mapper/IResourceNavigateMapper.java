package com.yyok.bbs.article.server.mapper;

import com.yyok.bbs.article.domain.ResourceNavigate;
import com.yyok.bbs.article.domain.ResourceNavigateExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IResourceNavigateMapper {
    /**
     * countByExample
     * 
     * @param example
     * @return 
     */
    long countByExample(ResourceNavigateExample example);

    /**
     * deleteByExample
     * 
     * @param example
     * @return 
     */
    int deleteByExample(ResourceNavigateExample example);

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
    int insert(ResourceNavigate record);

    /**
     * insertSelective
     * 
     * @param record
     * @return 
     */
    int insertSelective(ResourceNavigate record);

    /**
     * selectByExample
     * 
     * @param example
     * @return 
     */
    List<ResourceNavigate> selectByExample(ResourceNavigateExample example);

    /**
     * selectByPrimaryKey
     * 
     * @param id
     * @return 
     */
    ResourceNavigate selectByPrimaryKey(Integer id);

    /**
     * updateByExampleSelective
     * 
     * @param record
     * @param example
     * @return 
     */
    int updateByExampleSelective(@Param("record") ResourceNavigate record, @Param("example") ResourceNavigateExample example);

    /**
     * updateByExample
     * 
     * @param record
     * @param example
     * @return 
     */
    int updateByExample(@Param("record") ResourceNavigate record, @Param("example") ResourceNavigateExample example);

    /**
     * updateByPrimaryKeySelective
     * 
     * @param record
     * @return 
     */
    int updateByPrimaryKeySelective(ResourceNavigate record);

    /**
     * updateByPrimaryKey
     * 
     * @param record
     * @return 
     */
    int updateByPrimaryKey(ResourceNavigate record);
}