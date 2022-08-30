package com.yyok.share.admin.info.service.dto;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * @author yyok
 * @date 2020-05-14
 */
@Data
public class DictInfoDto implements Serializable {

    /**
     * 字典code
     */
    private String coder;

    /**
     * 字典名称
     */
    private String name;

    private List<DictInfoDetailDto> dictInfoDetails;

    /**
     * 描述
     */
    private String remark;

    /**
     * 创建日期
     */
    private Timestamp createTime;
}
