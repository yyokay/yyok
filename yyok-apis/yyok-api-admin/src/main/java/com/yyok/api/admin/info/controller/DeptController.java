package com.yyok.api.admin.info.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yyok.share.admin.config.DataScope;
import com.yyok.share.admin.info.domain.Dept;
import com.yyok.share.admin.info.service.IDeptService;
import com.yyok.share.admin.info.service.dto.DeptDto;
import com.yyok.share.admin.info.service.dto.DeptQueryCriteria;
import com.yyok.share.framework.annotation.ForbidSubmit;
import com.yyok.share.framework.dozer.service.IGenerator;
import com.yyok.share.framework.exception.BadRequestException;
import com.yyok.share.framework.utils.ValidationUtil;
import com.yyok.share.admin.logging.annotation.Log;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author yyok
 * @date 2019-03-25
 */
@RestController
@Api(tags = "系统：部门管理")
@RequestMapping("/api/sys/dept")
public class DeptController {

    private final IDeptService deptService;

    private final DataScope dataScope;

    private final IGenerator generator;

    private static final String ENTITY_NAME = "dept";

    public DeptController(IDeptService deptService, DataScope dataScope, IGenerator generator) {
        this.deptService = deptService;
        this.dataScope = dataScope;
        this.generator = generator;
    }

    @Log("导出部门数据")
    @ApiOperation("导出部门数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('admin','dept:list')")
    public void download(HttpServletResponse response, DeptQueryCriteria criteria) throws IOException {
        deptService.download(generator.convert(deptService.queryAll(criteria), DeptDto.class), response);
    }

    @Log("查询部门")
    @ApiOperation("查询部门")
    @GetMapping
    @PreAuthorize("@el.check('user:list','admin','dept:list')")
    public ResponseEntity<Object> getDepts(DeptQueryCriteria criteria) {
        // 数据权限
        criteria.setCodes(dataScope.getDeptCodes());
        List<DeptDto> deptDtos = generator.convert(deptService.queryAll(criteria), DeptDto.class);
        return new ResponseEntity<>(deptService.buildTree(deptDtos), HttpStatus.OK);
    }

    @Log("新增部门")
    @ApiOperation("新增部门")
    @PostMapping
    @PreAuthorize("@el.check('admin','dept:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody Dept resources) {
        if (resources.getCoder() != null) {
            throw new BadRequestException("A new " + ENTITY_NAME + " cannot already have an ID");
        }
        return new ResponseEntity<>(deptService.save(resources), HttpStatus.CREATED);
    }

    @Log("修改部门")
    @ApiOperation("修改部门")
    @PutMapping
    @PreAuthorize("@el.check('admin','dept:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody Dept resources) {
        if (resources.getCoder().equals(resources.getPcode())) {
            throw new BadRequestException("上级不能为自己");
        }
        Dept dept = deptService.getOne(new LambdaQueryWrapper<Dept>()
                .eq(Dept::getCoder, resources.getCoder()));
        ValidationUtil.isNull(dept.getCoder(), "Dept", "id", resources.getCoder());
        resources.setCoder(dept.getCoder());
        deptService.saveOrUpdate(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ForbidSubmit
    @Log("删除部门")
    @ApiOperation("删除部门")
    @DeleteMapping
    @PreAuthorize("@el.check('admin','dept:del')")
    public ResponseEntity<Object> delete(@RequestBody Set<String> coders) {
        List<String> deptCodes = new ArrayList<>();
        for (String deptCode : deptCodes) {
            List<Dept> deptList = deptService.findByPcode(deptCode);
            Dept dept = deptService.getOne(new LambdaQueryWrapper<Dept>().eq(Dept::getCoder, deptCode));
            if (null != dept) {
                deptCodes.add(dept.getCoder());
            }
            if (CollectionUtil.isNotEmpty(deptList)) {
                for (Dept d : deptList) {
                    deptCodes.add(d.getCoder());
                }
            }
        }

        deptService.delDepts(deptCodes);
//        try {
//            deptService.delDepts(deptCodes);
//        }catch (Throwable e){
//            throw new BadRequestException( "所选部门中存在岗位或者角色关联，请取消关联后再试");
//        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
