package com.yyok.share.admin.mnt.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yyok.share.framework.domain.Domain;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("mnt_info_cluster")
public class Cluster extends Domain implements Serializable {

    private String clusterName;

    private String clusterComponet;

}
