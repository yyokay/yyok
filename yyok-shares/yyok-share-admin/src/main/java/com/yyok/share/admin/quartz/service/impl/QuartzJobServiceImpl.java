package com.yyok.share.admin.quartz.service.impl;

import com.github.pagehelper.PageInfo;
import com.yyok.share.admin.quartz.domain.QuartzJob;
import com.yyok.share.admin.quartz.service.IQuartzJobService;
import com.yyok.share.admin.quartz.service.dto.QuartzJobDto;
import com.yyok.share.admin.quartz.service.dto.QuartzJobQueryCriteria;
import com.yyok.share.admin.quartz.service.mapper.IQuartzJobMapper;
import com.yyok.share.admin.quartz.utils.QuartzManage;
import com.yyok.share.framework.dozer.service.IGenerator;
import com.yyok.share.framework.exception.BadRequestException;
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
 * @date 2020-05-13
 */
@Service
@AllArgsConstructor
//@CacheConfig(cacheNames = "quartzJob")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class QuartzJobServiceImpl extends BaseServiceImpl<IQuartzJobMapper, QuartzJob> implements IQuartzJobService {

    private final IGenerator generator;
    private final QuartzManage quartzManage;

    @Override
    //@Cacheable
    public Map<String, Object> queryAll(QuartzJobQueryCriteria criteria, Pageable pageable) {
        getPage(pageable);
        PageInfo<QuartzJob> page = new PageInfo<>(queryAll(criteria));
        Map<String, Object> map = new LinkedHashMap<>(2);
        map.put("content", generator.convert(page.getList(), QuartzJobDto.class));
        map.put("totalElements", page.getTotal());
        return map;
    }


    @Override
    //@Cacheable
    public List<QuartzJob> queryAll(QuartzJobQueryCriteria criteria) {
        return baseMapper.selectList(QueryHelpPlus.getPredicate(QuartzJob.class, criteria));
    }


    @Override
    public void download(List<QuartzJobDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (QuartzJobDto quartzJob : all) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("Spring Bean名称", quartzJob.getBeanName());
            map.put("cron 表达式", quartzJob.getCronExpression());
            map.put("状态：1暂停、0启用", quartzJob.getIsPause());
            map.put("任务名称", quartzJob.getJobName());
            map.put("方法名称", quartzJob.getMethodName());
            map.put("参数", quartzJob.getParams());
            map.put("备注", quartzJob.getRemark());
            map.put("创建日期", quartzJob.getCreateTime());
            map.put("Spring Bean名称", quartzJob.getBeanName());
            map.put("cron 表达式", quartzJob.getCronExpression());
            map.put("状态：1暂停、0启用", quartzJob.getIsPause());
            map.put("任务名称", quartzJob.getJobName());
            map.put("方法名称", quartzJob.getMethodName());
            map.put("参数", quartzJob.getParams());
            map.put("备注", quartzJob.getRemark());
            map.put("创建日期", quartzJob.getCreateTime());
            map.put("Spring Bean名称", quartzJob.getBeanName());
            map.put("cron 表达式", quartzJob.getCronExpression());
            map.put("状态：1暂停、0启用", quartzJob.getIsPause());
            map.put("任务名称", quartzJob.getJobName());
            map.put("方法名称", quartzJob.getMethodName());
            map.put("参数", quartzJob.getParams());
            map.put("备注", quartzJob.getRemark());
            map.put("创建日期", quartzJob.getCreateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    /**
     * 更改定时任务状态
     *
     * @param quartzJob /
     */
    @Override
    public void updateIsPause(QuartzJob quartzJob) {
        if (quartzJob.getCoder().equals(1L)) {
            throw new BadRequestException("该任务不可操作");
        }
        if (quartzJob.getIsPause()) {
            quartzManage.resumeJob(quartzJob);
        } else {
            quartzManage.pauseJob(quartzJob);
        }
        quartzJob.setIsPause(!quartzJob.getIsPause());
        this.saveOrUpdate(quartzJob);
    }

    @Override
    public boolean save(QuartzJob quartzJob) {
        quartzManage.addJob(quartzJob);
        return retBool(baseMapper.insert(quartzJob));
    }

    @Override
    public boolean updateByCode(QuartzJob quartzJob) {
        quartzManage.updateJobCron(quartzJob);
        return this.updateByCode(quartzJob);
    }

    /**
     * 立即执行定时任务
     *
     * @param quartzJob /
     */
    @Override
    public void execution(QuartzJob quartzJob) {
        if (quartzJob.getCoder().equals(1L)) {
            throw new BadRequestException("该任务不可操作");
        }
        quartzManage.runJobNow(quartzJob);
    }

    /**
     * 查询启用的任务
     *
     * @return List
     */
    @Override
    public List<QuartzJob> findByIsPauseIsFalse() {
        QuartzJobQueryCriteria criteria = new QuartzJobQueryCriteria();
        criteria.setIsPause(false);
        return baseMapper.selectList(QueryHelpPlus.getPredicate(QuartzJob.class, criteria));
    }

    @Override
    public void removeByCodes(List<String> codeList) {
        codeList.forEach(coder -> {
            QuartzJob quartzJob = this.findByCode(coder);
            quartzManage.deleteJob(quartzJob);
        });
        baseMapper.deleteBatchIds(codeList);
    }

    @Override
    public QuartzJob findByName(String name) {
        return null;
    }
}
