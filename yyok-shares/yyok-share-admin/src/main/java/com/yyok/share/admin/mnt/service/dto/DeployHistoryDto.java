package com.yyok.share.admin.mnt.service.dto;

import com.yyok.share.framework.domain.Domain;
import lombok.Data;
import java.io.Serializable;
import java.sql.Timestamp;

@Data
public class DeployHistoryDto  extends Domain implements Serializable {

	/**
	 * 应用名称
	 */
    private String appName;

	/**
	 * 部署IP
	 */
    private String ip;

	/**
	 * 部署时间
	 */
	private Timestamp deployDate;

	/**
	 * 部署人员
	 */
	private String deployUser;

	/**
	 * 部署编号
	 */
	private Long deployId;
}
