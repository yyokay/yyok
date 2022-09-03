package com.yyok.share.admin.logging.aspect;

import com.yyok.share.admin.logging.domain.Log;
import com.yyok.share.admin.logging.service.ILogService;
import com.yyok.share.framework.utils.RequestHolder;
import com.yyok.share.framework.utils.SecurityUtils;
import com.yyok.share.framework.utils.StringUtils;
import com.yyok.share.framework.utils.ThrowableUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yyok
 * @date 2022-05-24
 */
@Component
@Aspect
@Slf4j
public class LogAspect {

    private final ILogService logService;

    private final KafkaTemplate kafkaTemplate;
    ThreadLocal<Long> currentTime = new ThreadLocal<>();

    public LogAspect(ILogService logService, KafkaTemplate kafkaTemplate) {
        this.logService = logService;
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * 配置切入点
     */
    @Pointcut("@annotation(com.yyok.share.admin.logging.annotation.Log)")
    public void logPointcut() {
        // 该方法无方法体,主要为了让同类中其他方法使用此切入点
    }

    /**
     * 配置环绕通知,使用在方法logPointcut()上注册的切入点
     *
     * @param joinPoint join point for advice
     */
    @Around("logPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result;
        currentTime.set(System.currentTimeMillis());
        result = joinPoint.proceed();
        Log log = new Log("INFO", System.currentTimeMillis() - currentTime.get());
        currentTime.remove();
        HttpServletRequest request = RequestHolder.getHttpServletRequest();
        String accountName = SecurityUtils.getAccountName();
        String accountIp = StringUtils.getIp(RequestHolder.getHttpServletRequest());
        String accountCode = SecurityUtils.getAccountCode();
        String msg = "[INFO-Around],[" + accountIp + "],[" + System.currentTimeMillis() + "],[" + accountCode + "],[" + accountName + "], " + joinPoint;
        kafkaTemplate.send("logs", msg);
        logService.save(accountName, accountIp, joinPoint, log, accountCode);
        return result;
    }

    /**
     * 配置异常通知
     *
     * @param joinPoint join point for advice
     * @param e         exception
     */
    @AfterThrowing(pointcut = "logPointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        Long st = System.currentTimeMillis();
        Log log = new Log("ERROR", st - currentTime.get());
        currentTime.remove();
        log.setExceptionDetail(ThrowableUtil.getStackTrace(e).getBytes());
        HttpServletRequest request = RequestHolder.getHttpServletRequest();
        String accountName = SecurityUtils.getAccountName();
        String accountIp = StringUtils.getIp(RequestHolder.getHttpServletRequest());
        String accountCode = SecurityUtils.getAccountCode();
        String msg = "[ERROR-AfterThrowing],[" + accountIp + "],[" + System.currentTimeMillis() + "],[" + accountCode + "],[" + accountName + "], " + joinPoint;
        kafkaTemplate.send("logs", msg);
        logService.save(accountName, accountIp,
                (ProceedingJoinPoint) joinPoint, log, accountCode);
    }


}
