package com.yyok.share.admin.system.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageInfo;
import com.yyok.share.admin.system.domain.Resource;
import com.yyok.share.admin.system.domain.vo.ResourceMetaVo;
import com.yyok.share.admin.system.domain.vo.ResourceVo;
import com.yyok.share.admin.system.service.IResourceService;
import com.yyok.share.admin.system.service.dto.ResourceDto;
import com.yyok.share.admin.system.service.dto.ResourceQueryCriteria;
import com.yyok.share.admin.system.service.dto.RoleSmallDto;
import com.yyok.share.admin.system.service.mapper.IResourceMapper;
import com.yyok.share.admin.system.service.mapper.IRoleMapper;
import com.yyok.share.framework.dozer.service.IGenerator;
import com.yyok.share.framework.exception.BadRequestException;
import com.yyok.share.framework.exception.EntityExistException;
import com.yyok.share.framework.mapper.common.service.impl.BaseServiceImpl;
import com.yyok.share.framework.mapper.common.utils.QueryHelpPlus;
import com.yyok.share.framework.utils.FileUtil;
import com.yyok.share.framework.utils.StringUtils;
import com.yyok.share.framework.utils.ValidationUtil;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
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
@CacheConfig(cacheNames = "resource")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ResourceServiceImpl extends BaseServiceImpl<IResourceMapper, Resource> implements IResourceService {

    private final IGenerator generator;
    private final IResourceMapper resourceMapper;
    private final IRoleMapper roleMapper;

    @Override
    @Cacheable
    public Map<String, Object> queryAll(ResourceQueryCriteria criteria, Pageable pageable) {
        getPage(pageable);
        PageInfo<Resource> page = new PageInfo<>(queryAll(criteria));
        Map<String, Object> map = new LinkedHashMap<>(2);
        map.put("content", generator.convert(page.getList(), ResourceDto.class));
        map.put("totalElements", page.getTotal());
        return map;
    }


    @Override
    @Cacheable
    public List<Resource> queryAll(ResourceQueryCriteria criteria) {
        return baseMapper.selectList(QueryHelpPlus.getPredicate(Resource.class, criteria));
    }


    @Override
    public void download(List<ResourceDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (ResourceDto resource : all) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("是否外链", resource.getIFrame());
            map.put("菜单名称", resource.getName());
            map.put("组件", resource.getComponent());
            map.put("上级菜单ID", resource.getPcode());
            map.put("排序", resource.getSorter());
            map.put("图标", resource.getIcon());
            map.put("链接地址", resource.getPath());
            map.put("缓存", resource.getCache());
            map.put("是否隐藏", resource.getHidden());
            map.put("组件名称", resource.getComponentName());
            map.put("创建日期", resource.getCreateTime());
            map.put("权限", resource.getPermission());
            map.put("类型", resource.getTyper());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    /**
     * 构建菜单树
     *
     * @param resourceDtos 原始数据
     * @return /
     */
    @Override
    public Map<String, Object> buildTree(List<ResourceDto> resourceDtos) {
        List<ResourceDto> trees = new ArrayList<>();
        Set<String> coders = new HashSet<>();
        for (ResourceDto resourceDto : resourceDtos) {
            if (resourceDto.getPcode() == "0") {
                trees.add(resourceDto);
            }
            for (ResourceDto it : resourceDtos) {
                if (it.getPcode().equals(resourceDto.getCoder())) {
                    if (resourceDto.getChildren() == null) {
                        resourceDto.setChildren(new ArrayList<>());
                    }
                    resourceDto.getChildren().add(it);
                    coders.add(it.getCoder());
                }
            }
        }
        Map<String, Object> map = new HashMap<>(2);
        if (trees.size() == 0) {
            trees = resourceDtos.stream().filter(s -> !coders.contains(s.getCoder())).collect(Collectors.toList());
        }
        map.put("content", trees);
        map.put("totalElements", resourceDtos.size());
        return map;
    }

    /**
     * 构建菜单树
     *
     * @param resourceDtos /
     * @return /
     */
    @Override
    public List<ResourceVo> buildResources(List<ResourceDto> resourceDtos) {
        List<ResourceVo> list = new LinkedList<>();
        resourceDtos.forEach(resourceDTO -> {
                    if (resourceDTO != null) {
                        List<ResourceDto> resourceDtoList = resourceDTO.getChildren();
                        ResourceVo ResourceVo = new ResourceVo();
                        ResourceVo.setName(ObjectUtil.isNotEmpty(resourceDTO.getComponentName()) ? resourceDTO.getComponentName() : resourceDTO.getName());
                        // 一级目录需要加斜杠，不然会报警告
                        ResourceVo.setPath(resourceDTO.getPcode() == "0" ? "/" + resourceDTO.getPath() : resourceDTO.getPath());
                        ResourceVo.setHidden(resourceDTO.getHidden());
                        // 如果不是外链
                        if (!resourceDTO.getIFrame()) {
                            if (resourceDTO.getPcode() == "0") {
                                ResourceVo.setComponent(StrUtil.isEmpty(resourceDTO.getComponent()) ? "Layout" : resourceDTO.getComponent());
                            } else if (!StrUtil.isEmpty(resourceDTO.getComponent())) {
                                ResourceVo.setComponent(resourceDTO.getComponent());
                            }
                        }
                        ResourceVo.setMeta(new ResourceMetaVo(resourceDTO.getName(), resourceDTO.getIcon(), !resourceDTO.getCache()));
                        if (resourceDtoList != null && resourceDtoList.size() != 0) {
                            ResourceVo.setAlwaysShow(true);
                            ResourceVo.setRedirect("noredirect");
                            ResourceVo.setChildren(buildResources(resourceDtoList));
                            // 处理是一级菜单并且没有子菜单的情况
                        } else if (resourceDTO.getPcode() == "0") {
                            ResourceVo ResourceVo1 = new ResourceVo();
                            ResourceVo1.setMeta(ResourceVo.getMeta());
                            // 非外链
                            if (!resourceDTO.getIFrame()) {
                                ResourceVo1.setPath("index");
                                ResourceVo1.setName(ResourceVo.getName());
                                ResourceVo1.setComponent(ResourceVo.getComponent());
                            } else {
                                ResourceVo1.setPath(resourceDTO.getPath());
                            }
                            ResourceVo.setName(null);
                            ResourceVo.setMeta(null);
                            ResourceVo.setComponent("Layout");
                            List<ResourceVo> list1 = new ArrayList<>();
                            list1.add(ResourceVo1);
                            ResourceVo.setChildren(list1);
                        }
                        list.add(ResourceVo);
                    }
                }
        );
        return list;
    }

    /**
     * 获取菜单树
     *
     * @param resources /
     * @return /
     */
    @Override
    @Cacheable(key = "'tree'")
    public Object getResourceTree(List<Resource> resources) {
        List<Map<String, Object>> list = new LinkedList<>();
        resources.forEach(resource -> {
                    if (resource != null) {
                        List<Resource> resourceList = resourceMapper.findByPcode(resource.getCoder());
                        Map<String, Object> map = new HashMap<>(16);
                        map.put("coder", resource.getCoder());
                        map.put("label", resource.getName());
                        if (resourceList != null && resourceList.size() != 0) {
                            map.put("children", getResourceTree(resourceList));
                        }
                        list.add(map);
                    }
                }
        );
        return list;
    }

    /**
     * 获取待删除的菜单
     *
     * @param resourceList /
     * @param resourceSet  /
     * @return /
     */
    @Override
    public Set<Resource> getDeleteResources(List<Resource> resourceList, Set<Resource> resourceSet) {
        // 递归找出待删除的菜单
        for (Resource resource1 : resourceList) {
            resourceSet.add(resource1);
            List<Resource> resources = resourceMapper.findByPcode(resource1.getCoder());
            if (resources != null && resources.size() != 0) {
                getDeleteResources(resources, resourceSet);
            }
        }
        return resourceSet;
    }

    /**
     * 根据pid查询
     *
     * @param pcode /
     * @return /
     */
    @Override
    @Cacheable(key = "'pcode:'+#p0")
    public List<Resource> findByPcode(String pcode) {
        return resourceMapper.findByPcode(pcode);
    }

    /**
     * 根据角色查询
     *
     * @param roles /
     * @return /
     */
    @Override
    public List<ResourceDto> findByRoles(List<RoleSmallDto> roles) {
        List<String> roleCodes = roles.stream().map(i -> {
            String role = i.getCoder();
            return role;
        }).collect(Collectors.toList());

        List<Resource> list = resourceMapper.selectListByRoles(roleCodes);

        return generator.convert(list, ResourceDto.class);
    }

    /**
     * 删除
     *
     * @param resourceSet /
     */
    @Override
    @CacheEvict(allEntries = true)
    public void delete(Set<Resource> resourceSet) {
        for (Resource resource : resourceSet) {
            roleMapper.untiedResource(resource.getCoder());
            this.removeByCode(resource.getCoder());
        }
    }

    /**
     * 编辑
     *
     * @param resources /
     */
    @Override
    @CacheEvict(allEntries = true)
    public void update(Resource resources) {
        if (resources.getCoder().equals(resources.getPcode())) {
            throw new BadRequestException("上级不能为自己");
        }
        Resource resource = this.findByCode(resources.getCoder());
        ValidationUtil.isNull(resource.getCoder(), "Permission", "id", resources.getCoder());

        isExitHttp(resources);

        Resource resource1 = this.getOne(new LambdaQueryWrapper<Resource>().eq(Resource::getName, resources.getName()));

        if (resource1 != null && !resource1.getCoder().equals(resource.getCoder())) {
            throw new EntityExistException(Resource.class, "name", resources.getName());
        }

        if (StringUtils.isNotBlank(resources.getComponentName())) {
            resource1 = this.getOne(new LambdaQueryWrapper<Resource>().eq(Resource::getComponentName, resources.getComponentName()));
            if (resource1 != null && !resource1.getCoder().equals(resource.getCoder())) {
                throw new EntityExistException(Resource.class, "componentName", resources.getComponentName());
            }
        }

        resource.setCoder(resources.getCoder());
        resource.setName(resources.getName());
        resource.setComponent(resources.getComponent());
        resource.setPath(resources.getPath());
        resource.setIcon(resources.getIcon());
        resource.setIFrame(resources.getIFrame());
        resource.setPcode(resources.getPcode());
        resource.setSorter(resources.getSorter());
        resource.setCache(resources.getCache());
        resource.setHidden(resources.getHidden());
        resource.setComponentName(resources.getComponentName());
        resource.setPermission(resources.getPermission());
        resource.setTyper(resources.getTyper());
        this.saveOrUpdate(resource);
    }


    @Override
    @CacheEvict(allEntries = true)
    public ResourceDto create(Resource resources) {
        isExitHttp(resources);
        if (this.getOne(new LambdaQueryWrapper<Resource>().eq(Resource::getName, resources.getName())) != null) {
            throw new EntityExistException(Resource.class, "name", resources.getName());
        }
        if (StringUtils.isNotBlank(resources.getComponentName())) {
            if (this.getOne(new LambdaQueryWrapper<Resource>().eq(Resource::getComponentName, resources.getComponentName())) != null) {
                throw new EntityExistException(Resource.class, "componentName", resources.getComponentName());
            }
        }
        this.save(resources);
        return generator.convert(resources, ResourceDto.class);
    }

    /**
     * 公共方法提取出来
     *
     * @param resources
     */
    private void isExitHttp(Resource resources) {
        if (resources.getIFrame()) {
            String http = "http://", https = "https://";
            if (!(resources.getPath().toLowerCase().startsWith(http) || resources.getPath().toLowerCase().startsWith(https))) {
                throw new BadRequestException("外链必须以http://或者https://开头");
            }
        }
    }

    @Override
    public Resource findByName(String name) {
        return null;
    }

    @Override
    public boolean updateByCode(Resource coder) {
        return false;
    }
}
