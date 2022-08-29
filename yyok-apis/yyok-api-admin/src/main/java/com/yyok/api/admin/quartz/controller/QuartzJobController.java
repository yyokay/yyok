package com.yyok.api.admin.quartz.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yyok.share.admin.quartz.domain.QuartzJob;
import com.yyok.share.admin.quartz.service.IQuartzJobService;
import com.yyok.share.admin.quartz.service.IQuartzLogService;
import com.yyok.share.admin.quartz.service.dto.QuartzJobDto;
import com.yyok.share.admin.quartz.service.dto.QuartzJobQueryCriteria;
import com.yyok.share.admin.quartz.service.dto.QuartzLogDto;
import com.yyok.share.admin.quartz.service.dto.QuartzLogQueryCriteria;
import com.yyok.share.framework.annotation.ForbidSubmit;
import com.yyok.share.framework.dozer.service.IGenerator;
import com.yyok.share.framework.exception.BadRequestException;
import com.yyok.share.admin.logging.annotation.Log;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author yyok
 * @date 2019-01-07
 */
@Slf4j
@RestController
@Api(tags = "系统：定时任务管理")
@RequestMapping("/api/sys/jobs")
public class QuartzJobController {

    private static final String ENTITY_NAME = "quartzJob";

    private final IQuartzJobService quartzJobService;
    private final IGenerator generator;
    private final IQuartzLogService quartzLogService;


    public QuartzJobController(IQuartzJobService quartzJobService, IGenerator generator, IQuartzLogService quartzLogService) {
        this.quartzJobService = quartzJobService;
        this.generator = generator;
        this.quartzLogService = quartzLogService;
    }

    @Log("查询定时任务")
    @ApiOperation("查询定时任务")
    @GetMapping
    @PreAuthorize("@el.check('admin','timing:list')")
    public ResponseEntity<Object> getJobs(QuartzJobQueryCriteria criteria, Pageable pageable) {
        return new ResponseEntity<>(quartzJobService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @Log("导出任务数据")
    @ApiOperation("导出任务数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('admin','timing:list')")
    public void download(HttpServletResponse response, QuartzJobQueryCriteria criteria) throws IOException {
        quartzJobService.download(generator.convert(quartzJobService.queryAll(criteria), QuartzJobDto.class), response);
    }

    @Log("导出日志数据")
    @ApiOperation("导出日志数据")
    @GetMapping(value = "/logs/download")
    @PreAuthorize("@el.check('admin','timing:list')")
    public void downloadLog(HttpServletResponse response, QuartzLogQueryCriteria criteria) throws IOException {
        quartzLogService.download(generator.convert(quartzLogService.queryAll(criteria), QuartzLogDto.class), response);
    }

    @ApiOperation("查询任务执行日志")
    @GetMapping(value = "/logs")
    @PreAuthorize("@el.check('admin','timing:list')")
    public ResponseEntity<Object> getJobLogs(QuartzLogQueryCriteria criteria, Pageable pageable) {
        return new ResponseEntity<>(quartzLogService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @ForbidSubmit
    @Log("新增定时任务")
    @ApiOperation("新增定时任务")
    @PostMapping
    @PreAuthorize("@el.check('admin','timing:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody QuartzJob resources) {
        if (resources.getCoder() != null) {
            throw new BadRequestException("A new " + ENTITY_NAME + " cannot already have an ID");
        }
        return new ResponseEntity<>(quartzJobService.save(resources), HttpStatus.CREATED);
    }

    @ForbidSubmit
    @Log("修改定时任务")
    @ApiOperation("修改定时任务")
    @PutMapping
    @PreAuthorize("@el.check('admin','timing:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody QuartzJob resources) {
        quartzJobService.updateByCode(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ForbidSubmit
    @Log("更改定时任务状态")
    @ApiOperation("更改定时任务状态")
    @PutMapping(value = "/{id}")
    @PreAuthorize("@el.check('admin','timing:edit')")
    public ResponseEntity<Object> updateIsPause(@PathVariable String coder) {
        quartzJobService.updateIsPause(quartzJobService.getOne(new LambdaQueryWrapper<QuartzJob>()
                .eq(QuartzJob::getCoder, coder)));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ForbidSubmit
    @Log("执行定时任务")
    @ApiOperation("执行定时任务")
    @PutMapping(value = "/exec/{id}")
    @PreAuthorize("@el.check('admin','timing:edit')")
    public ResponseEntity<Object> execution(@PathVariable String coder) {
        quartzJobService.execution(quartzJobService.getOne(new LambdaQueryWrapper<QuartzJob>().eq(QuartzJob::getCoder, coder)));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ForbidSubmit
    @Log("删除定时任务")
    @ApiOperation("删除定时任务")
    @DeleteMapping
    @PreAuthorize("@el.check('admin','timing:del')")
    public ResponseEntity<Object> delete(@RequestBody String[] codes) {
        quartzJobService.removeByCodes(new ArrayList<>(Arrays.asList(codes)));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
