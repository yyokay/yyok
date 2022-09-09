package com.yyok.share.admin.mnt.service.dto;

import com.yyok.share.framework.mapper.annotation.Query;
import lombok.Data;
import java.sql.Timestamp;
import java.util.List;

@Data
public class ServerDeployQueryCriteria{

	/**
	 * 模糊
	 */
	@Query(blurry = "name,ip,account")
    private String blurry;

	@Query(type = Query.Type.BETWEEN)
	private List<Timestamp> createTime;
}
