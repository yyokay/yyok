package com.yyok.share.admin.mnt.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yyok.share.framework.domain.Domain;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Data
@TableName("mnt_info_host")
public class Host extends Domain implements Serializable {

    private String pip;

    private String eip;

    private String nat;
    /**
     * ssh
     */
    private int sshport;
    /**
     * 用户名
     */
    private String machineName;
    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    private String baseDir;

    private String group;

    private String usr;

    @JsonIgnore
    private Set<Server> serverSet;

}
