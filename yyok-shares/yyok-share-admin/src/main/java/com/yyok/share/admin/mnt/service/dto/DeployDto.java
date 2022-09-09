package com.yyok.share.admin.mnt.service.dto;

import cn.hutool.core.collection.CollectionUtil;
import com.yyok.share.framework.domain.Domain;
import lombok.Data;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;


@Data
public class DeployDto  extends Domain implements Serializable {

	/**
	 * 部署编号
	 */

	private ServerDto app;

	/**
	 * 服务器
	 */
	private Set<ServerDeployDto> deploys;

	private String servers;


	public String getServers() {
		if(CollectionUtil.isNotEmpty(deploys)){
			return deploys.stream().map(ServerDeployDto::getName).collect(Collectors.joining(","));
		}
		return servers;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		DeployDto deployDto = (DeployDto) o;
		return Objects.equals(coder, deployDto.coder);
	}

	@Override
	public int hashCode() {
		return Objects.hash(coder);
	}
}
