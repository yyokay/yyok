package com.yyok.share.admin.kafka.domain;

import com.google.gson.Gson;
import com.yyok.share.framework.perproty.KafkaPerproty;
import lombok.Data;

@Data
public class Kafka {

    private KafkaPerproty kp;
    private String topic;
    private StringBuffer msg;
    private Gson msgObj;
    private String groupId;


}
