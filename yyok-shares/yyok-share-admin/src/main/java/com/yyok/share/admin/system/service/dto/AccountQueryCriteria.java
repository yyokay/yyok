package com.yyok.share.admin.system.service.dto;

import com.yyok.share.framework.mapper.annotation.Query;
import lombok.Data;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * @author yyok
 * @date 2020-05-14
 */
@Data
public class AccountQueryCriteria {

    @Query
    private String coder;

    @Query(propName = "deptCode", type = Query.Type.IN)
    private Set<String> deptCodes;

    @Query(blurry = "email,username,nickName")
    private String blurry;

    @Query
    private String deptCode;

    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> createTime;
}
