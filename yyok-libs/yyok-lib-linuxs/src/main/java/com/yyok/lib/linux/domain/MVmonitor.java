package com.yyok.lib.linux.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yyok.share.framework.domain.Domain;
import lombok.Data;

/**
 *
 *
 */
@Data
@TableName("mnt_mv")
public class MVmonitor extends Domain {

private String name;
//CPU、Memory、IO、Network，主要内容包括一、CPU、监控工具、二、Memory、监控工具、三、磁盘IO、监控工具、四、Network IO、对于TCP、基本概念、基础应用、原理机制
//procs -----------memory---------- ---swap-- -----io---- --system-- -----cpu------
private String procs;
private String memory;
private String swap;
private String diskio;
private String system;
private String cpu;

}
