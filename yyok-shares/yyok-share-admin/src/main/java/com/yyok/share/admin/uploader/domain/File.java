package com.yyok.share.admin.uploader.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yyok.share.framework.domain.Domain;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * File表存储上传的文件信息
 */
@Data
@AllArgsConstructor
@ToString
@TableName("info_file")
public class File extends Domain implements Serializable {

    private String name;
    private String version;
    private String pcode;
    private String md5;
    private String dir;
    private String dbt; //database.table
    private String path;

    public File(String name, String md5, String pcode, Date date) {
        super();
    }

    public File() {

    }
}
