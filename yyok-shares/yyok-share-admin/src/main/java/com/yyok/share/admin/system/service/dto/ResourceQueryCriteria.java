package com.yyok.share.admin.system.service.dto;

import com.yyok.share.framework.mapper.annotation.Query;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author yyok
 * @date 2020-05-14
 */
@Data
public class ResourceQueryCriteria {

    @Query(blurry = "name")
    private String blurry;

    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> createTime;
}
