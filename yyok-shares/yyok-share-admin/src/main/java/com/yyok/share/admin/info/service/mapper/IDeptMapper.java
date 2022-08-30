package com.yyok.share.admin.info.service.mapper;

import com.yyok.share.admin.info.domain.Dept;
import com.yyok.share.framework.mapper.common.mapper.ICoreMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * @author yyok
 * @date 2020-05-14
 */
@Repository
@Mapper
public interface IDeptMapper extends ICoreMapper<Dept> {

    @Select("select m.* from sys_dept m LEFT JOIN sys_roles_depts t on m.coder= t.dept_code LEFT JOIN role r on r.coder = t.role_code where r.coder = ${roleId}")
    Set<Dept> findDeptByRoleCode(@Param("roleCode") String roleCode);

    @Select("select * from sys_dept m LEFT JOIN sys_roles_depts t on m.coder= t.dept_code LEFT JOIN role r on r.coder = t.role_code where r.coder = #{roleCode}")
    Set<Dept> findDeptByRoleIds(@Param("roleCodes") Set<String> roleCode);
}
