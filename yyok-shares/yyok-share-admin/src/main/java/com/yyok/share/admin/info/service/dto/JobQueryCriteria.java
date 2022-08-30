package com.yyok.share.admin.info.service.dto;

import com.yyok.share.framework.mapper.annotation.Query;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

/**
 * @author yyok
 * @date 2019-6-4 14:49:34
 */
@Data
@NoArgsConstructor
public class JobQueryCriteria {

    @Query(type = Query.Type.INNER_LIKE)
    private String name;

    @Query
    private String deptCode;

    @Query(propName = "deptCode", type = Query.Type.IN)
    private Set<String> deptCodes;

    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> createTime;
}
