package com.yyok.share.admin.kafka.service.dto;

import lombok.Data;

@Data
public class KafkaQueryCriteria {

    private StringBuffer msg;
    private String topic;
}
