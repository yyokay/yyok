package com.yyok.share.admin.info.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yyok.share.framework.domain.Domain;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author yyok
 * @date 2020-05-14
 */
@Data
@TableName("info_user")
public class User extends Domain implements Serializable {

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号码
     */
    @NotBlank(message = "请输入手机号码")
    private String phone;

    /**
     * 头像
     */
    private String avatarCode;

    /**
     * 用户头像
     */
    @TableField(exist = false)
    private String avatar;

    /**
     * 用户部门
     */
    private String companyCode;

    /**
     * 用户部门
     */
    @TableField(exist = false)
    private Dept dept;

    /**
     * 用户职位
     */
    @TableField(exist = false)
    private Job job;

    /**
     * 部门名称
     */
    private String deptCode;

    /**
     * 岗位名称
     */
    private String jobCode;


    /**
     * 昵称
     */
    private String nickName;


    /**
     * 性别
     */
    private String sex;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(coder, user.coder) &&
                Objects.equals(phone, user.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coder, phone);
    }

    public void copy(User source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }

    public @interface Update {
    }
}
