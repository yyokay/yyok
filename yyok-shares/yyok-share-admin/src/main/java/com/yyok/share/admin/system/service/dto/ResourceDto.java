package com.yyok.share.admin.system.service.dto;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * @author yyok
 * @date 2020-05-14
 */
@Data
public class ResourceDto implements Serializable {

    private String coder;

    private int typer;

    private String permission;

    private String name;

    private int sorter;

    private String path;

    private String url;

    private String uri;

    private String method;


    private String component;

    private String pcode;

    private Boolean iFrame;

    private Boolean cache;

    private Boolean hidden;

    private String componentName;

    private String icon;

    private List<ResourceDto> children;

    private Timestamp createTime;
}
