package com.yyok.share.framework.perproty;

import lombok.Data;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.support.ProducerListener;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.HashMap;
import java.util.Map;

@Data
public class KafkaPerproty {
    private static final Map<String, Object> producerPerproties = new HashMap<>();
    private static final Map<String, Object> consumerPerproties = new HashMap<>();

    /**
     * ConsumerConfigs
     */
    private String servers;
    private boolean enableAutoCommit;
    private String sessionTimeout;
    private String autoCommitInterval;
    private String groupId;
    private String autoOffsetReset;

    private int concurrency;

    /**
     * producer
     */
    private int retries;
    private int batchSize;
    private int linger;
    private int bufferMemory;


    private static Map<String, Object> producerPerprotieInit(String bootstrapServers, String groupId) {
        producerPerproties.put("bootstrap.servers", bootstrapServers);
        producerPerproties.put("group.id", groupId);
        producerPerproties.put("compression.type", "gzip");
        producerPerproties.put("reconnect.backoff.ms ", 20000);
        producerPerproties.put("retry.backoff.ms", 20000);
        producerPerproties.put("retries", 30);
        producerPerproties.put("batch.size", "16384");
        producerPerproties.put("linger.ms", "50");
        producerPerproties.put("acks", "all");
        producerPerproties.put("buffer.memory", "33554432");
        producerPerproties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        producerPerproties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        return producerPerproties;
    }

    private static Map<String, Object> consumerPerprotieInit(String bootstrapServers, String groupId) {
        consumerPerproties.put("bootstrap.servers", bootstrapServers);
        consumerPerproties.put("group.id", groupId);
        //消费群组，如果需要所有消费者都能接收到消息，则为每个消费者设置不同的群组Id
        consumerPerproties.put("enable.auto.commit", "false");
        consumerPerproties.put("auto.commit.interval.ms", "1000");
        consumerPerproties.put("auto.offset.reset", "latest");
        consumerPerproties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        consumerPerproties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        return consumerPerproties;
    }

}
