package com.yyok.acquisite.datax.client.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 用于启动任务接收的实体
 *
 */
@Data
public class TriggerJobDto implements Serializable {

    private String executorParam;

    private int jobId;
}
