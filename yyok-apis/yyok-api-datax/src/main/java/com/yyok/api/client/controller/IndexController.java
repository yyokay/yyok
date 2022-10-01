package com.yyok.api.client.controller;

import com.yyok.acquisite.datax.core.biz.model.ReturnT;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@RestController
@Api(tags = "首页接口")
@RequestMapping("/api")
public class IndexController {

    @Resource
    private IJobService IJobService;


    @GetMapping("/index")
    @ApiOperation("监控图")
    public ReturnT<Map<String, Object>> index() {
        return new ReturnT<>(IJobService.dashboardInfo());
    }

    @PostMapping("/chartInfo")
    @ApiOperation("图表信息")
    public ReturnT<Map<String, Object>> chartInfo() {
        return IJobService.chartInfo();
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

}
