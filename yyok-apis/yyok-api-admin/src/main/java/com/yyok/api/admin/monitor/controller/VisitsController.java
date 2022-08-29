package com.yyok.api.admin.monitor.controller;

import com.yyok.share.admin.monitor.service.IVisitsService;
import com.yyok.share.framework.utils.RequestHolder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yyok
 * @date 2022-12-13
 */
@RestController
@RequestMapping("/api/sys/visits")
@Api(tags = "系统：访问记录管理")
public class VisitsController {

    private final IVisitsService visitsService;

    public VisitsController(IVisitsService visitsService) {
        this.visitsService = visitsService;
    }

    @PostMapping
    @ApiOperation("创建访问记录")
    public ResponseEntity<Object> create() {
        visitsService.count(RequestHolder.getHttpServletRequest());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    @ApiOperation("查询")
    public ResponseEntity<Object> get() {
        return new ResponseEntity<>(visitsService.get(), HttpStatus.OK);
    }

    @GetMapping(value = "/chartData")
    @ApiOperation("查询图表数据")
    public ResponseEntity<Object> getChartData() {
        return new ResponseEntity<>(visitsService.getChartData(), HttpStatus.OK);
    }
}
