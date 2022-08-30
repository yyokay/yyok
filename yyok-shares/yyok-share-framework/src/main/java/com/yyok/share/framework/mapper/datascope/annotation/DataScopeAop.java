package com.yyok.share.framework.mapper.datascope.annotation;

import java.lang.annotation.*;

/**
 * 数据权限过滤注解
 * 参考 https://gitee.com/cancerGit/Crown
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface DataScopeAop {

    /**
     * 表的别名
     */
    String alias() default "account_by";

    /**
     * 表的字段
     */
    String permissionScopeCode() default "permission_by";

    /**
     * 表的字段
     */
    String tenantScopeCode() default "tenant_by";

    /**
     * 表的字段
     */
    String systemScopeCode() default "system_by";

    /**
     * 表的字段
     */
    String typeScopeCode() default "type_by";

    /**
     * 表的字段
     */
    String areaScopeCode() default "area_by";

    /**
     * 表的字段
     */
    String groupScopeCode() default "group_by";

    /**
     * 表的字段
     */
    String companyScopeCode() default "company_by";

    /**
     * 表的部门字段
     */
    String deptScopeCode() default "dept_by";

    /**
     * 表的用户字段
     */
    String accountScopeCode() default "create_by";
}
