package com.yyok.share.admin.monitor.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yyok.share.framework.domain.Domain;
import lombok.Data;

import java.io.Serializable;

/**
 * pv 与 ip 统计
 *
 * @author yyok
 * @date 2022-12-13
 */
@Data
@TableName("sys_visit")
public class Visits extends Domain implements Serializable {

    private String date;

    private Long pvCounts;

    private Long ipCounts;

    private String weekDay;
}
