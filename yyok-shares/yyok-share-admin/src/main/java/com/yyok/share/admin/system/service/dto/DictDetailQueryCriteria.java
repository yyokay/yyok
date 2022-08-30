package com.yyok.share.admin.system.service.dto;

import com.yyok.share.framework.mapper.annotation.Query;
import lombok.Data;

/**
 * @author yyok
 * @date 2020-05-14
 */
@Data
public class DictDetailQueryCriteria {

    @Query(type = Query.Type.INNER_LIKE)
    private String label;

    private String dictName;
}
