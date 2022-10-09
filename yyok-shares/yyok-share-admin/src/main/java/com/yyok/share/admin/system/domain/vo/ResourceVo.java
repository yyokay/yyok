package com.yyok.share.admin.system.domain.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 构建前端路由时用到
 *
 * @author yyok
 * @date 2022-12-20
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ResourceVo implements Serializable {

    private String name;

    private String path;

    private Boolean hidden;

    private String redirect;

    private String component;

    private Boolean alwaysShow;

    private ResourceMetaVo meta;

    private List<ResourceVo> children;

    private String url;

    private String uri;

    private String method;
}
