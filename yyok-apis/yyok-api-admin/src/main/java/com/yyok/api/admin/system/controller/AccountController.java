package com.yyok.api.admin.system.controller;

import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.yyok.share.admin.config.DataScope;
import com.yyok.share.admin.info.service.IDeptService;
import com.yyok.share.admin.system.domain.Account;
import com.yyok.share.admin.system.service.IAccountService;
import com.yyok.share.admin.system.service.IRoleService;
import com.yyok.share.admin.system.service.dto.AccountDto;
import com.yyok.share.admin.system.service.dto.AccountQueryCriteria;
import com.yyok.share.admin.system.service.dto.RoleSmallDto;
import com.yyok.share.framework.annotation.ForbidSubmit;
import com.yyok.share.framework.dozer.service.IGenerator;
import com.yyok.share.framework.exception.BadRequestException;
import com.yyok.share.framework.utils.PageUtil;
import com.yyok.share.framework.utils.SecurityUtils;
import com.yyok.share.admin.logging.annotation.Log;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
@Api(tags = "系统：账户管理")
@RestController
@RequestMapping("/api/sys/accounts")
public class AccountController {

    @Value("${rsa.private_key}")
    private String privateKey;
    private final PasswordEncoder passwordEncoder;

    private final IAccountService accountService;
    private final DataScope dataScope;
    private final IDeptService deptService;
    private final IRoleService roleService;
    //private final VerificationCodeService verificationCodeService;
    private final IGenerator generator;

    public AccountController(PasswordEncoder passwordEncoder, IAccountService accountService, DataScope dataScope, IDeptService deptService, IRoleService roleService, IGenerator generator) {
        this.passwordEncoder = passwordEncoder;
        this.accountService = accountService;
        this.dataScope = dataScope;
        this.deptService = deptService;
        this.roleService = roleService;
        //this.verificationCodeService = verificationCodeService;
        this.generator = generator;
    }

    @Log("导出账户数据")
    @ApiOperation("导出账户数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('admin','account:list')")
    public void download(HttpServletResponse response, AccountQueryCriteria criteria) throws IOException {
        accountService.download(generator.convert(accountService.queryAll(criteria), AccountDto.class), response);
    }

    @Log("查询账户")
    @ApiOperation("查询账户")
    @GetMapping
    @PreAuthorize("@el.check('admin','account:list')")
    public ResponseEntity<Object> getAccounts(AccountQueryCriteria criteria, Pageable pageable) {
        Set<String> deptSet = new HashSet<>();
        Set<String> result = new HashSet<>();
        if (!ObjectUtils.isEmpty(criteria.getDeptCode())) {
            deptSet.add(criteria.getDeptCode());
            deptSet.addAll(dataScope.getDeptChildren(deptService.findByPcode(criteria.getDeptCode())));
        }
        // 数据权限
        Set<String> deptCodes = dataScope.getDeptCodes();
        // 查询条件不为空并且数据权限不为空则取交集
        if (!CollectionUtils.isEmpty(deptCodes) && !CollectionUtils.isEmpty(deptSet)) {
            // 取交集
            result.addAll(deptSet);
            result.retainAll(deptCodes);
            // 若无交集，则代表无数据权限
            criteria.setDeptCodes(result);
            if (result.size() == 0) {
                return new ResponseEntity<>(PageUtil.toPage(null, 0), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(accountService.queryAll(criteria, pageable), HttpStatus.OK);
            }
            // 否则取并集
        } else {
            result.addAll(deptSet);
            result.addAll(deptCodes);
            criteria.setDeptCodes(result);
            return new ResponseEntity<>(accountService.queryAll(criteria, pageable), HttpStatus.OK);
        }
    }

    @ForbidSubmit
    @Log("新增账户")
    @ApiOperation("新增账户")
    @PostMapping
    @PreAuthorize("@el.check('admin','account:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody Account resources) {

        checkLevel(resources);
        // 默认密码 123456
        resources.setPassword(passwordEncoder.encode("123456"));
        return new ResponseEntity<>(accountService.create(resources), HttpStatus.CREATED);
    }

    @ForbidSubmit
    @Log("修改账户")
    @ApiOperation("修改账户")
    @PutMapping
    @PreAuthorize("@el.check('admin','account:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody Account resources) {

        checkLevel(resources);
        accountService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ForbidSubmit
    @Log("修改账户：个人中心")
    @ApiOperation("修改账户：个人中心")
    @PutMapping(value = "center")
    public ResponseEntity<Object> center(@Validated(Account.Update.class) @RequestBody Account resources) {

        Account account = accountService.findByName(SecurityUtils.getAccountName());
        if (!resources.getCoder().equals(account.getCoder())) {
            throw new BadRequestException("不能修改他人资料");
        }
        accountService.saveOrUpdate(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ForbidSubmit
    @Log("删除账户")
    @ApiOperation("删除账户")
    @DeleteMapping
    @PreAuthorize("@el.check('admin','account:del')")
    public ResponseEntity<Object> delete(@RequestBody Set<String> coders) {

        Account account = accountService.findByName(SecurityUtils.getAccountName());
        for (String coder : coders) {
            Integer currentLevel = Collections.min(roleService.findByAccountsCode(account.getCoder()).stream().map(RoleSmallDto::getLevel).collect(Collectors.toList()));
            Integer optLevel = Collections.min(roleService.findByAccountsCode(coder).stream().map(RoleSmallDto::getLevel).collect(Collectors.toList()));
            if (currentLevel > optLevel) {
                throw new BadRequestException("角色权限不足，不能删除：" + accountService.findByName(SecurityUtils.getAccountName()));
            }
        }
        accountService.delete(coders);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @ForbidSubmit
    @ApiOperation("修改头像")
    @PostMapping(value = "/updateAvatar")
    public ResponseEntity<Object> updateAvatar(@RequestParam MultipartFile file) {

        accountService.updateAvatar(file);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ForbidSubmit
    @Log("修改邮箱")
    @ApiOperation("修改邮箱")
    @PostMapping(value = "/updateEmail/{code}")
    public ResponseEntity<Object> updateEmail(@PathVariable String code, @RequestBody Account account) {

        // 密码解密
        RSA rsa = new RSA(privateKey, null);
        String password = new String(rsa.decrypt(account.getPassword(), KeyType.PrivateKey));
        //Account account = accountService.findByName(SecurityUtils.getAccount().getAccount());
        if (!passwordEncoder.matches(password, account.getPassword())) {
            throw new BadRequestException("密码错误");
        }
        //VerificationCode verificationCode = new VerificationCode(code, yyokConstant.RESET_MAIL, "email", account.getEmail());
        //verificationCodeService.validated(verificationCode);
        accountService.updateEmail(account.getAccount(), account.getEmail());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 如果当前账户的角色级别低于创建账户的角色级别，则抛出权限不足的错误
     * @param resources /
     */
    private void checkLevel(Account resources) {
        Account account = accountService.findByName(SecurityUtils.getAccountName());
        Integer currentLevel = Collections.min(roleService.findByAccountsCode(account.getCoder()).stream().map(RoleSmallDto::getLevel).collect(Collectors.toList()));
       Integer optLevel = roleService.findByRoles(resources.getRoles());
        if (currentLevel > optLevel) {
            throw new BadRequestException("角色权限不足");
        }
    }
}
