package com.yyok.share.admin.info.service.mapper;

import com.yyok.share.admin.info.domain.User;
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
public interface IUserMapper extends ICoreMapper<User> {

    /**
     * 修改密码
     *
     * @param username              用户名
     * @param password              密码
     * @param lastPasswordResetTime /
     */
    @Update("update `user` set password = #{password} , last_password_reset_time = #{lastPasswordResetTime} where username = #{username}")
    void updatePass(@Param("password") String password, @Param("lastPasswordResetTime") String lastPasswordResetTime, @Param("username") String username);

    /**
     * 修改邮箱
     *
     * @param username 用户名
     * @param email    邮箱
     */
    @Update("update `user` set email = #{email} where username = #{username}")
    void updateEmail(@Param("email") String email, @Param("username") String username);

    /**
     * 根据用户名查询用户信息
     *
     * @param userName 用户名
     */
    @Select("SELECT u.coder,u.nick_name,u.sex,u.dept_code,u.job_code,u.company_code,u.enabled,u.create_time,u.phone,u.email,ua.real_name avatar FROM `sys_user` " +
            " u LEFT JOIN sys_account_avatar ua ON u.avatar_code = ua.account_code WHERE u.username = #{username}")
    User findByName(String userName);

}
