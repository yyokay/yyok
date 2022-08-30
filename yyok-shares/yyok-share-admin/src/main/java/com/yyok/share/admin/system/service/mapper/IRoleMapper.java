package com.yyok.share.admin.system.service.mapper;

import com.yyok.share.admin.system.domain.Role;
import com.yyok.share.framework.mapper.common.mapper.ICoreMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * @author yyok
 * @date 2020-05-14
 */
@Repository
@Mapper
public interface IRoleMapper extends ICoreMapper<Role> {

    /**
     * 根据用户coder查询
     *
     * @param coder 用户coder
     * @return
     */
    @Select("SELECT r.coder,r.create_time,r.data_scope,r.`level`,r.`name`,r.permission,r.remark " +
            "FROM sys_role r LEFT OUTER JOIN sys_account_role u1 ON r.coder = u1.role_code " +
            "LEFT OUTER JOIN sys_account u2 ON u1.account_code = u2.coder " +
            "WHERE u2.coder = #{coder}")
    Set<Role> findByAccountsCoder(@Param("coder") String coder);

    /**
     * 解绑角色菜单
     *
     * @param coder 菜单coder
     */
    @Delete("delete from sys_role_resource where resource_code = #{coder}")
    void untiedResource(@Param("coder") String coder);

    /**
     * 根据用户coder查询
     *
     * @param coder 用户coder
     * @return
     */
    @Select("select m.* from sys_role m LEFT JOIN sys_account_role t on m.coder= t.role_code LEFT JOIN `sys_account` r on r.coder = t.account_code where r.coder = #{coder}")
    List<Role> selectListByAccountCoder(@Param("coder") String coder);

}
