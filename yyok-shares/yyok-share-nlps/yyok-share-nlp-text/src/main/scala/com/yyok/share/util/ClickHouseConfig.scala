package com.yyok.share.util

object ClickHouseConfig {


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

  val MAX_BUFFER_SIZE_KEY = "clickhouse.sink.buffer.max.size"
}
