package com.yyok.acquisite.datax.client.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yyok.acquisite.datax.client.entity.JobProject;
import com.yyok.acquisite.datax.client.mapper.JobProjectMapper;
import com.yyok.acquisite.datax.client.service.IJobProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("jobProjectService")
public class IJobProjectServiceImpl extends ServiceImpl<JobProjectMapper, JobProject> implements IJobProjectService {

    @Autowired
    private JobProjectMapper jobProjectMapper;

    @Override
    public IPage<JobProject> getProjectListPaging(Integer pageSize, Integer pageNo, String searchName) {
        Page<JobProject> page = new Page(pageNo, pageSize);
        return jobProjectMapper.getProjectListPaging(page, searchName);
    }
}