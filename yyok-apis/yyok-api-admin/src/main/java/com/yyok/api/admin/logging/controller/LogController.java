package com.yyok.api.admin.logging.controller;

import com.yyok.share.framework.enums.LogTypeEnum;
import com.yyok.share.admin.logging.annotation.Log;
import com.yyok.share.admin.logging.service.ILogService;
import com.yyok.share.admin.logging.service.dto.LogQueryCriteria;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author yyok
 * @date 2022-05-24
 */
@RestController
@RequestMapping("/api/sys/logs")
@Api(tags = "监控：日志管理")
@SuppressWarnings("unchecked")
public class LogController {

    private final ILogService logService;


    public LogController(ILogService logService) {
        this.logService = logService;
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('admin','log:list')")
    public void download(HttpServletResponse response, LogQueryCriteria criteria) throws IOException {
        criteria.setLogType(LogTypeEnum.INFO.getDesc());
        logService.download(logService.queryAll(criteria), response);
    }

    @Log("导出错误数据")
    @ApiOperation("导出错误数据")
    @GetMapping(value = "/error/download")
    @PreAuthorize("@el.check('admin','log:list')")
    public void errorDownload(HttpServletResponse response, LogQueryCriteria criteria) throws IOException {
        criteria.setLogType(LogTypeEnum.ERROR.getDesc());
        logService.download(logService.queryAll(criteria), response);
    }

    @GetMapping
    @ApiOperation("日志查询")
    @PreAuthorize("@el.check('admin','log:list')")
    public ResponseEntity<Object> getLogs(LogQueryCriteria criteria, Pageable pageable) {
        return new ResponseEntity<>(logService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @GetMapping(value = "/mlogs")
    @PreAuthorize("@el.check('admin','log:list')")
    public ResponseEntity getApiLogs(LogQueryCriteria criteria, Pageable pageable) {
        return new ResponseEntity(logService.findAllByPageable(criteria, pageable), HttpStatus.OK);
    }

    @GetMapping(value = "/user")
    @ApiOperation("用户日志查询")
    public ResponseEntity<Object> getUserLogs(LogQueryCriteria criteria, Pageable pageable) {
        return new ResponseEntity<>(logService.queryAllByUser(criteria, pageable), HttpStatus.OK);
    }

    @GetMapping(value = "/error")
    @ApiOperation("错误日志查询")
    @PreAuthorize("@el.check('admin','logError:list')")
    public ResponseEntity<Object> getErrorLogs(LogQueryCriteria criteria, Pageable pageable) {
        return new ResponseEntity<>(logService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @GetMapping(value = "/error/{id}")
    @ApiOperation("日志异常详情查询")
    @PreAuthorize("@el.check('admin','logError:detail')")
    public ResponseEntity<Object> getErrorLogs(@PathVariable String coder) {
        return new ResponseEntity<>(logService.findByErrDetail(coder), HttpStatus.OK);
    }

    @DeleteMapping(value = "/del/error")
    @Log("删除所有ERROR日志")
    @ApiOperation("删除所有ERROR日志")
    @PreAuthorize("@el.check('admin','logError:remove')")
    public ResponseEntity<Object> delAllByError() {
        logService.delAllByError();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/del/info")
    @Log("删除所有INFO日志")
    @ApiOperation("删除所有INFO日志")
    @PreAuthorize("@el.check('admin','logInfo:remove')")
    public ResponseEntity<Object> delAllByInfo() {
        logService.delAllByInfo();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
