package com.yyok.share.framework.utils;

public class SQLtrainsUtil {

    public static String changeMysqlTableToClickHouse(String createDatabaseTable) {
        String tables = createDatabaseTable;
        String primaryKey = "`id`";//默认id
        String[] rows = tables.split("\n");
        StringBuilder replaceTables = new StringBuilder();
        Boolean haveKey = false;
        int i = 0;
        for (String row : rows) {
            // 注释，不处理
            if (row.contains("--")) {
                replaceTables.append(row + "\n");
                continue;
            }
            if (row.contains("KEY")) {
                if (row.contains("PRIMARY")) {
                    haveKey = true;
                    primaryKey = row.substring(row.indexOf("(") + 1, row.indexOf(")"));
                }
                // 跳过、不添加
                continue;
            }
            if (row.contains("ENGINE=InnoDB")) {
                // 引擎替换
                row = ") ENGINE = ReplacingMergeTree";
            }

            // 无关删除
            String changeRow = row.replaceAll("AUTO_INCREMENT", "")
                    .replaceAll("CHARACTER SET utf8mb4", "")
                    .replaceAll("CHARACTER SET utf8", "")
                    .replaceAll("ON UPDATE CURRENT_TIMESTAMP", "")
                    .replaceAll("CURRENT_TIMESTAMP", "")
                    .replaceAll("( DEFAULT CHARSET).*", "")
                    // 时间替换
                    .replaceAll("datetime DEFAULT NULL", " DateTime ")
                    .replaceAll(" datetime ", " DateTime ");

            /*String规则*/
            // 为空,字符串
            changeRow = changeRow.replaceAll("(` ).*(char).*(DEFAULT NULL)", "` String NULL");
            changeRow = changeRow.replaceAll("(` ).*(char).*(DEFAULT '')", "` String");
            // changeRow = changeRow.replaceAll("(DEFAULT '')", "NULL");
            // 非空,字符串
            changeRow = changeRow.replaceAll("(` ).*(char).*(NOT NULL)", "` String");
            changeRow = changeRow.replaceAll("text", "String");
            changeRow = changeRow.replaceAll("(DEFAULT NULL)", "NULL");
            changeRow = changeRow.replaceAll("(NOT NULL)", "");

            // 以空格分割
            String[] changeColumns = changeRow.split("[ ]");
            //      System.out.println(changeRow);
            // 含有int的替换规则
            if (changeColumns[3].contains("int") || changeColumns[3].contains("bigint")
                    ||changeColumns[3].contains("INT")) {
                changeColumns[3].replaceAll("INT","int");
                changeColumns[3].replaceAll("BIGINT","bigint");
                // 将括号内的数字拿出来
                int length = Integer.parseInt(changeColumns[3]
                        .replaceAll("bigint", "")
                        .replaceAll("tinyint", "")
                        .replaceAll("int", "")
                        .replaceAll("\\(", "")
                        .replaceAll("\\)", ""));
                // 获取数据类型
                String type = changeColumns[3].substring(0, changeColumns[3].indexOf("("));
                // 处理int 是否可以为空值
                String last = " NULL";
                String[] _int = {"Int8", "Int16", "Int32", "Int64"};
                if (changeRow.contains("DEFAULT NULL")) {
                    changeRow = changeRow.replaceAll("DEFAULT NULL", "");
                    for (int j = 0; j < _int.length; j++) {
                        _int[j] = _int[j] + last;
                    }
                }
                if ("tinyint".equals(type)) {
                    changeRow = changeRow
                            .replaceFirst(type + "\\(" + length + "\\)", _int[0]);
                } else if ("smallint".equals(type)) {
                    changeRow = changeRow
                            .replaceFirst(type + "\\(" + length + "\\)", _int[1]);
                } else if ("int".equals(type) || "mediumint".equals(type)) {
                    changeRow = changeRow
                            .replaceFirst(type + "\\(" + length + "\\)", _int[2]);
                } else {
                    changeRow = changeRow
                            .replaceFirst(type + "\\(" + length + "\\)", _int[3]);
                }
            }

            replaceTables.append(changeRow.trim() + "\n");
            if (i == 0) {
                replaceTables.append("\n");
            }
            i++;
        }
        if (replaceTables.toString().contains(",) ENGINE = Memory")) {
            String temp = replaceTables.substring(0, replaceTables.indexOf(",) ENGINE = Memory"));
            replaceTables = new StringBuilder(temp + ") ENGINE = Memory ");
        }
        replaceTables.toString().replaceAll("CREATE TABLE `" + createDatabaseTable + "`", createDatabaseTable + "_local");
        if (haveKey) {
            replaceTables.append("PRIMARY KEY " + primaryKey);
        }
        replaceTables.append("\nORDER BY " + primaryKey);
        replaceTables.append(";");
        return replaceTables.toString();
    }

    public static String changeClickHouseToMysqlTable(){


        return "";
    }

    public static void main(String[] args) {
        //将sql复制到这里
        String createTable = "DROP TABLE IF EXISTS `runoob_tbl`;\n" +
                "CREATE TABLE IF NOT EXISTS `runoob_tbl`(\n" +
                "   `runoob_id` int UNSIGNED AUTO_INCREMENT,\n" +
                "   `runoob_title` VARCHAR(100) NOT NULL,\n" +
                "   `runoob_author` VARCHAR(40) NOT NULL,\n" +
                "   `submission_date` DATE,\n" +
                "   PRIMARY KEY ( `runoob_id` )\n" +
                ")ENGINE=InnoDB DEFAULT CHARSET=utf8;";
        System.out.println("请检查语句,不要有空行 也就是只有'\\n'");
        String res = SQLtrainsUtil.changeMysqlTableToClickHouse(createTable);
        System.out.println("转换后的建表语句为：");
        System.out.println(res);
        System.out.println("需要删除ENGINE的上一行最后的一个逗号");
    }


}
