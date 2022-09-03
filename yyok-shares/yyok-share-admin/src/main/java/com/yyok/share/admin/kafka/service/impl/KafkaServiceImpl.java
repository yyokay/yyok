package com.yyok.share.admin.kafka.service.impl;

import com.google.gson.Gson;
import com.yyok.share.admin.kafka.domain.Kafka;
import com.yyok.share.admin.kafka.service.IKafkaService;
import com.yyok.share.admin.kafka.service.mapper.IKafkaMapper;
import com.yyok.share.framework.mapper.common.service.impl.BaseServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class KafkaServiceImpl extends BaseServiceImpl<IKafkaMapper, Kafka> implements IKafkaService {


    @Override
    public Kafka findByName(String topic) {
        return null;
    }

    @Override
    public boolean updateByCode(Kafka coder) {
        return false;
    }

    //@KafkaListener(topics = "${kafka.topic.name}", groupId = "${kafka.consumer.group.id}")
    public StringBuffer recieveStr(@Payload Kafka msg, @Header(KafkaHeaders.MESSAGE_KEY) StringBuffer msgStr) throws Exception{
        Gson gs = new Gson();
        msgStr.append(gs.toJson(msg));
        return msgStr;

    }

    //@KafkaListener(topics = "${kafka.topic.name}", groupId = "${kafka.consumer.group.id}")
    public Gson recieveObj(@Payload Kafka msg, @Header(KafkaHeaders.MESSAGE_KEY) Gson msgGson) throws Exception{
        Gson gs = new Gson();

        msgGson.toJson(msg);
        return msgGson;
    }



}
