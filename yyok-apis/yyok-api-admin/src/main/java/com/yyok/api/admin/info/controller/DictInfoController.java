package com.yyok.api.admin.info.controller;

import com.yyok.share.admin.info.domain.DictInfo;
import com.yyok.share.admin.info.service.IDictInfoService;
import com.yyok.share.admin.info.service.dto.DictInfoDto;
import com.yyok.share.admin.info.service.dto.DictInfoQueryCriteria;
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

/**
 * @author yyok
 * @date 2019-04-10
 */
@Api(tags = "系统：字典管理")
@RestController
@RequestMapping("/api/info/dictInfo")
public class DictInfoController {

    private final IDictInfoService dictInfoService;
    private final IGenerator generator;

    private static final String ENTITY_NAME = "dict";

    public DictInfoController(IDictInfoService dictInfoService, IGenerator generator) {
        this.dictInfoService = dictInfoService;
        this.generator = generator;
    }

    @Log("导出字典数据")
    @ApiOperation("导出字典数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('admin','dict:list')")
    public void download(HttpServletResponse response, DictInfoQueryCriteria criteria) throws IOException {
        dictInfoService.download(generator.convert(dictInfoService.queryAll(criteria), DictInfoDto.class), response);
    }

    @Log("查询字典")
    @ApiOperation("查询字典")
    @GetMapping(value = "/all")
    @PreAuthorize("@el.check('admin','dict:list')")
    public ResponseEntity<Object> all() {
        return new ResponseEntity<>(dictInfoService.queryAll(new DictInfoQueryCriteria()), HttpStatus.OK);
    }

    @Log("查询字典")
    @ApiOperation("查询字典")
    @GetMapping
    @PreAuthorize("@el.check('admin','dict:list')")
    public ResponseEntity<Object> getDicts(DictInfoQueryCriteria resources, Pageable pageable) {
        return new ResponseEntity<>(dictInfoService.queryAll(resources, pageable), HttpStatus.OK);
    }

    @Log("新增字典")
    @ApiOperation("新增字典")
    @PostMapping
    @PreAuthorize("@el.check('admin','dict:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody DictInfo resources) {
        if (resources.getCoder() != null) {
            throw new BadRequestException("A new " + ENTITY_NAME + " cannot already have an ID");
        }
        return new ResponseEntity<>(dictInfoService.save(resources), HttpStatus.CREATED);
    }

    @ForbidSubmit
    @Log("修改字典")
    @ApiOperation("修改字典")
    @PutMapping
    @PreAuthorize("@el.check('admin','dict:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody DictInfo resources) {

        dictInfoService.saveOrUpdate(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ForbidSubmit
    @Log("删除字典")
    @ApiOperation("删除字典")
    @DeleteMapping(value = "/{id}")
    @PreAuthorize("@el.check('admin','dict:del')")
    public ResponseEntity<Object> delete(@PathVariable String coder) {

        dictInfoService.removeByCode(coder);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
