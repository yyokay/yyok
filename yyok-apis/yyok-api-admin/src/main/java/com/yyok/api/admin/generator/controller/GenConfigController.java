package com.yyok.api.admin.generator.controller;

import com.yyok.share.admin.generator.domain.GenConfig;
import com.yyok.share.admin.generator.service.IGenConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author yyok
 * @date 2019-01-14
 */
@RestController
@RequestMapping("/api/sys/generator/config")
@Api(tags = "系统：代码生成器配置管理")
public class GenConfigController {

    private final IGenConfigService genConfigService;

    public GenConfigController(IGenConfigService genConfigService) {
        this.genConfigService = genConfigService;
    }

    @ApiOperation("查询")
    @GetMapping(value = "/{tableName}")
    public ResponseEntity<Object> get(@PathVariable String tableName) {
        return new ResponseEntity<>(genConfigService.find(tableName), HttpStatus.OK);
    }

    @ApiOperation("修改")
    @PutMapping
    public ResponseEntity<Object> emailConfig(@Validated @RequestBody GenConfig genConfig) {
        return new ResponseEntity<>(genConfigService.update(genConfig.getTableName(), genConfig), HttpStatus.OK);
    }
}
