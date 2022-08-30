package com.yyok.share.admin.system.service.mapper;

import com.yyok.share.admin.system.domain.Resource;
import com.yyok.share.framework.mapper.common.mapper.ICoreMapper;
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
public interface IResourceMapper extends ICoreMapper<Resource> {


    /**
     * 根据菜单的 pcode 查询
     *
     * @param pcode /
     * @return /
     */
    @Select("SELECT * from sys_resource m where m.pcode = #{pcode} ")
    List<Resource> findByPcode(@Param("pcode") String pcode);

    @Select("select m.* from sys_resource m LEFT JOIN sys_role_resource t on m.coder= t.resource_code LEFT JOIN sys_role r on r.coder = t.role_code where r.coder = #{coder}")
    Set<Resource> findResourceByRoleCode(@Param("coder") String coder);

    @Select("<script>select m.* from sys_resource m LEFT OUTER JOIN sys_role_resource t on m.coder= t.resource_code LEFT OUTER JOIN sys_role r on r.coder = t.role_code where m.typer!=2 and  r.coder in <foreach collection=\"roleCodes\" index=\"index\" item=\"item\" open=\"(\" separator=\",\" close=\")\">#{item}</foreach> order by m.sorter asc</script>")
    List<Resource> selectListByRoles(@Param("roleCodes") List<String> roleCodes);
}
