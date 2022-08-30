package com.yyok.share.admin.security.service;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.yyok.share.admin.security.properties.SecurityProperties;
import com.yyok.share.admin.security.security.vo.JwtAccount;
import com.yyok.share.admin.security.domain.vo.OnlineAccount;
import com.yyok.share.framework.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Service
@Slf4j
public class OnlineAccountService {

    private final SecurityProperties properties;
    private final RedisUtils redisUtils;

    public OnlineAccountService(SecurityProperties properties, RedisUtils redisUtils) {
        this.properties = properties;
        this.redisUtils = redisUtils;
    }

    /**
     * 保存在线用户信息
     *
     * @param jwtAccount /
     * @param token      /
     * @param request    /
     */
    public void save(JwtAccount jwtAccount, String token, HttpServletRequest request) {
        String job = "";//jwtAccount.getUser().getDept() + "/" + jwtAccount.getUser().getJob();
        String ip = StringUtils.getIp(request);
        String browser = StringUtils.getBrowser(request);
        String address = StringUtils.getCityInfo(ip);
        OnlineAccount onlineAccount = null;
        try {
            onlineAccount = new OnlineAccount(jwtAccount.getUsername(), jwtAccount.getNickName(), jwtAccount.getCoder(), job, browser, ip, address, EncryptUtils.desEncrypt(token,jwtAccount.getPassword()), new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }
        redisUtils.set(properties.getOnlineKey() + token, onlineAccount, properties.getTokenValidityInSeconds() / 1000);
    }

    /**
     * 查询全部数据
     *
     * @param filter   /
     * @param pageable /
     * @return /
     */
    public Map<String, Object> getAll(String filter, int type, Pageable pageable) {
        List<OnlineAccount> onlineAccounts = getAll(filter, type);
        return PageUtil.toPage(
                PageUtil.toPage(pageable.getPageNumber(), pageable.getPageSize(), onlineAccounts),
                onlineAccounts.size()
        );
    }

    /**
     * 查询全部数据，不分页
     *
     * @param filter /
     * @return /
     */
    public List<OnlineAccount> getAll(String filter, int type) {
        List<String> keys = null;
        if (type == 1) {
            keys = redisUtils.scan("online-token*");
        } else {
            keys = redisUtils.scan(properties.getOnlineKey() + "*");
        }
        Collections.reverse(keys);
        List<OnlineAccount> onlineAccounts = new ArrayList<>();
        for (String key : keys) {
            LinkedTreeMap lt = (LinkedTreeMap) redisUtils.get(key);
            Gson gson = new Gson();
            String jsonString = gson.toJson(lt);
            OnlineAccount onlineAccount = gson.fromJson(jsonString, OnlineAccount.class);
            if (StringUtils.isNotBlank(filter)) {
                if (onlineAccount.toString().contains(filter)) {
                    onlineAccounts.add(onlineAccount);
                }
            } else {
                onlineAccounts.add(onlineAccount);
            }
            onlineAccount = null;
        }
        onlineAccounts.sort((o1, o2) -> o2.getLoginTime().compareTo(o1.getLoginTime()));
        return onlineAccounts;
    }

    /**
     * 踢出用户
     *
     * @param key /
     * @throws Exception /
     */
    public void kickOut(String key,String strKey) throws Exception {
        key = properties.getOnlineKey() + EncryptUtils.desDecrypt(key,strKey);
        redisUtils.del(key);

    }

    /**
     * 踢出移动端用户
     *
     * @param key /
     * @throws Exception /
     */
    public void kickOutT(String key,String strKey) throws Exception {

        String keyt = "online-token" + EncryptUtils.desDecrypt(key, strKey);
        redisUtils.del(keyt);

    }

    /**
     * 退出登录
     *
     * @param token /
     */
    public void logout(String token) {
        String key = properties.getOnlineKey() + token;
        redisUtils.del(key);
    }

    /**
     * 导出
     *
     * @param all      /
     * @param response /
     * @throws IOException /
     */
    public void download(List<OnlineAccount> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (OnlineAccount user : all) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("用户名", user.getUserName());
            map.put("用户昵称", user.getNickName());
            map.put("登录IP", user.getIp());
            map.put("登录地点", user.getAddress());
            map.put("浏览器", user.getBrowser());
            map.put("登录日期", user.getLoginTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    /**
     * 查询用户
     *
     * @param key /
     * @return /
     */
    public OnlineAccount getOne(String key) {
        Gson gson = new Gson();
        String jsonString = gson.toJson(redisUtils.get(key));
        OnlineAccount bean = gson.fromJson(jsonString, OnlineAccount.class);
        return bean;
    }

    /**
     * 检测用户是否在之前已经登录，已经登录踢下线
     *
     * @param userName 用户名
     */
    public void checkLoginOnUser(String userName, String igoreToken, String strKey) {
        List<OnlineAccount> onlineAccounts = getAll(userName, 0);
        if (onlineAccounts == null || onlineAccounts.isEmpty()) {
            return;
        }
        for (OnlineAccount onlineAccount : onlineAccounts) {
            if (onlineAccount.getUserName().equals(userName)) {
                try {
                    String token = EncryptUtils.desDecrypt(onlineAccount.getKey(),strKey);
                    if (StringUtils.isNotBlank(igoreToken) && !igoreToken.equals(token)) {
                        this.kickOut(onlineAccount.getKey(),strKey);
                    } else if (StringUtils.isBlank(igoreToken)) {
                        this.kickOut(onlineAccount.getKey(), strKey);
                    }
                } catch (Exception e) {
                    log.error("checkUser is error", e);
                }
            }
        }
    }

}
