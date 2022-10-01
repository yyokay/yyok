package com.yyok.api.client.controller;


import com.yyok.acquisite.datax.core.biz.model.ReturnT;
import com.yyok.acquisite.datax.client.core.cron.CronExpression;
import com.yyok.acquisite.datax.client.core.util.I18nUtil;
import com.yyok.acquisite.datax.client.entity.JobTemplate;
import com.yyok.acquisite.datax.client.service.IJobTemplateService;
import com.yyok.share.framework.utils.DateUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.*;

@Api(tags = "任务配置接口")
@RestController
@RequestMapping("/api/jobTemplate")
public class JobTemplateController extends BaseController{

    @Resource
    private IJobTemplateService IJobTemplateService;

    @GetMapping("/pageList")
    @ApiOperation("任务模板列表")
    public ReturnT<Map<String, Object>> pageList(@RequestParam(required = false, defaultValue = "0") int current,
                                        @RequestParam(required = false, defaultValue = "10") int size,
                                        int jobGroup, String jobDesc, String executorHandler, int userId,Integer[] projectIds) {

        return new ReturnT<>(IJobTemplateService.pageList((current-1)*size, size, jobGroup, jobDesc, executorHandler, userId, projectIds));
    }

    @PostMapping("/add")
    @ApiOperation("添加任务模板")
    public ReturnT<String> add(HttpServletRequest request, @RequestBody JobTemplate jobTemplate) {
        jobTemplate.setUserId(getCurrentUserId(request));
        return IJobTemplateService.add(jobTemplate);
    }

    @PostMapping("/update")
    @ApiOperation("更新任务")
    public ReturnT<String> update(HttpServletRequest request,@RequestBody JobTemplate jobTemplate) {
        jobTemplate.setUserId(getCurrentUserId(request));
        return IJobTemplateService.update(jobTemplate);
    }

    @PostMapping(value = "/remove/{id}")
    @ApiOperation("移除任务模板")
    public ReturnT<String> remove(@PathVariable(value = "id") int id) {
        return IJobTemplateService.remove(id);
    }

    @GetMapping("/nextTriggerTime")
    @ApiOperation("获取近5次触发时间")
    public ReturnT<List<String>> nextTriggerTime(String cron) {
        List<String> result = new ArrayList<>();
        try {
            CronExpression cronExpression = new CronExpression(cron);
            Date lastTime = new Date();
            for (int i = 0; i < 5; i++) {
                lastTime = cronExpression.getNextValidTimeAfter(lastTime);
                if (lastTime != null) {
                    result.add(DateUtils.formatDateTime(lastTime));
                } else {
                    break;
                }
            }
        } catch (ParseException e) {
            return new ReturnT<>(ReturnT.FAIL_CODE, I18nUtil.getString("jobinfo_field_cron_invalid"));
        }
        return new ReturnT<>(result);
    }
}
