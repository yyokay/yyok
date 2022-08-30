package com.yyok.share.admin.quartz.service.dto;

import com.yyok.share.framework.mapper.annotation.Query;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author yyok
 * @date 2020-05-13
 */
@Data
public class QuartzJobQueryCriteria {

    @Query(type = Query.Type.INNER_LIKE)
    private String jobName;

    @Query
    private Boolean isSuccess;

    @Query
    private Boolean isPause;

    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> createTime;
}
