package com.yyok.share.admin.system.service;

import com.yyok.share.admin.system.domain.Account;
import com.yyok.share.admin.system.domain.Role;
import com.yyok.share.admin.system.service.dto.RoleDto;
import com.yyok.share.admin.system.service.dto.RoleQueryCriteria;
import com.yyok.share.admin.system.service.dto.RoleSmallDto;
import com.yyok.share.framework.mapper.common.service.IBaseService;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author yyok
 * @date 2020-05-14
 */
public interface IRoleService extends IBaseService<Role> {

    /**
     * 查询数据分页
     *
     * @param criteria 条件
     * @param pageable 分页参数
     * @return Map<String, Object>
     */
    Map<String, Object> queryAll(RoleQueryCriteria criteria, Pageable pageable);


    /**
     * 查询数据分页
     *
     * @param pageable 分页参数
     * @return Map<String, Object>
     */
    Object queryAlls(RoleQueryCriteria criteria, Pageable pageable);

    /**
     * 查询所有数据不分页
     *
     * @param criteria 条件参数
     * @return List<RoleDto>
     */
    List<Role> queryAll(RoleQueryCriteria criteria);

    /**
     * 导出数据
     *
     * @param all      待导出的数据
     * @param response /
     * @throws IOException /
     */
    void download(List<RoleDto> all, HttpServletResponse response) throws IOException;

    /**
     * 根据用户coder查询
     *
     * @param coder 用户coder
     * @return /
     */
    List<RoleSmallDto> findByAccountsCode(String coder);

    /**
     * 根据角色查询角色级别
     *
     * @param roles /
     * @return /
     */
    Integer findByRoles(Set<Role> roles);

    /**
     * 修改绑定的菜单
     *
     * @param resources /
     * @param roleDto   /
     */
    void updateResource(Role resources, RoleDto roleDto);

    /**
     * 创建
     *
     * @param resources /
     * @return /
     */
    Role create(Role resources);

    /**
     * 编辑
     *
     * @param resources /
     */
    void update(Role resources);

    /**
     * 获取用户权限信息
     *
     * @param account 用户信息
     * @return 权限信息
     */
    Account mapToGrantedAuthorities(Account account);

    void delete(Set<String> coders);
}
