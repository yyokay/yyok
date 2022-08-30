package com.yyok.share.framework.mapper.datascope.aspectj;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.yyok.share.framework.constant.Constant;
import com.yyok.share.framework.mapper.datascope.annotation.DataScopeAop;
import com.yyok.share.framework.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 数据过滤处理（基于注解式,用于自定义sql）
 */
@Slf4j
@Aspect
public class DataScopeAspect {

    // 配置织入点
    @Pointcut("@annotation(com.yyok.share.framework.mapper.datascope.annotation.DataScopeAop)")
    public void dataScopePointCut() {
    }

    @Before("dataScopePointCut()")
    public void doBefore(JoinPoint point) {
        handleDataScope(point);
    }

    protected void handleDataScope(final JoinPoint joinPoint) {
        // 获得注解
        DataScopeAop dataScope = getAnnotationLog(joinPoint);
        if (dataScope == null) {
            return;
        }
        String currentAccount = SecurityUtils.getAccountCode();
        if (null != currentAccount) {
            // 如果是超级管理员，则不过滤数据
            if (!SecurityUtils.getAccountName().equals("admin")) {
                dataScopeFilter(joinPoint, currentAccount, dataScope);
            }
        }
    }

    /**
     * 数据范围过滤
     *
     * @param currentAccount
     * @param dataScope
     */
    private void dataScopeFilter(JoinPoint joinPoint, String currentAccount, DataScopeAop dataScope) {
        StringBuilder sqlString = new StringBuilder();
       /* List<DataRole> roles = currentAccount.getRoles();
        if (CollUtil.isNotEmpty(roles)){
            for (DataRole role : roles){
                String roleDataScope = role.getDataScope();
                if (Constant.DataScope.ALL.getKey().equals(roleDataScope)) {
                    sqlString = new StringBuilder();
                    break;
                } else if (Constant.DataScope.CUSTOM.getKey().equals(roleDataScope)) {
                    sqlString.append(StrUtil.format(
                            " OR {}.{} IN ( SELECT dept_id FROM sys_role_dept WHERE role_id = {} ) "
                            ,dataScope.alias()
                            ,dataScope.deptScopeName()
                            ,"'" + role.getId() + "'"
                    ));
                } else if (Constant.DataScope.DEPT.getKey().equals(roleDataScope)) {
                    sqlString.append(StrUtil.format(
                            " OR {}.{} = {} "
                            ,dataScope.alias()
                            ,dataScope.deptScopeName()
                            ,"'" + user.getDept() + "'"
                    ));
                } else if (Constant.DataScope.DEPTANDCHILD.getKey().equals(roleDataScope)) {
                    sqlString.append(StrUtil.format(
                            " OR {}.{} IN ( SELECT descendant FROM sys_dept_relation WHERE ancestor = {} )"
                            ,dataScope.alias()
                            ,dataScope.deptScopeName()
                            ,"'" + user.getDept() + "'"
                    ));
                } else if (Constant.DataScope.SELF.getKey().equals(roleDataScope)) {
                    if (StrUtil.isNotBlank(dataScope.alias())) {
                        sqlString.append(StrUtil.format(" OR {}.{} = {} "
                                ,dataScope.alias()
                                ,dataScope.userScopeName()
                                ,user.getId()));
                    } else {
                        // 数据权限为仅本人且没有alias别名不查询任何数据
                        sqlString.append(" OR 1=0 ");
                    }
                }
            }
        }
        if (StrUtil.isNotBlank(sqlString.toString())) {
            BaseQueryParams baseQueryParams = (BaseQueryParams) joinPoint.getArgs()[0];
            baseQueryParams.setDataScope(" AND (" + sqlString.substring(4) + ")");
        }*/
        log.info("数据范围过滤:{}", sqlString);
    }

    /**
     * 是否存在注解，如果存在就获取
     */
    private DataScopeAop getAnnotationLog(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        if (method != null) {
            return method.getAnnotation(DataScopeAop.class);
        }
        return null;
    }
}
