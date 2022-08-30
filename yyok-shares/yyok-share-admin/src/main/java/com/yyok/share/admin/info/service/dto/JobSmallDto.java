package com.yyok.share.admin.info.service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author yyok
 * @date 2019-6-10 16:32:18
 */
@Data
@NoArgsConstructor
public class JobSmallDto implements Serializable {

    private String coder;

    private String name;
}
