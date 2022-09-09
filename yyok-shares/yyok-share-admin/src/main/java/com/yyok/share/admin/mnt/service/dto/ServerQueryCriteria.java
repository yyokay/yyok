package com.yyok.share.admin.mnt.service.dto;

import com.yyok.share.framework.mapper.annotation.Query;
import lombok.Data;
import java.sql.Timestamp;
import java.util.List;

@Data
public class ServerQueryCriteria {

	/**
	 * 模糊
	 */
    @Query(type = Query.Type.INNER_LIKE)
    private String name;

	@Query(type = Query.Type.BETWEEN)
	private List<Timestamp> createTime;
}
