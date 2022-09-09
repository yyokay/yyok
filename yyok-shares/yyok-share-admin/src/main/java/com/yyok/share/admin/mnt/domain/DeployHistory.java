package com.yyok.share.admin.mnt.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yyok.share.framework.domain.Domain;
import io.swagger.annotations.ApiModelProperty;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@TableName("mnt_deploy_history")
public class DeployHistory extends Domain implements Serializable {

    @ApiModelProperty(value = "应用名称")
    private String appName;

	@ApiModelProperty(value = "IP")
    private String ip;

	@ApiModelProperty(value = "部署者")
    private String deployUser;

	@ApiModelProperty(value = "部署ID")
	private Long deployId;

    public void copy(DeployHistory source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
