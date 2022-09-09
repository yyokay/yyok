package com.yyok.share.admin.mnt.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yyok.share.framework.domain.Domain;
import io.swagger.annotations.ApiModelProperty;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import lombok.Data;
import java.io.Serializable;
import java.util.Objects;


@Data
@TableName("mnt_info_server")
public class ServerDeploy extends Domain implements Serializable {

    @ApiModelProperty(value = "服务器Code")
    private String hostCode;

    @ApiModelProperty(value = "服务Code")
    private String serverCode;

    @ApiModelProperty(value = "IP")
    private String ip;

    @ApiModelProperty(value = "端口")
    private Integer port;

    @ApiModelProperty(value = "账号")
    private String account;

    @ApiModelProperty(value = "密码")
    private String password;

    public void copy(ServerDeploy source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
