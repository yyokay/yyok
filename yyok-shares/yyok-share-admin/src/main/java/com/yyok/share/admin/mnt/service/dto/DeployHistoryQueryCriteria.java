package com.yyok.share.admin.mnt.service.dto;

import com.yyok.share.framework.mapper.annotation.Query;
import lombok.Data;
import java.sql.Timestamp;
import java.util.List;

@Data
public class DeployHistoryQueryCriteria{

	/**
	 * 精确
	 */
	@Query(blurry = "appName,ip,deployUser")
	private String blurry;

	@Query
	private Long deployId;

	@Query(type = Query.Type.BETWEEN)
	private List<Timestamp> deployDate;
}
