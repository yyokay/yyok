package com.yyok.share.admin.info.service.dto;

import com.yyok.share.framework.mapper.annotation.Query;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

/**
 * @author yyok
 * @date 2020-05-14
 */
@Data
public class DeptQueryCriteria {

    @Query(type = Query.Type.IN, propName = "coder")
    private Set<String> codes;

    @Query(type = Query.Type.INNER_LIKE)
    private String name;

    @Query
    private String pcode;

    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> createTime;
}
