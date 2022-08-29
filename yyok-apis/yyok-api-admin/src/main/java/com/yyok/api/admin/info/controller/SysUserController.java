package com.yyok.api.admin.info.controller;

import com.yyok.share.admin.config.DataScope;
import com.yyok.share.admin.info.domain.User;
import com.yyok.share.admin.info.service.IDeptService;
import com.yyok.share.admin.info.service.IUserService;
import com.yyok.share.admin.info.service.dto.UserDto;
import com.yyok.share.admin.info.service.dto.UserQueryCriteria;
import com.yyok.share.admin.system.service.IRoleService;
import com.yyok.share.admin.system.service.dto.RoleSmallDto;
import com.yyok.share.framework.annotation.ForbidSubmit;
import com.yyok.share.framework.dozer.service.IGenerator;
import com.yyok.share.framework.exception.BadRequestException;
import com.yyok.share.framework.utils.SecurityUtils;
import com.yyok.share.admin.logging.annotation.Log;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author yyok
 * @date 2022-05-23
 */
@Api(tags = "系统：用户管理")
@RestController
@RequestMapping("/api/sys/users")
public class SysUserController {

    private final IUserService userService;
    private final DataScope dataScope;
    private final IDeptService deptService;
    private final IRoleService roleService;

    private final IGenerator generator;

    public SysUserController(IUserService userService, DataScope dataScope, IDeptService deptService, IRoleService roleService, IGenerator generator) {
        this.userService = userService;
        this.dataScope = dataScope;
        this.deptService = deptService;
        this.roleService = roleService;
        this.generator = generator;
    }

    @Log("导出用户数据")
    @ApiOperation("导出用户数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('admin','user:list')")
    public void download(HttpServletResponse response, UserQueryCriteria criteria) throws IOException {
        userService.download(generator.convert(userService.queryAll(criteria), UserDto.class), response);
    }

    @Log("查询用户")
    @ApiOperation("查询用户")
    @GetMapping
    @PreAuthorize("@el.check('admin','user:list')")
    public ResponseEntity<Object> getUsers(UserQueryCriteria criteria, Pageable pageable) {
        Set<String> deptSet = new HashSet<>();
        Set<String> result = new HashSet<>();
        if (!ObjectUtils.isEmpty(criteria.getDeptCode())) {
            deptSet.add(criteria.getDeptCode());
            deptSet.addAll(dataScope.getDeptChildren(deptService.findByPcode(criteria.getDeptCode())));
        }

        result.addAll(deptSet);
        criteria.setDeptCodes(result);
        return new ResponseEntity<>(userService.queryAll(criteria, pageable), HttpStatus.OK);

    }

    @ForbidSubmit
    @Log("新增用户")
    @ApiOperation("新增用户")
    @PostMapping
    @PreAuthorize("@el.check('admin','user:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody User resources) {
        return new ResponseEntity<>(userService.create(resources), HttpStatus.CREATED);
    }

    @ForbidSubmit
    @Log("修改用户")
    @ApiOperation("修改用户")
    @PutMapping
    @PreAuthorize("@el.check('admin','user:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody User resources) {
        userService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ForbidSubmit
    @Log("修改用户：个人中心")
    @ApiOperation("修改用户：个人中心")
    @PutMapping(value = "center")
    public ResponseEntity<Object> center(@Validated(User.Update.class) @RequestBody User resources) {
        User user = userService.findByName(SecurityUtils.getAccountName());
        if (!resources.getCoder().equals(user.getCoder())) {
            throw new BadRequestException("不能修改他人资料");
        }
        userService.saveOrUpdate(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ForbidSubmit
    @Log("删除用户")
    @ApiOperation("删除用户")
    @DeleteMapping
    @PreAuthorize("@el.check('admin','user:del')")
    public ResponseEntity<Object> delete(@RequestBody Set<String> coders) {

        User user = userService.findByName(SecurityUtils.getAccountName());
        for (String coder : coders) {
            Integer currentLevel = Collections.min(roleService.findByAccountsCode(user.getCoder()).stream().map(RoleSmallDto::getLevel).collect(Collectors.toList()));
            Integer optLevel = Collections.min(roleService.findByAccountsCode(coder).stream().map(RoleSmallDto::getLevel).collect(Collectors.toList()));
            if (currentLevel > optLevel) {
                throw new BadRequestException("角色权限不足，不能删除：" + userService.findByName(SecurityUtils.getAccountName()));
            }
        }
        userService.delete(coders);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ForbidSubmit
    @ApiOperation("修改头像")
    @PostMapping(value = "/updateAvatar")
    public ResponseEntity<Object> updateAvatar(@RequestParam MultipartFile file) {

        userService.updateAvatar(file);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
