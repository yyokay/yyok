package com.yyok.ods.source;

import javafx.scene.control.Alert;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.shaded.zookeeper3.org.apache.zookeeper.Transaction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class Main {
    public static void main(String[] args) throws Exception {
        //StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        BatchTableEnvironment btenv = TableEnvironment.get

        env.execute("Fraud Detection");
    }
}