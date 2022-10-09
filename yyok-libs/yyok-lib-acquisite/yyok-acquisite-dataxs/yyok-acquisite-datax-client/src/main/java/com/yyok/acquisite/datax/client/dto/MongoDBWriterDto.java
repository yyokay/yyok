package com.yyok.acquisite.datax.client.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 构建mongodb write dto
 *
 */
@Data
public class MongoDBWriterDto implements Serializable {

    private UpsertInfo upsertInfo;

}
