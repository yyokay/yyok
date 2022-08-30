package com.yyok.share.admin.system.service.mapper;

import com.yyok.share.admin.system.domain.Account;
import com.yyok.share.framework.mapper.common.mapper.ICoreMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * @author yyok
 * @date 2020-05-14
 */
@Repository
@Mapper
public interface IAccountMapper extends ICoreMapper<Account> {

    /**
     * 修改密码
     *
     * @param account               用户名
     * @param password              密码
     * @param lastPasswordResetTime /
     */
    @Update("update `sys_account` set password = #{password} , last_password_reset_time = #{lastPasswordResetTime} where account = #{account}")
    void updatePass(@Param("password") String password, @Param("lastPasswordResetTime") String lastPasswordResetTime, @Param("account") String account);

    /**
     * 修改邮箱
     *
     * @param account 用户名
     * @param email   邮箱
     */
    @Update("update `sys_account` set email = #{email} where account = #{account}")
    void updateEmail(@Param("email") String email, @Param("account") String account);

    /**
     * 根据用户名查询用户信息
     *
     * @param account 用户名
     */
    @Select("SELECT u.coder,u.nick_name,u.user_code, u.enabled,u.create_time,u.phone,u.email,u.`password`,u.account FROM `sys_account` " +
            " u LEFT JOIN sys_account_avatar ua ON u.coder = ua.account_code  WHERE u.account = #{account}")
    Account findByName(String account);

}
