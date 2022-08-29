package com.yyok.api.admin.info.controller;

import com.yyok.share.admin.config.DataScope;
import com.yyok.share.admin.info.domain.Job;
import com.yyok.share.admin.info.service.IJobService;
import com.yyok.share.admin.info.service.dto.JobDto;
import com.yyok.share.admin.info.service.dto.JobQueryCriteria;
import com.yyok.share.framework.annotation.ForbidSubmit;
import com.yyok.share.framework.dozer.service.IGenerator;
import com.yyok.share.framework.exception.BadRequestException;
import com.yyok.share.admin.logging.annotation.Log;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author yyok
 * @date 2019-03-29
 */
@Api(tags = "系统：岗位管理")
@RestController
@RequestMapping("/api/sys/job")
public class JobController {

    private final IJobService jobService;

    private final DataScope dataScope;

    private final IGenerator generator;

    private static final String ENTITY_NAME = "job";

    public JobController(IJobService jobService, DataScope dataScope, IGenerator generator) {
        this.jobService = jobService;
        this.dataScope = dataScope;
        this.generator = generator;
    }

    @Log("导出岗位数据")
    @ApiOperation("导出岗位数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('admin','job:list')")
    public void download(HttpServletResponse response, JobQueryCriteria criteria) throws IOException {
        jobService.download(generator.convert(jobService.queryAll(criteria), JobDto.class), response);
    }

    @Log("查询岗位")
    @ApiOperation("查询岗位")
    @GetMapping
    @PreAuthorize("@el.check('admin','job:list','user:list')")
    public ResponseEntity<Object> getJobs(JobQueryCriteria criteria, Pageable pageable) {
        // 数据权限
        criteria.setDeptCodes(dataScope.getDeptCodes());
        return new ResponseEntity<>(jobService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @Log("新增岗位")
    @ApiOperation("新增岗位")
    @PostMapping
    @PreAuthorize("@el.check('admin','job:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody Job resources) {
        if (resources.getCoder() != null) {
            throw new BadRequestException("A new " + ENTITY_NAME + " cannot already have an ID");
        }
        resources.setDeptCode(resources.getDept().getCoder());
        return new ResponseEntity<>(jobService.save(resources), HttpStatus.CREATED);
    }

    @ForbidSubmit
    @Log("修改岗位")
    @ApiOperation("修改岗位")
    @PutMapping
    @PreAuthorize("@el.check('admin','job:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody Job resources) {

        resources.setDeptCode(resources.getDept().getCoder());
        jobService.saveOrUpdate(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ForbidSubmit
    @Log("删除岗位")
    @ApiOperation("删除岗位")
    @DeleteMapping
    @PreAuthorize("@el.check('admin','job:del')")
    public ResponseEntity<Object> delete(@RequestBody List<String> coders) {

        try {
            jobService.removeByCodes(coders);
        } catch (Throwable e) {
            throw new BadRequestException("所选岗位存在用户关联，请取消关联后再试");
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
