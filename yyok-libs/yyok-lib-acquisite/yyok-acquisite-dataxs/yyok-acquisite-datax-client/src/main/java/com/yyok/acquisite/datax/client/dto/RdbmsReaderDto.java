package com.yyok.acquisite.datax.client.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 构建json dto
 *
 */
@Data
public class RdbmsReaderDto implements Serializable {

    private String readerSplitPk;

    private String whereParams;

    private String querySql;
}
