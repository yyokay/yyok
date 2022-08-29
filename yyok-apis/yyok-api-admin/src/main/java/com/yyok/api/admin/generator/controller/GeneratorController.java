package com.yyok.api.admin.generator.controller;

import com.yyok.share.framework.exception.BadRequestException;
import com.yyok.share.framework.utils.PageUtil;
import com.yyok.share.admin.generator.domain.ColumnConfig;
import com.yyok.share.admin.generator.service.IGenConfigService;
import com.yyok.share.admin.generator.service.IGeneratorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author yyok
 * @date 2019-01-02
 */
@RestController
@RequestMapping("/api/sys/generator")
@Api(tags = "系统：代码生成管理")
public class GeneratorController {

    private final IGeneratorService IGeneratorService;

    private final IGenConfigService genConfigService;

    @Value("${generator.enabled}")
    private int generatorEnabled;

    public GeneratorController(IGeneratorService IGeneratorService, IGenConfigService genConfigService) {
        this.IGeneratorService = IGeneratorService;
        this.genConfigService = genConfigService;
    }

    @ApiOperation("查询数据库数据")
    @GetMapping(value = "/tables/all")
    public ResponseEntity<Object> getTables() {
        return new ResponseEntity<>(IGeneratorService.getTables(), HttpStatus.OK);
    }

    @ApiOperation("查询数据库数据")
    @GetMapping(value = "/tables")
    public ResponseEntity<Object> getTables(@RequestParam(defaultValue = "") String name,
                                            @RequestParam(defaultValue = "1") Integer page,
                                            @RequestParam(defaultValue = "10") Integer size) {
        return new ResponseEntity<>(IGeneratorService.getTables(name, page, size), HttpStatus.OK);
    }

    @ApiOperation("查询字段数据")
    @GetMapping(value = "/columns")
    public ResponseEntity<Object> getTables(@RequestParam String tableName) {
        List<ColumnConfig> columnInfos = IGeneratorService.getColumns(tableName);
        return new ResponseEntity<>(PageUtil.toPage(columnInfos, columnInfos.size()), HttpStatus.OK);
    }

    @ApiOperation("保存字段数据")
    @PutMapping
    public ResponseEntity<HttpStatus> save(@RequestBody List<ColumnConfig> columnInfos) {
        IGeneratorService.save(columnInfos);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation("同步字段数据")
    @PostMapping(value = "sync")
    public ResponseEntity<HttpStatus> sync(@RequestBody List<String> tables) {
        for (String table : tables) {
            IGeneratorService.sync(IGeneratorService.getColumns(table), IGeneratorService.query(table));
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation("生成代码")
    @PostMapping(value = "/{tableName}/{type}")
    public ResponseEntity<Object> generator(@PathVariable String tableName, @PathVariable Integer type, HttpServletRequest request, HttpServletResponse response) {
        if (generatorEnabled != 1 && type == 0) {
            throw new BadRequestException("此环境不允许生成代码，请选择预览或者下载查看！");
        }
        switch (type) {
            // 生成代码
            case 0:
                IGeneratorService.generator(genConfigService.find(tableName), IGeneratorService.getColumns(tableName));
                break;
            // 预览
            case 1:
                return IGeneratorService.preview(genConfigService.find(tableName), IGeneratorService.getColumns(tableName));
            // 打包
            case 2:
                IGeneratorService.download(genConfigService.find(tableName), IGeneratorService.getColumns(tableName), request, response);
                break;
            default:
                throw new BadRequestException("没有这个选项");
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
