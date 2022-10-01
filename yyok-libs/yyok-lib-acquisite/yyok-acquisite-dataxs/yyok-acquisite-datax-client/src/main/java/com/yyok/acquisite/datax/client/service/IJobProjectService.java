package com.yyok.acquisite.datax.client.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yyok.acquisite.datax.client.entity.JobProject;

public interface IJobProjectService extends IService<JobProject> {

    /**
     * project page
     * @param pageSize
     * @param pageNo
     * @param searchName
     * @return
     */

    IPage<JobProject> getProjectListPaging(Integer pageSize, Integer pageNo, String searchName);
}