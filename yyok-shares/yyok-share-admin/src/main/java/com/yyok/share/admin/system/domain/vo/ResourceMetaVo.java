package com.yyok.share.admin.system.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @author yyok
 * @date 2022-12-20
 */
@Data
@AllArgsConstructor
public class ResourceMetaVo implements Serializable {

    private String title;

    private String icon;

    private Boolean noCache;
}
