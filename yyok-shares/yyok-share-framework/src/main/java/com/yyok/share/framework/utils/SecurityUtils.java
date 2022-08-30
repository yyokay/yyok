package com.yyok.share.framework.utils;

import cn.hutool.json.JSONObject;
import com.yyok.share.framework.exception.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * 获取当前登录的用户
 *
 * @author yyok
 * @date 2019-01-17
 */
public class SecurityUtils {

    public static UserDetails getUserDetails() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new BadRequestException(HttpStatus.UNAUTHORIZED, "当前登录状态过期");
        }
        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return (UserDetails) authentication.getPrincipal();
        } else if ("anonymousUser".equals(authentication.getPrincipal())) {
            throw new BadRequestException(HttpStatus.UNAUTHORIZED, "找不到当前登录的信息");
        } else {
            UserDetailsService userDetailsService = SpringContextHolder.getBean(UserDetailsService.class);
            return userDetailsService.loadUserByUsername(authentication.getName());
        }

    }

    /**
     * 获取系统用户名称
     * @return 系统用户名称
     */
    public static UserDetails getAccount() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new BadRequestException(HttpStatus.UNAUTHORIZED, "当前登录状态过期");
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails;
    }

    /**
     * 获取系统用户Code
     *
     * @return 系统用户Code
     */
    public static String getAccountCode() {
        Object obj = getUserDetails();
        JSONObject json = new JSONObject(obj);
        return json.get("coder", String.class);
    }

    public static String getAccountName() {
        Object obj = getUserDetails();
        JSONObject json = new JSONObject(obj);
        return json.get("account", String.class);
    }

    public static String getAccountPhone() {
        Object obj = getUserDetails();
        JSONObject json = new JSONObject(obj);
        return json.get("phone", String.class);
    }

    public static String getAccountAEnabled() {
        Object obj = getUserDetails();
        JSONObject json = new JSONObject(obj);
        return json.get("enabled", String.class);
    }

    public static byte[] hexToByte(String substring) {
        return substring.getBytes();
    }

    public static String byteToHex(byte[] encryptedData) {
        return new String(encryptedData);
    }
}
