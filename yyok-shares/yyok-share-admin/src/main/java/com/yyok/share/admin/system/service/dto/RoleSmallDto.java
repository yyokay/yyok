package com.yyok.share.admin.system.service.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yyok
 * @date 2022-05-23
 */
@Data
public class RoleSmallDto implements Serializable {

    private String coder;

    private String name;

    private Integer level;

    private String dataScope;

}
