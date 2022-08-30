package com.yyok.share.admin.info.service;

import com.yyok.share.admin.info.domain.Dept;
import com.yyok.share.admin.info.service.dto.DeptDto;
import com.yyok.share.admin.info.service.dto.DeptQueryCriteria;
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
public interface IDeptService extends IBaseService<Dept> {

    /**
     * 查询数据分页
     *
     * @param criteria 条件
     * @param pageable 分页参数
     * @return Map<String, Object>
     */
    Map<String, Object> queryAll(DeptQueryCriteria criteria, Pageable pageable);

    /**
     * 查询所有数据不分页
     *
     * @param criteria 条件参数
     * @return List<DeptDto>
     */
    List<Dept> queryAll(DeptQueryCriteria criteria);

    /**
     * 导出数据
     *
     * @param all      待导出的数据
     * @param response /
     * @throws IOException /
     */
    void download(List<DeptDto> all, HttpServletResponse response) throws IOException;

    /**
     * 根据pcode查询
     *
     * @param pcode /
     * @return /
     */
    List<Dept> findByPcode(String pcode);

    /**
     * 构建树形数据
     *
     * @param deptDtos 原始数据
     * @return /
     */
    Object buildTree(List<DeptDto> deptDtos);


    /**
     * 删除部门
     *
     * @param deptCodes
     */
    void delDepts(List<String> deptCodes);

    /**
     * 获取待删除的部门
     * @param deptList /
     * @param deptDtos /
     * @return /
     */
    /*Set<DeptDto> getDeleteDepts(List<Dept> deptList, Set<DeptDto> deptDtos);*/

    /**
     * 根据角色coder查询
     *
     * @param coder /
     * @return /
     */
    Set<Dept> findByRoleCodes(String coder);
}
