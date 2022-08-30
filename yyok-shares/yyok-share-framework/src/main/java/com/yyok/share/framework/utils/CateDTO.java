package com.yyok.share.framework.utils;


import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * yyok商品分类
 *
 * @author yyok
 * @since 2019-09-08
 */
@Data
public class CateDTO implements Serializable {

    private static final long serialVersionUID = 1L;


    private String coder;
    /**
     * 上级分类编号
     */
    private String pcode;

    /**
     * 商品分类名称
     */
    private String cateName;

    /**
     * 缩略图url
     */
    private String pic;

    private List<CateDTO> children = new ArrayList<>();

}
