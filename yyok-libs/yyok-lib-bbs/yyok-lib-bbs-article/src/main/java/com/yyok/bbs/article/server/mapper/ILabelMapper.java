package com.yyok.bbs.article.server.mapper;

import com.yyok.bbs.article.domain.Label;
import com.yyok.bbs.article.domain.LabelExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ILabelMapper {
    /**
     * countByExample
     * 
     * @param example
     * @return 
     */
    long countByExample(LabelExample example);

    /**
     * deleteByExample
     * 
     * @param example
     * @return 
     */
    int deleteByExample(LabelExample example);

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
    int insert(Label record);

    /**
     * insertSelective
     * 
     * @param record
     * @return 
     */
    int insertSelective(Label record);

    /**
     * selectByExample
     * 
     * @param example
     * @return 
     */
    List<Label> selectByExample(LabelExample example);

    /**
     * selectByPrimaryKey
     * 
     * @param id
     * @return 
     */
    Label selectByPrimaryKey(Integer id);

    /**
     * updateByExampleSelective
     * 
     * @param record
     * @param example
     * @return 
     */
    int updateByExampleSelective(@Param("record") Label record, @Param("example") LabelExample example);

    /**
     * updateByExample
     * 
     * @param record
     * @param example
     * @return 
     */
    int updateByExample(@Param("record") Label record, @Param("example") LabelExample example);

    /**
     * updateByPrimaryKeySelective
     * 
     * @param record
     * @return 
     */
    int updateByPrimaryKeySelective(Label record);

    /**
     * updateByPrimaryKey
     * 
     * @param record
     * @return 
     */
    int updateByPrimaryKey(Label record);
}