package com.yyok.share.framework.mapper.datascope;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.annotation.*;

/**
 * 数据权限查询参数 code
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataScope {

    /**
     * 表的字段
     */
    private String permissionScopeCode = "permission_by";

    /**
     * 表的字段
     */
    private String tenantScopeCode = "tenant_by";

    /**
     * 表的字段
     */
    private String systemScopeCode = "system_by";

    /**
     * 表的字段
     */
    private String typeScopeCode = "type_by";

    /**
     * 表的字段
     */
    private String areaScopeCode = "area_by";

    /**
     * 表的字段
     */
    private String groupScopeCode = "group_by";

    /**
     * 表的字段
     */
    private String companyScopeCode = "company_by";

    /**
     * 表的部门字段
     */
    private String deptScopeCode = "dept_by";

    /**
     * 表的用户字段
     */
    private String accountScopeCode = "create_by";

}
