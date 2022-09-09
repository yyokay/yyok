package com.yyok.share.framework.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;


/**
 * @ClassName 公共模型
 * @Author yyok
 * @Date 2020/10/09
 **/
@Getter
@Setter
public class Domain implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    public int status;
    @TableField(fill = FieldFill.INSERT)
    public String coder;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    public String remark;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    public int enabled;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    public int sorter;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    public int typer;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    public int level;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    public String createBy;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    public String updateBy;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    public String systemBy;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    public String tenantBy;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    public String groupBy;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private int isDel;
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @TableField(fill = FieldFill.UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;


}
