package com.yyok.api.admin.security.controller;

import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.google.gson.Gson;
import com.wf.captcha.ArithmeticCaptcha;
import com.yyok.share.admin.security.domain.TokenUtil;
import com.yyok.share.admin.security.domain.vo.AuthAccount;
import com.yyok.share.admin.security.security.vo.JwtAccount;
import com.yyok.share.admin.security.properties.SecurityProperties;
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
import com.yyok.share.admin.logging.annotation.Log;
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
 * 授权、(authorization)根据token获取用户详细信息
 */
@Slf4j
@RestController
@RequestMapping("/sys/auth")
@Api(tags = "系统：集中授权.集中认证接口")
public class AuthController {
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

    public AuthController(SecurityProperties properties, RedisUtils redisUtils, UserDetailsServiceImpl userDetailsServiceImpl, OnlineAccountService onlineAccountService, TokenUtil tokenUtil, AuthenticationManagerBuilder authenticationManagerBuilder, IRoleService roleService, IAccountsRolesService accountsRolesService, IRolesResourcesService roleResourceService, IRolesAclService rolesAclService, IRolesAbacService rolesAbacService) {
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
    @ApiOperation("集中授权(ROLE)")
    @AnonymousAccess
    @PostMapping(value = "/authorization/role")
    public ResponseEntity<Object> doAuthorization(@Validated @RequestBody List<AccountRoleVo> accountsRoles, HttpServletRequest request) {
        // admin authority granted >> account : Role  = m:n
        return ResponseEntity.ok(accountsRolesService.grantAuthorization(accountsRoles));
    }

    @Log("集中授权(RESOURCE)admin authority granted >> Role : Resource  = m:n")
    @ApiOperation("集中授权(RESOURCE)")
    @PostMapping(value = "/authorization/resource")
    public ResponseEntity<Object> doAuthorizationResource(@Validated @RequestBody List<RoleResourceVo> roleResourceVo, HttpServletRequest request) {
        // admin authority granted >> resource : Role  = m:n
        return ResponseEntity.ok(roleResourceService.grantAuthorizater(roleResourceVo));
    }

    @Log("集中授权(ACL)admin authority granted >> Role : acl  = m:n")
    @ApiOperation("集中授权(ACL)")
    @PostMapping(value = "/authorization/acl")
    public ResponseEntity<Object> doAuthorizationAcl(@Validated @RequestBody List<RoleAclVo> roleAclVo, HttpServletRequest request) {
        // admin authority granted >> acl : Role  = m:n
        return ResponseEntity.ok(rolesAclService.grantAuthorizater(roleAclVo));
    }

    @Log("集中授权(ABAC)admin authority granted >> Role : abac  = m:n")
    @ApiOperation("集中授权(ABAC)")
    @PostMapping(value = "/authorization/abac")
    public ResponseEntity<Object> doAuthorizationAbac(@Validated @RequestBody List<RoleAbacVo> roleAbacVo, HttpServletRequest request) {
        // admin authority granted >> abac : Role  = m:n
        return ResponseEntity.ok(rolesAbacService.grantAuthorizater(roleAbacVo));
    }

    @Log("集中授权-校验(verification)admin authority granted >> account : Role  = m:n")
    @ApiOperation("集中授权-校验(verification)")
    @AnonymousAccess
    @PostMapping(value = "/authorization/verification")
    public ResponseEntity<Object> doAuthorizationVerification(@Validated @RequestBody HashMap hs, HttpServletRequest request) {
        // admin authority granted >> account : Role  = m:n
        return ResponseEntity.ok(accountsRolesService.verification(hs));
    }

    @Log("用户登录")
    @ApiOperation("登录授权")
    @AnonymousAccess
    @PostMapping(value = "/login")
    public ResponseEntity<Object> login(@Validated @RequestBody AuthAccount authAccount, HttpServletRequest request) {
        // 密码解密
        RSA rsa = new RSA(privateKey, null);
        String password = new String(rsa.decrypt(authAccount.getPassword(), KeyType.PrivateKey));
        // 查询验证码
        String code = (String) redisUtils.get(authAccount.getUuid());
        // 清除验证码
        redisUtils.del(authAccount.getUuid());
        if (StringUtils.isBlank(code)) {
            throw new BadRequestException("验证码不存在或已过期");
        }
        if (StringUtils.isBlank(authAccount.getCode()) || !authAccount.getCode().equalsIgnoreCase(code)) {
            throw new BadRequestException("验证码错误");
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(authAccount.getUsername(), password);
        //身份验证; 认证；鉴定;
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        // 生成令牌
        final UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = tokenUtil.generateToken(userDetails);

        final JwtAccount jwtAccount = (JwtAccount) authentication.getPrincipal();
        jwtAccount.setToken(token);

        //jwtAccount.setAuthentication(authentication);
        //授权  granted authority
        // 保存在线信息
        onlineAccountService.save(jwtAccount, token, request);
        // 返回 token 与 用户信息
        Map<String, Object> authInfo = new HashMap<String, Object>(2) {{
            put("token", properties.getTokenStartWith() + token);
            put("account", new Gson().toJson(jwtAccount));

        }};
        if (singleLogin) {
            //踢掉之前已经登录的token
            onlineAccountService.checkLoginOnUser(authAccount.getUsername(), token,authAccount.getPassword());
        }
        return ResponseEntity.ok(authInfo);
    }

    @Log("用户登录-Test")
    @ApiOperation("登录授权-Test")
    @AnonymousAccess
    @PostMapping(value = "/loginTest")
    public ResponseEntity<Object> loginTest(@Validated @RequestBody AuthAccount authAccount, HttpServletRequest request) {
        // 密码解密
        RSA rsa = new RSA(privateKey, null);
        String pwd = authAccount.getPassword();
        byte[] pwds = null;
        try {
            pwds = rsa.decrypt(pwd, KeyType.PrivateKey);
        }catch (Exception e){
            pwds = pwd.getBytes();
        }
        String password = new String(pwds);
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(authAccount.getUsername(), password);

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // 生成令牌
        final UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = tokenUtil.generateToken(userDetails);
        final JwtAccount jwtAccount = (JwtAccount) authentication.getPrincipal();
        jwtAccount.setToken(token);
        // 保存在线信息
        onlineAccountService.save(jwtAccount, token, request);
        // 返回 token 与 用户信息
        Map<String, Object> authInfo = new HashMap<String, Object>(2) {{
            put("token", properties.getTokenStartWith() + token);
            put("account", new Gson().toJson(jwtAccount));
        }};

        if (singleLogin) {
            //踢掉之前已经登录的token
            onlineAccountService.checkLoginOnUser(authAccount.getUsername(), token,authAccount.getPassword());
        }
        request.setAttribute(jwtAccount.getCoder(), jwtAccount);
        return ResponseEntity.ok(authInfo);
    }

    @ApiOperation("获取用户信息")
    @GetMapping(value = "/info")
    public ResponseEntity<Object> getAccountInfo() {

        JwtAccount jwtAccount = (JwtAccount) userDetailsServiceImpl.loadUserByUsername(SecurityUtils.getAccountName());
        String token = tokenUtil.generateToken(SecurityUtils.getUserDetails());
        jwtAccount.setToken(token);
        return ResponseEntity.ok(jwtAccount);
    }

    @AnonymousAccess
    @ApiOperation("获取验证码")
    @GetMapping(value = "/code")
    public ResponseEntity<Object> getCode() {
        // 算术类型 https://gitee.com/whvse/EasyCaptcha
        ArithmeticCaptcha captcha = new ArithmeticCaptcha(111, 36);
        // 几位数运算，默认是两位
        captcha.setLen(2);
        // 获取运算的结果
        String result = "";
        try {
            result = new Double(Double.parseDouble(captcha.text())).intValue() + "";
        } catch (Exception e) {
            result = captcha.text();
        }
        String uuid = properties.getCodeKey() + IdUtil.simpleUUID();
        // 保存
        redisUtils.set(uuid, result, expiration, TimeUnit.MINUTES);
        // 验证码信息
        Map<String, Object> imgResult = new HashMap<String, Object>(2) {{
            put("img", captcha.toBase64());
            put("uuid", uuid);
        }};
        return ResponseEntity.ok(imgResult);
    }

    @ApiOperation("退出登录")
    @AnonymousAccess
    @DeleteMapping(value = "/logout")
    public ResponseEntity<Object> logout(HttpServletRequest request) {
        onlineAccountService.logout(tokenUtil.getToken(request));
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
