package com.yyok.share.nlp.text

import java.text.SimpleDateFormat
import java.util.{Date, Properties}
import com.alibaba.fastjson.JSON
import com.yyok.share.util.ClickHouseConfig
import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.api.java.utils.ParameterTool
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011


object SaveToClickhouseJob {

  def main(args: Array[String]): Unit = {
    val parameterTool = ParameterTool.fromArgs(args)
    val topic = parameterTool.get("kafka.topic.name", "logs")
    val env = StreamExecutionEnvironment.createLocalEnvironment()

    val TARGET_TABLE_NAME_VAL = "szzd.table"
    val BATCH_SIZE_VAL = "20000"
    val INSTANCES_VAL = "192.168.10.34:8123"
    val USERNAME_VAL = "default"
    val PASSWORD_VAL = "root"
    val FLUSH_INTERVAL_VAL = "2"

    val CLICKHOUSE_HOST_KEY ="clickhouse.host"
    val CLICKHOUSE_PORT_KEY = "clickhouse.port"
    val BATCH_SIZE_KEY = "batch.size"
    val USERNAME_KEY = "clickhouse.username"
    val PASSWORD_KEY = "clickhouse.password"
    val DATABASE_KEY = "clickhouse.dbname"

    val TABLENAME_KEY = "clickhouse.tablename"

    val ckSinkerProps = new Properties
    ckSinkerProps.put(ClickHouseConfig.TABLENAME_KEY, ClickHouseConfig.TARGET_TABLE_NAME_VAL)
    ckSinkerProps.put(ClickHouseConfig.BATCH_SIZE_KEY, ClickHouseConfig.BATCH_SIZE_VAL)

    ckSinkerProps.put(ClickHouseConfig.INSTANCES_KEY, ClickHouseConfig.INSTANCES_VAL)
    ckSinkerProps.put(ClickHouseConfig.USERNAME_KEY, ClickHouseConfig.USERNAME_VAL)
    ckSinkerProps.put(ClickHouseConfig.PASSWORD_KEY, ClickHouseConfig.PASSWORD_VAL)
    ckSinkerProps.put(ClickHouseConfig., ClickHouseConfig.FLUSH_INTERVAL_VAL)

    val kafkaProps = new Properties()
    kafkaProps.setProperty("bootstrap.servers", "192.168.10.36:9092,192.168.10.38:9092,192.168.10.39:9092")
    kafkaProps.setProperty("group.id", "szzd")

    val myConsumer = new FlinkKafkaConsumer011[String](topic, new SimpleStringSchema(), kafkaProps)

    myConsumer.setStartFromEarliest()

    val sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")

    val records = env.addSource(myConsumer).map(s => {
      val data = JSON.parseObject(s, classOf[Data])
      s"('${data.name}','${data.city}','${sdf.format(new Date(data.dateT))}','${data.ts}','${data.num}')"
    })

    records.addSink(new ClickhouseSink(ckSinkerProps)).setParallelism(2)

    env.execute("kafka2clickhouse")
  }
}