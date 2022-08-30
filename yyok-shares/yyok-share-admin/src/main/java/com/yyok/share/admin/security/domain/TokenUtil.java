package com.yyok.share.admin.security.domain;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.yyok.share.admin.security.properties.SecurityProperties;
import com.yyok.share.admin.security.security.vo.JwtAccount;
import com.yyok.share.framework.utils.RedisUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

/**
 * Token 工具类
 *
 * @author Linqinghong
 */
@Slf4j
@Component
public class TokenUtil {

    /**
     * 权限缓存前缀
     */
    private static final String REDIS_PREFIX_AUTH = "auth:";
    /**
     * 用户信息缓存前缀
     */
    private static final String REDIS_PREFIX_ACCOUNT = "account_";
    @Autowired
    private SecurityProperties properties;
    /**
     * redis repository
     */
    @Autowired
    private RedisUtils redisUtils;

    public static void main(String[] args) {
        String key = Base64.getEncoder().encodeToString("123456".getBytes());
        Claims claims = Jwts.parser().setSigningKey(key)
                .parseClaimsJws("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJhZG1pbiIsInNjb3BlIjpbImFwcCIsIndyaXRlIl0sInVpbiI6MSwiZXhwIjoxNTc1MDE1ODgzLCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwianRpIjoiYjdiYjQ1NTQtNTQ4OS00YTg5LWI3NjQtNzNjODI0YzljNGMyIiwiY2xpZW50X2lkIjoibHZoYWliYW8ifQ.x7QZxRAR1wuX_YNLi6EzRJ1iaKr1rIEUgjtYF0oSx5k").getBody();
        // System.out.println(JSON.toJSONString(claims));
    }

    /**
     * 获取用户名
     *
     * @param token Token
     * @return String
     */

    public String getUsernameFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims != null ? claims.getSubject() : null;
    }

    /**
     * 获取过期时间
     *
     * @param token Token
     * @return Date
     */

    public Date getExpiredFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims != null ? claims.getExpiration() : null;
    }

    /**
     * 获得 Claims
     *
     * @param token Token
     * @return Claims
     */

    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(properties.getSecret())
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            log.warn("getClaimsFromToken exception", e);
            claims = null;
        }
        return claims;
    }

    /**
     * 计算过期时间
     *
     * @return Date
     */
    private Date generateExpired() {
        return new Date(System.currentTimeMillis() + properties.getTokenValidityInSeconds() * 1000);
    }

    /**
     * 判断 Token 是否过期
     *
     * @param token Token
     * @return Boolean
     */

    private Boolean isTokenExpired(String token) {
        Date expirationDate = getExpiredFromToken(token);
        return expirationDate.before(new Date());
    }

    /**
     * 生成 Token
     *
     * @param userDetails 用户信息
     * @return String
     */
    public String generateToken(UserDetails userDetails) {
        JwtAccount jwtAccount = (JwtAccount) userDetails;
        String secret = properties.getSecret();
        String token = Jwts.builder()
                .setSubject(jwtAccount.getAccount())
                .setExpiration(generateExpired())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();

        String key_auth = REDIS_PREFIX_AUTH + jwtAccount.getAccount() + ":" + token;
        redisUtils.set(key_auth, token, properties.getTokenValidityInSeconds() / 1000);
        //存储用户信息
        putUserDetails(userDetails);
        return token;
    }

    /**
     * 验证 Token
     *
     * @param token Token
     * @return Boolean
     */
    public Boolean validateToken(String token) {
        final String username = getUsernameFromToken(token);
        String key = REDIS_PREFIX_AUTH + username + ":" + token;
        Object data = redisUtils.get(key);
        String redisToken = data == null ? null : data.toString();
        return StringUtils.isNotEmpty(token) && !isTokenExpired(token) && token.equals(redisToken);
    }

    /**
     * 移除 Token
     *
     * @param token Token
     */
    public void removeToken(String token) {
        final String username = getUsernameFromToken(token);
        String key = REDIS_PREFIX_AUTH + username + ":" + token;
        redisUtils.del(key);
        delUserDetails(username);
    }

    /**
     * 获得用户信息 Json 字符串
     *
     * @param token Token
     * @return String
     */
    protected String getUserDetailsString(String token) {
        final String username = getUsernameFromToken(token);
        String key = REDIS_PREFIX_ACCOUNT + username;
        LinkedTreeMap data = (LinkedTreeMap) redisUtils.get(key);
        Gson gson = new Gson();
        String jsonString = gson.toJson(data);
        return jsonString;
    }

    /**
     * 获得用户信息
     *
     * @param token Token
     * @return UserDetails
     */
    public UserDetails getUserDetails(String token) {
        String userDetailsString = getUserDetailsString(token);
        if (userDetailsString != null) {
            return new Gson().fromJson(userDetailsString, JwtAccount.class);
        }
        return null;
    }

    /**
     * 存储用户信息
     *
     * @param userDetails 用户信息
     */
    private void putUserDetails(UserDetails userDetails) {
        JwtAccount jwtAccount = (JwtAccount) userDetails;
        String key = REDIS_PREFIX_ACCOUNT + jwtAccount.getAccount();
        redisUtils.set(key, userDetails, properties.getTokenValidityInSeconds() / 1000);
    }

    /**
     * 删除用户信息
     *
     * @param username 用户名
     */
    private void delUserDetails(String username) {
        String key = REDIS_PREFIX_ACCOUNT + username;
        redisUtils.del(key);
    }

    public String getToken(HttpServletRequest request) {
        final String requestHeader = request.getHeader(properties.getHeader());
        if (requestHeader != null && requestHeader.startsWith(properties.getTokenStartWith())) {
            return requestHeader.substring(7);
        } else {

        }
        return null;
    }
}
