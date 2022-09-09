package com.yyok.share.admin.mnt.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yyok.share.framework.domain.Domain;
import io.swagger.annotations.ApiModelProperty;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import lombok.Data;
import java.io.Serializable;

@Data
@TableName("mnt_info_server")
public class Server extends Domain implements Serializable {


	@ApiModelProperty(value = "名称")
    private String name;

	@ApiModelProperty(value = "端口")
	private int port;

	@ApiModelProperty(value = "上传路径")
	private String uploadPath;

	@ApiModelProperty(value = "部署路径")
	private String deployPath;

	@ApiModelProperty(value = "备份路径")
	private String backupPath;

	@ApiModelProperty(value = "启动脚本")
	private String startScript;

	@ApiModelProperty(value = "部署脚本")
	private String deployScript;

	@ApiModelProperty(value = "版本")
	private String version;

	@ApiModelProperty(value = "拥有")
	private String ownerCode;


    public void copy(Server source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
