package com.yyok.api.admin.system.controller;

import com.yyok.share.admin.system.domain.DictDetail;
import com.yyok.share.admin.system.service.IDictDetailService;
import com.yyok.share.admin.system.service.dto.DictDetailQueryCriteria;
import com.yyok.share.framework.annotation.ForbidSubmit;
import com.yyok.share.framework.exception.BadRequestException;
import com.yyok.share.admin.logging.annotation.Log;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yyok
 * @date 2019-04-10
 */
@RestController
@Api(tags = "系统：字典详情管理")
@RequestMapping("/api/sys/dictDetail")
public class DictDetailController {

    private final IDictDetailService dictDetailService;
    private static final String ENTITY_NAME = "dictDetail";

    public DictDetailController(IDictDetailService dictDetailService) {
        this.dictDetailService = dictDetailService;
    }

    @Log("查询字典详情")
    @ApiOperation("查询字典详情")
    @GetMapping
    public ResponseEntity<Object> getDictDetails(DictDetailQueryCriteria criteria,
                                                 @PageableDefault(sort = {"sort + 0"}, direction = Sort.Direction.ASC) Pageable pageable) {
        return new ResponseEntity<>(dictDetailService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @Log("查询多个字典详情")
    @ApiOperation("查询多个字典详情")
    @GetMapping(value = "/map")
    public ResponseEntity<Object> getDictDetailMaps(DictDetailQueryCriteria criteria,
                                                    @PageableDefault(sort = {"sort + 0"}, direction = Sort.Direction.ASC) Pageable pageable) {
        String[] names = criteria.getDictName().split(",");
        Map<String, Object> map = new HashMap<>(names.length);
        for (String name : names) {
            criteria.setDictName(name);
            map.put(name, dictDetailService.queryAll(criteria, pageable).get("content"));
        }
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @Log("新增字典详情")
    @ApiOperation("新增字典详情")
    @PostMapping
    @PreAuthorize("@el.check('admin','dict:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody DictDetail resources) {
        if (resources.getCoder() != null) {
            throw new BadRequestException("A new " + ENTITY_NAME + " cannot already have an ID");
        }
        resources.setDictCode(resources.getDict().getCoder());
        return new ResponseEntity<>(dictDetailService.save(resources), HttpStatus.CREATED);
    }

    @ForbidSubmit
    @Log("修改字典详情")
    @ApiOperation("修改字典详情")
    @PutMapping
    @PreAuthorize("@el.check('admin','dict:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody DictDetail resources) {

        dictDetailService.saveOrUpdate(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ForbidSubmit
    @Log("删除字典详情")
    @ApiOperation("删除字典详情")
    @DeleteMapping(value = "/{id}")
    @PreAuthorize("@el.check('admin','dict:del')")
    public ResponseEntity<Object> delete(@PathVariable String coder) {

        dictDetailService.removeByCode(coder);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
