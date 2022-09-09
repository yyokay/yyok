package com.yyok.share.admin.mnt.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yyok.share.framework.domain.Domain;
import io.swagger.annotations.ApiModelProperty;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import lombok.Data;
import java.io.Serializable;

/**
* @author zhanghouying
* @date 2019-08-24
*/
@Data
@TableName("mnt_info_database")
public class Database extends Domain implements Serializable {


	@ApiModelProperty(value = "数据库名称")
    private String name;

	@ApiModelProperty(value = "数据库连接地址")
    private String jdbcUrl;

	@ApiModelProperty(value = "数据库密码")
    private String pwd;

	@ApiModelProperty(value = "用户名")
    private String userName;

    public void copy(Database source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
