package com.yyok.share.nlp.text

mport org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.api.scala._
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment

object flinkcdcMySql {

  def main(args: Array[String]): Unit = {
    //调用程序封装好的创建env对象的类
    val checkpointPath=EnvConfig.getConfigValue("flink.checkout.path")
    val driverName = EnvConfig.getConfigValue("com.xxx.driverName")
    val hostName = EnvConfig.getConfigValue("com.xxx.host")
    val userName = EnvConfig.getConfigValue("com.xxx.userName")
    val password = EnvConfig.getConfigValue("com.xxx.password")
    val dataBase = EnvConfig.getConfigValue("com.xxx.databse")
    val orderTableName = EnvConfig.getConfigValue("com.xxx.orderTableName")
    //创建流程序入口
    val env: StreamExecutionEnvironment = FlinkExecutionEnvUtil.getStreamEnv(checkpointPath)

    env.setParallelism(1) //便于测试使用
    val sourceFunction: DebeziumSourceFunction[String] = MySQLSource.builder[String]()
      .hostname(hostName)
      .port(3306)
      .username(userName)
      .password(password)
      .databaseList(dataBase)
      .tableList(orderTableName)
      .deserializer(new StringDebeziumDeserializationSchema)
      .build()

    val value = env.addSource(sourceFunction)
    value.print()

    env.execute("xhna")
  }

}
