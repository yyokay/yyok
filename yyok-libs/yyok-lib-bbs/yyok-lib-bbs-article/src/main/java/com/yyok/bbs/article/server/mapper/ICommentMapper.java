package com.yyok.bbs.article.server.mapper;

import com.yyok.bbs.article.domain.Comment;
import com.yyok.bbs.article.domain.CommentExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ICommentMapper {
    /**
     * countByExample
     *
     * @param example
     * @return
     */
    long countByExample(CommentExample example);

    /**
     * deleteByExample
     *
     * @param example
     * @return
     */
    int deleteByExample(CommentExample example);

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
    int insert(Comment record);

    /**
     * insertSelective
     *
     * @param record
     * @return
     */
    int insertSelective(Comment record);

    /**
     * selectByExample
     *
     * @param example
     * @return
     */
    List<Comment> selectByExample(CommentExample example);

    /**
     * selectByPrimaryKey
     *
     * @param id
     * @return
     */
    Comment selectByPrimaryKey(Integer id);

    /**
     * updateByExampleSelective
     *
     * @param record
     * @param example
     * @return
     */
    int updateByExampleSelective(@Param("record") Comment record, @Param("example") CommentExample example);

    /**
     * updateByExample
     *
     * @param record
     * @param example
     * @return
     */
    int updateByExample(@Param("record") Comment record, @Param("example") CommentExample example);

    /**
     * updateByPrimaryKeySelective
     *
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Comment record);

    /**
     * updateByPrimaryKey
     *
     * @param record
     * @return
     */
    int updateByPrimaryKey(Comment record);
}