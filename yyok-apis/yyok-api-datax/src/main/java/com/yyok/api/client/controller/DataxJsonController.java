package com.yyok.api.client.controller;

import com.yyok.acquisite.datax.client.core.util.I18nUtil;
import com.yyok.acquisite.datax.client.dto.DataXJsonBuildDto;
import com.yyok.acquisite.datax.client.service.IDataxJsonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/dataxJson")
@Api(tags = "组装datax json的控制器")
public class DataxJsonController extends BaseController {

    @Autowired
    private IDataxJsonService IDataxJsonService;


    @PostMapping("/buildJson")
    @ApiOperation("JSON构建")
    public String buildJobJson(@RequestBody DataXJsonBuildDto dto) {
        String key = "system_please_choose";
        if (dto.getReaderDatasourceId() == null) {
            return I18nUtil.getString(key) + I18nUtil.getString("jobinfo_field_readerDataSource");
        }
        if (dto.getWriterDatasourceId() == null) {
            return I18nUtil.getString(key) + I18nUtil.getString("jobinfo_field_writerDataSource");
        }
        if (CollectionUtils.isEmpty(dto.getReaderColumns())) {
            return I18nUtil.getString(key) + I18nUtil.getString("jobinfo_field_readerColumns");
        }
        if (CollectionUtils.isEmpty(dto.getWriterColumns())) {
            return I18nUtil.getString(key) + I18nUtil.getString("jobinfo_field_writerColumns");
        }
        return IDataxJsonService.buildJobJson(dto);
    }

}
