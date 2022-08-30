package com.yyok.share.admin.info.service.impl;

import com.github.pagehelper.PageInfo;
import com.yyok.share.admin.info.domain.Job;
import com.yyok.share.admin.info.service.IDeptService;
import com.yyok.share.admin.info.service.IJobService;
import com.yyok.share.admin.info.service.dto.JobDto;
import com.yyok.share.admin.info.service.dto.JobQueryCriteria;
import com.yyok.share.admin.info.service.mapper.IJobMapper;
import com.yyok.share.framework.dozer.service.IGenerator;
import com.yyok.share.framework.mapper.common.service.impl.BaseServiceImpl;
import com.yyok.share.framework.mapper.common.utils.QueryHelpPlus;
import com.yyok.share.framework.utils.FileUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

// 默认不使用缓存
//import org.springframework.cache.annotation.CacheConfig;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;

/**
 * @author yyok
 * @date 2020-05-14
 */
@Service
@AllArgsConstructor
//@CacheConfig(cacheNames = "job")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class JobServiceImpl extends BaseServiceImpl<IJobMapper, Job> implements IJobService {

    private final IGenerator generator;

    private final IDeptService deptService;

    @Override
    //@Cacheable
    public Map<String, Object> queryAll(JobQueryCriteria criteria, Pageable pageable) {
        getPage(pageable);
        PageInfo<Job> page = new PageInfo<>(queryAll(criteria));
        Map<String, Object> map = new LinkedHashMap<>(2);
        map.put("content", generator.convert(page.getList(), JobDto.class));
        map.put("totalElements", page.getTotal());
        return map;
    }


    @Override
    //@Cacheable
    public List<Job> queryAll(JobQueryCriteria criteria) {
        List<Job> jobList = baseMapper.selectList(QueryHelpPlus.getPredicate(Job.class, criteria));
        if (criteria.getDeptCodes().size() == 0) {
            for (Job job : jobList) {
                job.setDept(deptService.findByCode(job.getDeptCode()));
            }
        } else {
            //断权限范围
            for (String deptCode : criteria.getDeptCodes()) {
                for (Job job : jobList) {
                    if (deptCode.equals(job.getDeptCode())) {
                        job.setDept(deptService.findByCode(job.getDeptCode()));
                    }
                }
            }
        }
        return jobList;
    }


    @Override
    public void download(List<JobDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (JobDto job : all) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("岗位名称", job.getName());
            map.put("岗位状态", job.getEnabled());
            map.put("岗位排序", job.getSorter());
            map.put("创建日期", job.getCreateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    public Job findByName(String name) {
        return null;
    }

    @Override
    public boolean updateByCode(Job coder) {
        return false;
    }
}
