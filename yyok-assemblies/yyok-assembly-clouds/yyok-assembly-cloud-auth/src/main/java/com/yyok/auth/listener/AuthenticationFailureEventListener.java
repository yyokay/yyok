package com.yyok.auth.listener;

import com.yyok.auth.util.IPUtil;
import com.yyok.share.framework.utils.RequestHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Component
public class AuthenticationFailureEventListener implements ApplicationListener<AbstractAuthenticationFailureEvent> {

    @Override
    public void onApplicationEvent(AbstractAuthenticationFailureEvent event) {
        HttpServletRequest httpServletRequest = RequestHolder.getHttpServletRequest();
        String ipAddr = IPUtil.getIpAddr(httpServletRequest);
        log.info("{}登录失败:地址{}", event.getAuthentication().getPrincipal(), ipAddr);
    }
}
