package com.yyok.share.admin.system.service;

import com.yyok.share.admin.system.domain.Resource;
import com.yyok.share.admin.system.domain.vo.ResourceVo;
import com.yyok.share.admin.system.service.dto.ResourceDto;
import com.yyok.share.admin.system.service.dto.ResourceQueryCriteria;
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
public interface IResourceService extends IBaseService<Resource> {

    /**
     * 查询数据分页
     *
     * @param criteria 条件
     * @param pageable 分页参数
     * @return Map<String, Object>
     */
    Map<String, Object> queryAll(ResourceQueryCriteria criteria, Pageable pageable);

    /**
     * 查询所有数据不分页
     *
     * @param criteria 条件参数
     * @return List<ResourceDto>
     */
    List<Resource> queryAll(ResourceQueryCriteria criteria);

    /**
     * 导出数据
     *
     * @param all      待导出的数据
     * @param response /
     * @throws IOException /
     */
    void download(List<ResourceDto> all, HttpServletResponse response) throws IOException;

    /**
     * 构建菜单树
     *
     * @param resourceDtos 原始数据
     * @return /
     */
    Map<String, Object> buildTree(List<ResourceDto> resourceDtos);

    /**
     * 构建菜单树
     *
     * @param resourceDtos /
     * @return /
     */
    List<ResourceVo> buildResources(List<ResourceDto> resourceDtos);

    /**
     * 获取菜单树
     *
     * @param resources /
     * @return /
     */
    Object getResourceTree(List<Resource> resources);


    /**
     * 获取待删除的菜单
     *
     * @param resourceList /
     * @param resourceSet  /
     * @return /
     */
    Set<Resource> getDeleteResources(List<Resource> resourceList, Set<Resource> resourceSet);

    /**
     * 根据pcode查询
     *
     * @param pcode /
     * @return /
     */
    List<Resource> findByPcode(String pcode);

    /**
     * 根据角色查询
     *
     * @param roles /
     * @return /
     */
    List<ResourceDto> findByRoles(List<RoleSmallDto> roles);

    /**
     * 删除
     *
     * @param resourceSet /
     */
    void delete(Set<Resource> resourceSet);

    /**
     * 编辑
     *
     * @param resources /
     */
    void update(Resource resources);

    Object create(Resource resources);
}
