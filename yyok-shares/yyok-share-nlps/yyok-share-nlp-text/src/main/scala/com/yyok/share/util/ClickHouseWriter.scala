package com.yyok.share.util

import cn.hutool.json.JSON
import com.google.gson.Gson
import org.slf4j.LoggerFactory
import ru.yandex.clickhouse.ClickHouseDataSource

import scala.collection.mutable.ArrayBuffer

class ClickHouseWriter(ds:ClickHouseDataSource,dbname:String,tablename:String,copy_data:ArrayBuffer[String]) extends Thread {

  val log = LoggerFactory.getLogger("ClickHouseWriter")
  override def run(): Unit = {
   writer()
  }

  private[utils] def writer(): Unit ={
    using(ds.getConnection){conn =>
      if(!copy_data.isEmpty){

        val columns = new Gson().(copy_data.last,Feature.OrderedField).keySet().toArray().map(_.toString)
        val insertStatementSql = generateInsertStatment(dbName = dbname,tableName=tablename,columns)
        val statement = conn.prepareStatement(insertStatementSql)

        copy_data.foreach(item =>{
          val vals = JSON.parseObject(item,Feature.OrderedField).values().toArray()
          for(i <- 0 until columns.size){
            statement.setObject(i+1,vals(i))
          }
          statement.addBatch()
        })
        statement.executeBatch()
      }
    }
  }

  private[utils] def using[A, B <: {def close(): Unit}] (closeable: B) (f: B => A): A =
    try {
      f(closeable)
    }
    finally {
      closeable.close()
    }

  private[utils] def generateInsertStatment( dbName: String, tableName: String,columns :Array[String]) = {
    val vals = 1 to (columns.length) map (i => "?")
    s"INSERT INTO $dbName.$tableName (${columns.mkString(",")}) VALUES (${vals.mkString(",")})"
  }

}
