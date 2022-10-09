package com.yyok.share.kafka.config;

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

@Configuration
@EnableKafka
public class KafkaPerproty {
    private static final Map<String, Object> producerPerproties = new HashMap<>();
    private static final Map<String, Object> consumerPerproties = new HashMap<>();
    private DefaultKafkaProducerFactory kafkaProducerFactory;
    private KafkaTemplate kafkaTemplate;
    private DefaultKafkaConsumerFactory kafkaConsumerFactory;

    /**
     * ConsumerConfigs
     */
    @Value("${spring.kafka.bootstrap-servers}")
    private String servers;
    @Value("${spring.kafka.consumer.enable.auto.commit}")
    private boolean enableAutoCommit;
    @Value("${spring.kafka.consumer.session.timeout}")
    private String sessionTimeout;
    @Value("${spring.kafka.consumer.auto.commit.interval}")
    private String autoCommitInterval;
    @Value("${spring.kafka.consumer.group.id}")
    private String groupId;
    @Value("${spring.kafka.consumer.auto.offset.reset}")
    private String autoOffsetReset;
    @Value("${spring.kafka.consumer.concurrency}")
    private int concurrency;

    /**
     * ProducerConfigs
     */
    @Value("${spring.kafka.producer.retries}")
    private int retries;
    @Value("${spring.kafka.producer.batch.size}")
    private int batchSize;
    @Value("${spring.kafka.producer.linger}")
    private int linger;
    @Value("${spring.kafka.producer.buffer.memory}")
    private int bufferMemory;

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setConcurrency(concurrency);
        factory.getContainerProperties().setPollTimeout(1500);
        return factory;
    }

    private ConsumerFactory<String, String> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(
                consumerConfigs(),
                new StringDeserializer(),
                new StringDeserializer()
        );
    }

    private Map<String, Object> consumerConfigs() {
        Map<String, Object> propsMap = new HashMap<>();
        propsMap.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
        propsMap.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, enableAutoCommit);
        propsMap.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, autoCommitInterval);
        propsMap.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, sessionTimeout);
        propsMap.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        propsMap.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        propsMap.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        propsMap.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset);
        return propsMap;
    }



    private static Map<String, Object> producerPerprotiesInit(String bootstrapServers, String groupId) {
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

    private static Map<String, Object> consumerPerprotiesInit(String bootstrapServers, String groupId) {
        consumerPerproties.put("bootstrap.servers", bootstrapServers);
        consumerPerproties.put("group.id", groupId);//消费群组，如果需要所有消费者都能接收到消息，则为每个消费者设置不同的群组Id
        consumerPerproties.put("enable.auto.commit", "false");
        consumerPerproties.put("auto.commit.interval.ms", "1000");
        consumerPerproties.put("auto.offset.reset", "latest");
        consumerPerproties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        consumerPerproties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        return consumerPerproties;
    }

    /**
     * 创建一个生产者工厂类
     *
     * @return
     */
    public DefaultKafkaProducerFactory getKafkaProducerFactory(String bootstrapServers, String groupId) {
        if (kafkaProducerFactory == null) {
            kafkaProducerFactory = new DefaultKafkaProducerFactory(producerPerprotiesInit(bootstrapServers, groupId));
        }
        return kafkaProducerFactory;
    }

    /**
     * 创建一个消息模板
     *
     * @param topic    默认的TOPIC
     * @param listener 生产者监听，如不需要则传入null
     * @return KafkaTemplate
     */
    public KafkaTemplate createKafkaTemplate(String topic, ProducerListener listener, String bootstrapServers, String groupId) {
        if (kafkaTemplate == null) {
            kafkaTemplate = new KafkaTemplate(this.getKafkaProducerFactory(bootstrapServers, groupId));
            kafkaTemplate.setDefaultTopic(topic);
            kafkaTemplate.setProducerListener(listener);
        }
        return kafkaTemplate;
    }

    /**
     * 发布消息
     *
     * @param topic          TopicName
     * @param message        消息字符串，通常为JSON string
     * @param isUsePartition 是否使用分区
     * @param partitionNum   分区的数量
     * @param role           用来区分消息key值
     * @return
     */
    public boolean send(String topic, String message, boolean isUsePartition, Integer partitionNum, String role) {
        if (role == null) {
            //role = ROLE_DEFAULT;
        }

        String key = role + "_" + message.hashCode();
        ListenableFuture<SendResult<String, Object>> result;
        if (isUsePartition) {
            int index = 0;//getPartitionIndex(key, partitionNum);
            result = kafkaTemplate.send(topic, index, key, message);

        } else {
            result = kafkaTemplate.send(topic, key, message);
        }

        return checkResult(result);
    }

    private boolean checkResult(ListenableFuture<SendResult<String, Object>> result) {
        boolean bl = true;

        return bl;
    }

    /**
     * 创建一个消费者工厂类
     *
     * @return
     */
    public DefaultKafkaConsumerFactory getKafkaConsumerFactory() {
        new StringDeserializer();
        if (kafkaConsumerFactory == null) {
            kafkaConsumerFactory = new DefaultKafkaConsumerFactory(consumerPerproties);
        }
        return kafkaConsumerFactory;
    }

    /**
     * 添加一个消费者监听
     *
     * @param listener 监听器
     * @param groupId  消费者Id，需要让所有的消费者接收消息，请指定不同的分组Id
     * @param topic    监听Topic名称
     * @return 返回KafkaMessageListenerContainer对象，可以进行stop或start
     */
    public KafkaMessageListenerContainer addListener(MessageListener listener, String groupId, String... topic) {
        ContainerProperties properties = new ContainerProperties(topic);
        properties.setMessageListener(listener);
        properties.setGroupId(groupId);

        KafkaMessageListenerContainer container = new KafkaMessageListenerContainer(getKafkaConsumerFactory(), properties);
        container.start();
        return container;
    }


    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
        props.put(ProducerConfig.RETRIES_CONFIG, retries);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, batchSize);
        props.put(ProducerConfig.LINGER_MS_CONFIG, linger);
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, bufferMemory);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return props;
    }

    public ProducerFactory<String, String> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }


}
