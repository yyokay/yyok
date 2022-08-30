package com.yyok.share.admin.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageInfo;
import com.yyok.share.admin.system.domain.*;
import com.yyok.share.admin.system.service.IRoleService;
import com.yyok.share.admin.system.service.IRolesDeptsService;
import com.yyok.share.admin.system.service.IRolesResourcesService;
import com.yyok.share.admin.system.service.dto.RoleDto;
import com.yyok.share.admin.system.service.dto.RoleQueryCriteria;
import com.yyok.share.admin.system.service.dto.RoleSmallDto;
import com.yyok.share.admin.system.service.mapper.IResourceMapper;
import com.yyok.share.admin.system.service.mapper.IRoleMapper;
import com.yyok.share.framework.dozer.service.IGenerator;
import com.yyok.share.framework.exception.EntityExistException;
import com.yyok.share.framework.mapper.common.service.impl.BaseServiceImpl;
import com.yyok.share.framework.mapper.common.utils.QueryHelpPlus;
import com.yyok.share.framework.utils.FileUtil;
import com.yyok.share.framework.utils.StringUtils;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

// 默认不使用缓存
//import org.springframework.cache.annotation.CacheConfig;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;

/**
 * @author yyok
 * @date 2020-05-14
 */
@Service
@AllArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class RoleServiceImpl extends BaseServiceImpl<IRoleMapper, Role> implements IRoleService {
    private final IGenerator generator;
    private final IRoleMapper roleMapper;
    private final IResourceMapper resourceMapper;

    private final IRolesResourcesService rolesResourcesService;
    private final IRolesDeptsService rolesDeptsService;

    @Override
    public Map<String, Object> queryAll(RoleQueryCriteria criteria, Pageable pageable) {
        getPage(pageable);
        PageInfo<Role> page = new PageInfo<>(queryAll(criteria));
        Map<String, Object> map = new LinkedHashMap<>(2);
        map.put("content", generator.convert(page.getList(), RoleDto.class));
        map.put("totalElements", page.getTotal());
        return map;
    }

    /**
     * 查询数据分页
     *
     * @param pageable 分页参数
     * @return Object
     */
    @Override
    public Object queryAlls(RoleQueryCriteria criteria, Pageable pageable) {
        List<Role> roleList = baseMapper.selectList(QueryHelpPlus.getPredicate(Role.class, criteria));
        return roleList;
    }


    @Override
    public List<Role> queryAll(RoleQueryCriteria criteria) {
        List<Role> roleList = baseMapper.selectList(QueryHelpPlus.getPredicate(Role.class, criteria));
        for (Role role : roleList) {
            role.setResources(resourceMapper.findResourceByRoleCode(role.getCoder()));
            //role.setDepts(deptMapper.findDeptByRoleCode(role.getCoder()));
        }
        return roleList;
    }

    @Override
    public void download(List<RoleDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (RoleDto role : all) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("名称", role.getName());
            map.put("备注", role.getRemark());
            map.put("数据权限", role.getDataScope());
            map.put("角色级别", role.getLevel());
            map.put("创建日期", role.getCreateTime());
            map.put("功能权限", role.getPermission());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    /**
     * 根据用户ID查询
     *
     * @param coder 用户coder
     * @return /
     */
//    @Cacheable(key = "'findByUsers_Id:' + #p0")
    @Override
    public List<RoleSmallDto> findByAccountsCode(String coder) {
        List<Role> roles = roleMapper.selectListByAccountCoder(coder);
        return generator.convert(roles, RoleSmallDto.class);
    }

    /**
     * 根据角色查询角色级别
     *
     * @param roles /
     * @return /
     */
    @Override
    public Integer findByRoles(Set<Role> roles) {
        Set<RoleDto> roleDtos = new HashSet<>();
        for (Role role : roles) {
            //roleDtos.add(findByCode(role.getCoder()));
        }
        return Collections.min(roleDtos.stream().map(RoleDto::getLevel).collect(Collectors.toList()));
    }

    /**
     * 根据ID查询
     *
     * @param coder /
     * @return /
     */
    @Override
    public Role findByCode(String coder) {
        Role role = this.findByCode(coder);
        role.setResources(resourceMapper.findResourceByRoleCode(role.getCoder()));
        //role.setDepts(deptMapper.findDeptByRoleCode(role.getCoder()));
        Role r = generator.convert(role, Role.class);
        return r;
    }

    @Override
    public Role findByName(String name) {
        return null;
    }


    @Override
    public boolean updateByCode(Role coder) {
        return false;
    }

    /**
     * 修改绑定的菜单
     *
     * @param resources /
     * @param roleDto   /
     */
    @Override
//    @CacheEvict(allEntries = true)
    public void updateResource(Role resources, RoleDto roleDto) {
        if (resources.getResources().size() > 0) {
            List<RoleResource> roleResourceList = resources.getResources().stream().map(i -> {
                RoleResource roleResource = new RoleResource();
                roleResource.setRoleCode(resources.getCoder());
                roleResource.setResourceCode(i.getCoder());
                return roleResource;
            }).collect(Collectors.toList());
            rolesResourcesService.remove(new LambdaQueryWrapper<RoleResource>().eq(RoleResource::getRoleCode, resources.getCoder()));
            rolesResourcesService.saveBatch(roleResourceList);
        }
    }


    @Override
//    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public Role create(Role resources) {
        if (this.getOne(new LambdaQueryWrapper<Role>().eq(Role::getName, resources.getName())) != null) {
            throw new EntityExistException(Role.class, "username", resources.getName());
        }

        if (this.getOne(new LambdaQueryWrapper<Role>().eq(Role::getName, resources.getName())) != null) {
            throw new EntityExistException(Role.class, "username", resources.getName());
        }
        this.save(resources);
        if (resources.getDataScopes().size() > 0) {
            List<RolesDepts> rolesDeptsList = resources.getDataScopes().stream().map(i -> {
                RolesDepts rolesDepts = new RolesDepts();
                rolesDepts.setRoleCode(resources.getCoder());
                rolesDepts.setDeptCode(i.getCoder());
                return rolesDepts;
            }).collect(Collectors.toList());
            rolesDeptsService.saveBatch(rolesDeptsList);
        }
        return resources;//generator.convert(resources, RoleDto.class);
    }

    @Override
//    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void update(Role resources) {
        Role role = this.findByCode(resources.getCoder());

        Role role1 = this.getOne(new LambdaQueryWrapper<Role>().eq(Role::getName, resources.getName()));

        if (role1 != null && !role1.getCoder().equals(role.getCoder())) {
            throw new EntityExistException(Role.class, "username", resources.getName());
        }
        role1 = this.getOne(new LambdaQueryWrapper<Role>().eq(Role::getPermission, resources.getPermission()));
        if (role1 != null && !role1.getCoder().equals(role.getCoder())) {
            throw new EntityExistException(Role.class, "permission", resources.getPermission());
        }
        role.setName(resources.getName());
        role.setRemark(resources.getRemark());
        role.setDataScope(resources.getDataScope());
        if (resources.getDataScopes().size() > 0) {
            List<RolesDepts> rolesDeptsList = resources.getDataScopes().stream().map(i -> {
                RolesDepts rolesDepts = new RolesDepts();
                rolesDepts.setRoleCode(resources.getCoder());
                rolesDepts.setDeptCode(i.getCoder());
                return rolesDepts;
            }).collect(Collectors.toList());
            rolesDeptsService.remove(new LambdaQueryWrapper<RolesDepts>().eq(RolesDepts::getRoleCode, resources.getCoder()));
            rolesDeptsService.saveBatch(rolesDeptsList);
        }
        role.setLevel(resources.getLevel());
        role.setPermission(resources.getPermission());
        this.saveOrUpdate(role);
    }

    /**
     * 获取用户权限信息
     *
     * @param account 用户信息
     * @return 权限信息
     */
    @Override
//    @Cacheable(key = "'loadPermissionByUser:' + #p0.username") Collection<SimpleGrantedAuthority>
    public Account mapToGrantedAuthorities(Account account) {
        Set<Role> roles = roleMapper.findByAccountsCoder(account.getCoder());
        for (Role role : roles) {
            Set<Resource> resourceSet = resourceMapper.findResourceByRoleCode(role.getCoder());
            role.setResources(resourceSet);
            //Set<Dept> deptSet = deptMapper.findDeptByRoleCode(role.getCoder());
            //role.setDepts(deptSet);
        }
        account.setRoles(roles);
        Set<String> permissions = roles.stream().filter(role -> StringUtils.isNotBlank(role.getPermission())).map(Role::getPermission).collect(Collectors.toSet());
        permissions.addAll(
                roles.stream().flatMap(role -> role.getResources().stream())
                        .filter(resource -> StringUtils.isNotBlank(resource.getPermission()))
                        .map(Resource::getPermission).collect(Collectors.toSet())
        );

        account.setAuthorities(permissions.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet()));
        return account;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<String> coders) {
        for (String coder : coders) {
            //vbnmervice.lambdaUpdate().eq(RolesDepts::getRoleCode, coder).remove();

        }
        //this.removeByCodes(coders);
    }

}
