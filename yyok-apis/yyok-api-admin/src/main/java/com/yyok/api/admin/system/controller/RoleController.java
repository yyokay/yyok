package com.yyok.api.admin.system.controller;

import cn.hutool.core.lang.Dict;
import com.yyok.share.admin.system.domain.Account;
import com.yyok.share.admin.system.domain.Role;
import com.yyok.share.admin.system.service.IAccountService;
import com.yyok.share.admin.system.service.IRoleService;
import com.yyok.share.admin.system.service.dto.RoleDto;
import com.yyok.share.admin.system.service.dto.RoleQueryCriteria;
import com.yyok.share.admin.system.service.dto.RoleSmallDto;
import com.yyok.share.framework.annotation.ForbidSubmit;
import com.yyok.share.framework.dozer.service.IGenerator;
import com.yyok.share.framework.exception.BadRequestException;
import com.yyok.share.framework.utils.SecurityUtils;
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

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author yyok
 * @date 2022-12-03
 */
@Api(tags = "系统：角色管理")
@RestController
@RequestMapping("/api/sys/roles")
public class RoleController {

    private final IRoleService roleService;
    private final IAccountService accountService;
    private final IGenerator generator;

    private static final String ENTITY_NAME = "role";

    public RoleController(IRoleService roleService, IAccountService accountService, IGenerator generator) {
        this.roleService = roleService;
        this.accountService = accountService;
        this.generator = generator;
    }

    @ApiOperation("获取单个role")
    @GetMapping(value = "/{id}")
    @PreAuthorize("@el.check('roles:list')")
    public ResponseEntity<Object> getRoles(@PathVariable String coder) {
        return new ResponseEntity<>(roleService.findByCode(coder), HttpStatus.OK);
    }

    @Log("导出角色数据")
    @ApiOperation("导出角色数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('role:list')")
    public void download(HttpServletResponse response, RoleQueryCriteria criteria) throws IOException {
        roleService.download(generator.convert(roleService.queryAll(criteria), RoleDto.class), response);
    }

    @ApiOperation("返回全部的角色")
    @GetMapping(value = "/all")
    @PreAuthorize("@el.check('roles:list','user:add','user:edit')")
    public ResponseEntity<Object> getAll(RoleQueryCriteria criteria, @PageableDefault(value = 2000, sort = {"level"}, direction = Sort.Direction.ASC) Pageable pageable) {
        return new ResponseEntity<>(roleService.queryAlls(criteria, pageable), HttpStatus.OK);
    }

    @Log("查询角色")
    @ApiOperation("查询角色")
    @GetMapping
    @PreAuthorize("@el.check('roles:list')")
    public ResponseEntity<Object> getRoles(RoleQueryCriteria criteria, Pageable pageable) {
        return new ResponseEntity<>(roleService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @ApiOperation("获取用户级别")
    @GetMapping(value = "/level")
    public ResponseEntity<Object> getLevel() {
        return new ResponseEntity<>(Dict.create().set("level", getLevels(null)), HttpStatus.OK);
    }

    @ForbidSubmit
    @Log("新增角色")
    @ApiOperation("新增角色")
    @PostMapping
    @PreAuthorize("@el.check('roles:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody Role resources) {

        if (resources.getCoder() != null) {
            throw new BadRequestException("A new " + ENTITY_NAME + " cannot already have an ID");
        }
        getLevels(resources.getLevel());
        return new ResponseEntity<>(roleService.create(resources), HttpStatus.CREATED);
    }

    @ForbidSubmit
    @Log("修改角色")
    @ApiOperation("修改角色")
    @PutMapping
    @PreAuthorize("@el.check('roles:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody Role resources) {

        getLevels(resources.getLevel());
        roleService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ForbidSubmit
    @Log("修改角色菜单")
    @ApiOperation("修改角色菜单")
    @PutMapping(value = "/resource")
    @PreAuthorize("@el.check('roles:edit')")
    public ResponseEntity<Object> updateResource(@RequestBody Role resources) {

        Role role = roleService.findByCode(resources.getCoder());
        getLevels(role.getLevel());
        roleService.update(role);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ForbidSubmit
    @Log("删除角色")
    @ApiOperation("删除角色")
    @DeleteMapping
    @PreAuthorize("@el.check('roles:del')")
    public ResponseEntity<Object> delete(@RequestBody Set<String> coders) {

        for (String coder : coders) {
            Role role = roleService.findByCode(coder);
            getLevels(role.getLevel());
        }
        try {
            roleService.delete(coders);
        } catch (Throwable e) {
            throw new BadRequestException("所选角色存在用户关联，请取消关联后再试");
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 获取用户的角色级别
     * @return /
     */
    private int getLevels(Integer level) {
        Account user = accountService.findByName(SecurityUtils.getAccountName());
        List<Integer> levels = roleService.findByAccountsCode(user.getCoder()).stream().map(RoleSmallDto::getLevel).collect(Collectors.toList());
        int min = Collections.min(levels);
        if (level != null) {
            if (level < min) {
                throw new BadRequestException("权限不足，你的角色级别：" + min + "，低于操作的角色级别：" + level);
            }
        }
        return min;
    }
}
