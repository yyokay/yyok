package com.yyok.share.admin.mnt.service.dto;

import com.yyok.share.framework.domain.Domain;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

/**
* @author zhanghouying
* @date 2019-08-24
*/
@Data
public class ServerDeployDto  extends Domain implements Serializable {

    private Long id;

	private String name;

	private String ip;

	private Integer port;

	private String account;

	private String password;

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		ServerDeployDto that = (ServerDeployDto) o;
		return Objects.equals(id, that.id) &&
				Objects.equals(name, that.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name);
	}
}
