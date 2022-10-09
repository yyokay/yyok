package com.yyok.acquisite.datax.client.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yyok.acquisite.datax.client.entity.JobRegistry;
import com.yyok.acquisite.datax.client.mapper.JobRegistryMapper;
import com.yyok.acquisite.datax.client.service.IJobRegistryService;
import org.springframework.stereotype.Service;

@Service("jobRegistryService")
public class IJobRegistryServiceImpl extends ServiceImpl<JobRegistryMapper, JobRegistry> implements IJobRegistryService {

}