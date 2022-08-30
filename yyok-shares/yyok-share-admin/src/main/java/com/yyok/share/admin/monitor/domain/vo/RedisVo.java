package com.yyok.share.admin.monitor.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author yyok
 * @date 2022-12-10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RedisVo implements Serializable {

    @NotBlank
    private String key;

    @NotBlank
    private String value;
}
