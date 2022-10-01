package com.yyok.api.client.controller;


import com.yyok.acquisite.datax.core.biz.model.ReturnT;
import com.yyok.acquisite.datax.client.core.cron.CronExpression;
import com.yyok.acquisite.datax.client.core.thread.JobTriggerPoolHelper;
import com.yyok.acquisite.datax.client.core.trigger.TriggerTypeEnum;
import com.yyok.acquisite.datax.client.core.util.I18nUtil;
import com.yyok.acquisite.datax.client.dto.DataXBatchJsonBuildDto;
import com.yyok.acquisite.datax.client.dto.TriggerJobDto;
import com.yyok.acquisite.datax.client.entity.JobInfo;
import com.yyok.share.framework.utils.DateUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Api(tags = "任务配置接口")
@RestController
@RequestMapping("/api/job")
public class JobInfoController extends BaseController{

    @Resource
    private IJobService IJobService;


    @GetMapping("/pageList")
    @ApiOperation("任务列表")
    public ReturnT<Map<String, Object>> pageList(@RequestParam(required = false, defaultValue = "0") int current,
                                        @RequestParam(required = false, defaultValue = "10") int size,
                                        int jobGroup, int triggerStatus, String jobDesc, String glueType, Integer[] projectIds) {

        return new ReturnT<>(IJobService.pageList((current-1)*size, size, jobGroup, triggerStatus, jobDesc, glueType, 0, projectIds));
    }

    @GetMapping("/list")
    @ApiOperation("全部任务列表")
    public ReturnT<List<JobInfo>> list(){
        return new ReturnT<>(IJobService.list());
    }

    @PostMapping("/add")
    @ApiOperation("添加任务")
    public ReturnT<String> add(HttpServletRequest request, @RequestBody JobInfo jobInfo) {
        jobInfo.setUserId(getCurrentUserId(request));
        return IJobService.add(jobInfo);
    }

    @PostMapping("/update")
    @ApiOperation("更新任务")
    public ReturnT<String> update(HttpServletRequest request,@RequestBody JobInfo jobInfo) {
        jobInfo.setUserId(getCurrentUserId(request));
        return IJobService.update(jobInfo);
    }

    @PostMapping(value = "/remove/{id}")
    @ApiOperation("移除任务")
    public ReturnT<String> remove(@PathVariable(value = "id") int id) {
        return IJobService.remove(id);
    }

    @RequestMapping(value = "/stop",method = RequestMethod.POST)
    @ApiOperation("停止任务")
    public ReturnT<String> pause(int id) {
        return IJobService.stop(id);
    }

    @RequestMapping(value = "/start",method = RequestMethod.POST)
    @ApiOperation("开启任务")
    public ReturnT<String> start(int id) {
        return IJobService.start(id);
    }

    @PostMapping(value = "/trigger")
    @ApiOperation("触发任务")
    public ReturnT<String> triggerJob(@RequestBody TriggerJobDto dto) {
        // force cover job param
        String executorParam=dto.getExecutorParam();
        if (executorParam == null) {
            executorParam = "";
        }
        JobTriggerPoolHelper.trigger(dto.getJobId(), TriggerTypeEnum.MANUAL, -1, null, executorParam);
        return ReturnT.SUCCESS;
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

    @PostMapping("/batchAdd")
    @ApiOperation("批量创建任务")
    public ReturnT<String> batchAdd(@RequestBody DataXBatchJsonBuildDto dto) throws IOException {
        IJobService.ceateTargetStoreTable(dto);
        if (dto.getTemplateId() ==0) {
            return new ReturnT<>(ReturnT.FAIL_CODE, (I18nUtil.getString("system_please_choose") + I18nUtil.getString("jobinfo_field_temp")));
        }
        return IJobService.batchAdd(dto);
    }
}
