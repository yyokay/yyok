package com.yyok.acquisite.datax.client.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yyok.acquisite.datax.client.entity.JobProject;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Project
 *
 * @author jingwk
 * @version v2.1.12
 * @since 2020-05-24
 */
@Mapper
public interface JobProjectMapper extends BaseMapper<JobProject> {
    /**
     * project page
     * @param page
     * @param searchName
     * @return
     */
    IPage<JobProject> getProjectListPaging(IPage<JobProject> page,
                                          @Param("searchName") String searchName);
}