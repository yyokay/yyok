package com.yyok.share.admin.kafka.service.mapper;

import com.yyok.share.admin.kafka.domain.Kafka;
import com.yyok.share.framework.mapper.common.mapper.ICoreMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface IKafkaMapper extends ICoreMapper<Kafka> {

}
