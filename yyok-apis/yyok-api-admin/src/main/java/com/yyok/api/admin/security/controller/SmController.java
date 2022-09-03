package com.yyok.api.admin.security.controller;

import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.google.gson.Gson;
import com.wf.captcha.ArithmeticCaptcha;
import com.yyok.share.admin.logging.annotation.Log;
import com.yyok.share.admin.security.domain.TokenUtil;
import com.yyok.share.admin.security.domain.vo.AuthAccount;
import com.yyok.share.admin.security.properties.SecurityProperties;
import com.yyok.share.admin.security.security.vo.JwtAccount;
import com.yyok.share.admin.security.service.OnlineAccountService;
import com.yyok.share.admin.security.service.UserDetailsServiceImpl;
import com.yyok.share.admin.system.domain.vo.AccountRoleVo;
import com.yyok.share.admin.system.domain.vo.RoleAbacVo;
import com.yyok.share.admin.system.domain.vo.RoleAclVo;
import com.yyok.share.admin.system.domain.vo.RoleResourceVo;
import com.yyok.share.admin.system.service.*;
import com.yyok.share.framework.annotation.AnonymousAccess;
import com.yyok.share.framework.exception.BadRequestException;
import com.yyok.share.framework.utils.RedisUtils;
import com.yyok.share.framework.utils.SecurityUtils;
import com.yyok.share.framework.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author yyok
 * @date 2022-05-23
 * 国密SM算法
 */
@Slf4j
@RestController
@RequestMapping("/sys/sm")
@Api(tags = "系统：国密SM算法接口")
public class SmController {
    @Value("${loginCode.expiration}")
    private Long expiration;
    @Value("${rsa.private_key}")
    private String privateKey;
    @Value("${single.login}")
    private Boolean singleLogin;
    private final SecurityProperties properties;
    private final RedisUtils redisUtils;
    private final UserDetailsServiceImpl userDetailsServiceImpl;
    private final OnlineAccountService onlineAccountService;
    private final TokenUtil tokenUtil;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final IRoleService roleService;
    private final IAccountsRolesService accountsRolesService;
    private final IRolesResourcesService roleResourceService;
    private final IRolesAclService rolesAclService;
    private final IRolesAbacService rolesAbacService;

    public SmController(SecurityProperties properties, RedisUtils redisUtils, UserDetailsServiceImpl userDetailsServiceImpl, OnlineAccountService onlineAccountService, TokenUtil tokenUtil, AuthenticationManagerBuilder authenticationManagerBuilder, IRoleService roleService, IAccountsRolesService accountsRolesService, IRolesResourcesService roleResourceService, IRolesAclService rolesAclService, IRolesAbacService rolesAbacService) {
        this.properties = properties;
        this.redisUtils = redisUtils;
        this.userDetailsServiceImpl = userDetailsServiceImpl;
        this.onlineAccountService = onlineAccountService;
        this.tokenUtil = tokenUtil;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.roleService = roleService;
        this.accountsRolesService = accountsRolesService;
        this.roleResourceService = roleResourceService;
        this.rolesAclService = rolesAclService;
        this.rolesAbacService = rolesAbacService;
    }

    @Log("集中认证--")
    @ApiOperation("集中认证--")
    @AnonymousAccess
    @PostMapping(value = "/authentication")
    public ResponseEntity<Object> doAuthentication(@Validated @RequestBody AuthAccount authAccount, HttpServletRequest request) {
        //私钥



        // 密码解密
        RSA rsa = new RSA(privateKey, null);
        String password = new String(rsa.decrypt(authAccount.getPassword(), KeyType.PrivateKey));

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(authAccount.getUsername(), password);
        //身份验证; 认证；鉴定;
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        // 生成令牌
        final UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = tokenUtil.generateToken(userDetails);

        // 返回 token 与 认证信息
        Map<String, Object> authenticationInfo = new HashMap<String, Object>(2) {{
            put("token", properties.getTokenStartWith() + token);
            put("authentication", authentication);

        }};
        if (singleLogin) {
            //踢掉之前已经登录的token
            onlineAccountService.checkLoginOnUser(authAccount.getUsername(), token,authAccount.getPassword());
        }
        return ResponseEntity.ok(authenticationInfo);
    }

    /**
     [{
     "accountCode": "001",
     "roleCode": "100"
     },  {
     "accountCode": "002",
     "roleCode": "200"
     },  {
     "accountCode": "003",
     "roleCode": "300"
     },  {
     "accountCode": "004",
     "roleCode": "400"
     },  {
     "accountCode": "005",
     "roleCode": "500"
     },  {
     "accountCode": "006",
     "roleCode": "600"
     }
     ]
     */
    @Log("集中授权(ROLE)admin authority granted >> account : Role  = m:n")
    @ApiOperation("SM2公钥加密算法国密公钥加密标准")
    @AnonymousAccess
    @PostMapping(value = "/2")
    public ResponseEntity<Object> sm2(@Validated @RequestBody List<AccountRoleVo> accountsRoles, HttpServletRequest request) {
        // admin authority granted >> account : Role  = m:n
        return ResponseEntity.ok(accountsRolesService.grantAuthorization(accountsRoles));
    }

    @Log("集中授权(RESOURCE)admin authority granted >> Role : Resource  = m:n")
    @ApiOperation("SM2公钥加密算法国密公钥加密标准")
    @PostMapping(value = "/3")
    public ResponseEntity<Object> sm3(@Validated @RequestBody List<RoleResourceVo> roleResourceVo, HttpServletRequest request) {
        // admin authority granted >> resource : Role  = m:n
        return ResponseEntity.ok(roleResourceService.grantAuthorizater(roleResourceVo));
    }

    @Log("集中授权(ACL)admin authority granted >> Role : acl  = m:n")
    @ApiOperation("集中授权(ACL)")
    @PostMapping(value = "/4")
    public ResponseEntity<Object> sm4(@Validated @RequestBody List<RoleAclVo> roleAclVo, HttpServletRequest request) {
        // admin authority granted >> acl : Role  = m:n
        return ResponseEntity.ok(rolesAclService.grantAuthorizater(roleAclVo));
    }

    @Log("集中授权(ABAC)admin authority granted >> Role : abac  = m:n")
    @ApiOperation("集中授权(ABAC)")
    @PostMapping(value = "/9")
    public ResponseEntity<Object> sm9(@Validated @RequestBody List<RoleAbacVo> roleAbacVo, HttpServletRequest request) {
        // admin authority granted >> abac : Role  = m:n
        return ResponseEntity.ok(rolesAbacService.grantAuthorizater(roleAbacVo));
    }


}
