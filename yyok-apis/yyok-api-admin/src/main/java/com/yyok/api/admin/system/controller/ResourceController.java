package com.yyok.api.admin.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yyok.share.admin.security.security.vo.JwtAccount;
import com.yyok.share.admin.system.domain.Resource;
import com.yyok.share.admin.system.service.IAccountService;
import com.yyok.share.admin.system.service.IResourceService;
import com.yyok.share.admin.system.service.IRoleService;
import com.yyok.share.admin.system.service.dto.ResourceDto;
import com.yyok.share.admin.system.service.dto.ResourceQueryCriteria;
import com.yyok.share.framework.annotation.ForbidSubmit;
import com.yyok.share.framework.dozer.service.IGenerator;
import com.yyok.share.framework.exception.BadRequestException;
import com.yyok.share.framework.utils.SecurityUtils;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author yyok
 * @date 2022-12-03
 */
@Api(tags = "系统：菜单管理")
@RestController
@RequestMapping("/api/sys/resources")
@SuppressWarnings("unchecked")
public class ResourceController {

    private final IResourceService resourceService;

    private final IAccountService accountService;

    private final IRoleService roleService;

    private final IGenerator generator;

    private static final String ENTITY_NAME = "resource";

    public ResourceController(IResourceService resourceService, IAccountService accountService, IRoleService roleService, IGenerator generator) {
        this.resourceService = resourceService;
        this.accountService = accountService;
        this.roleService = roleService;
        this.generator = generator;
    }

    @ForbidSubmit
    @Log("导出菜单数据")
    @ApiOperation("导出菜单数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('resource:list')")
    public void download(HttpServletResponse response, ResourceQueryCriteria criteria) throws IOException {

        resourceService.download(generator.convert(resourceService.queryAll(criteria), ResourceDto.class), response);
    }

    @ApiOperation("获取前端所需菜单")
    @GetMapping(value = "/build")
    public ResponseEntity<Object> buildResources() {
        JwtAccount ju = (JwtAccount)SecurityUtils.getUserDetails();
        //Account account = accountService.findByName(ju.getAccount());
        List<ResourceDto> resourceDtoList = resourceService.findByRoles(roleService.findByAccountsCode(ju.getCoder()));
        List<ResourceDto> resourceDtos = (List<ResourceDto>) resourceService.buildTree(resourceDtoList).get("content");
        return new ResponseEntity<>(resourceService.buildResources(resourceDtos), HttpStatus.OK);
    }

    @ApiOperation("返回全部的菜单")
    @GetMapping(value = "/tree")
    @PreAuthorize("@el.check('resource:list','roles:list')")
    public ResponseEntity<Object> getResourceTree() {
        return new ResponseEntity<>(resourceService.getResourceTree(resourceService.findByPcode("")), HttpStatus.OK);
    }

    @Log("查询菜单")
    @ApiOperation("查询菜单")
    @GetMapping
    @PreAuthorize("@el.check('resource:list')")
    public ResponseEntity<Object> getResources(ResourceQueryCriteria criteria) {
        List<ResourceDto> resourceDtoList = generator.convert(resourceService.queryAll(criteria), ResourceDto.class);
        return new ResponseEntity<>(resourceService.buildTree(resourceDtoList), HttpStatus.OK);
    }

    @ForbidSubmit
    @Log("新增菜单")
    @ApiOperation("新增菜单")
    @PostMapping
    @PreAuthorize("@el.check('resource:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody Resource resources) {

        if (resources.getCoder() != null) {
            throw new BadRequestException("A new " + ENTITY_NAME + " cannot already have an ID");
        }
        return new ResponseEntity<>(resourceService.create(resources), HttpStatus.CREATED);
    }

    @ForbidSubmit
    @Log("修改菜单")
    @ApiOperation("修改菜单")
    @PutMapping
    @PreAuthorize("@el.check('resource:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody Resource resources) {

        resourceService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ForbidSubmit
    @Log("删除菜单")
    @ApiOperation("删除菜单")
    @DeleteMapping
    @PreAuthorize("@el.check('resource:del')")
    public ResponseEntity<Object> delete(@RequestBody Set<String> coders) {

        Set<Resource> resourceSet = new HashSet<>();
        for (String coder : coders) {
            List<Resource> resourceList = resourceService.findByPcode(coder);
            resourceSet.add(resourceService.getOne(new LambdaQueryWrapper<Resource>().eq(Resource::getCoder, coder)));
            resourceSet = resourceService.getDeleteResources(resourceList, resourceSet);
        }
        resourceService.delete(resourceSet);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
