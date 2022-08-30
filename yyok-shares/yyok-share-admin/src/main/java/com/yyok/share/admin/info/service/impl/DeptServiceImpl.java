package com.yyok.share.admin.info.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.yyok.share.admin.info.domain.Dept;
import com.yyok.share.admin.info.domain.Job;
import com.yyok.share.admin.info.service.IDeptService;
import com.yyok.share.admin.info.service.dto.DeptDto;
import com.yyok.share.admin.info.service.dto.DeptQueryCriteria;
import com.yyok.share.admin.info.service.mapper.IDeptMapper;
import com.yyok.share.admin.info.service.mapper.IJobMapper;
import com.yyok.share.admin.system.domain.RolesDepts;
import com.yyok.share.admin.system.service.mapper.IRolesDeptsMapper;
import com.yyok.share.framework.dozer.service.IGenerator;
import com.yyok.share.framework.exception.BadRequestException;
import com.yyok.share.framework.mapper.common.service.impl.BaseServiceImpl;
import com.yyok.share.framework.mapper.common.utils.QueryHelpPlus;
import com.yyok.share.framework.utils.FileUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

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
//@CacheConfig(cacheNames = "dept")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DeptServiceImpl extends BaseServiceImpl<IDeptMapper, Dept> implements IDeptService {

    private final IGenerator generator;

    private final IDeptMapper deptMapper;

    private final IJobMapper jobMapper;

    private final IRolesDeptsMapper rolesDeptsMapper;

    @Override
    //@Cacheable
    public Map<String, Object> queryAll(DeptQueryCriteria criteria, Pageable pageable) {
        getPage(pageable);
        PageInfo<Dept> page = new PageInfo<>(queryAll(criteria));
        Map<String, Object> map = new LinkedHashMap<>(2);
        map.put("content", generator.convert(page.getList(), DeptDto.class));
        map.put("totalElements", page.getTotal());
        return map;
    }


    @Override
    //@Cacheable
    public List<Dept> queryAll(DeptQueryCriteria criteria) {
        return baseMapper.selectList(QueryHelpPlus.getPredicate(Dept.class, criteria));
    }


    @Override
    public void download(List<DeptDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (DeptDto dept : all) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("名称", dept.getName());
            map.put("上级部门", dept.getPcode());
            map.put("状态", dept.getEnabled());
            map.put("创建日期", dept.getCreateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    /**
     * 根据Pcoder查询
     *
     * @param pcode /
     * @return /
     */
    @Override
//    @Cacheable(key = "#p0")
    public List<Dept> findByPcode(String pcode) {
        DeptQueryCriteria criteria = new DeptQueryCriteria();
        criteria.setPcode(pcode);
        return baseMapper.selectList(QueryHelpPlus.getPredicate(Dept.class, criteria));
    }

    /**
     * 构建树形数据
     *
     * @param deptDtos 原始数据
     * @return /
     */
    @Override
    public Object buildTree(List<DeptDto> deptDtos) {
        Set<DeptDto> trees = new LinkedHashSet<>();
        Set<DeptDto> depts = new LinkedHashSet<>();
        List<String> deptNames = deptDtos.stream().map(DeptDto::getName).collect(Collectors.toList());
        boolean isChild;
        DeptQueryCriteria criteria = new DeptQueryCriteria();
        List<Dept> deptList = this.queryAll(criteria);
        for (DeptDto deptDto : deptDtos) {
            isChild = false;
            if ("0".equals(deptDto.getPcode())) {
                trees.add(deptDto);
            }
            for (DeptDto it : deptDtos) {
                if (it.getPcode().equals(deptDto.getCoder())) {
                    isChild = true;
                    if (deptDto.getChildren() == null) {
                        deptDto.setChildren(new ArrayList<>());
                    }
                    deptDto.getChildren().add(it);
                }
            }
            if (isChild) {
                depts.add(deptDto);
                for (Dept dept : deptList) {
                    if (dept.getCoder().equals(deptDto.getPcode()) && !deptNames.contains(dept.getName())) {
                        depts.add(deptDto);
                    }
                }
            }
        }

        if (CollectionUtils.isEmpty(trees)) {
            trees = depts;
        }

        Integer totalElements = deptDtos.size();

        Map<String, Object> map = new HashMap<>(2);
        map.put("totalElements", totalElements);
        map.put("content", CollectionUtils.isEmpty(trees) ? deptDtos : trees);
        return map;
    }

    /**
     * 删除部门
     *
     * @param coders
     */
    @Override
    public void delDepts(List<String> coders) {
        Integer jobCount = null;// jobMapper.selectCount(Wrappers.<Job>lambdaQuery().in(Job::getCoder, coders));
        Integer roleCount = null;//rolesDeptsMapper.selectCount(Wrappers.<RolesDepts>lambdaQuery().in(RolesDepts::getCoder, coders));
        if (jobCount > 0) {
            throw new BadRequestException("所选部门中存在与岗位关联，请取消关联后再试");
        }
        if (roleCount > 0) {
            throw new BadRequestException("所选部门中存在与角色关联，请取消关联后再试");
        }
        this.removeByCodes(coders);
    }

    /**
     * 获取待删除的部门
     *
     * @param deptList /
     * @param deptDtos /
     * @return /
     */
/*    @Override
    public Set<DeptDto> getDeleteDepts(List<Dept> deptList, Set<DeptDto> deptDtos) {

        for (Dept dept : deptList) {
            deptDtos.add((DeptDto)generator.convert(deptList,DeptDto.class));
            List<Dept> depts = Collections.singletonList(this.getOne(new LambdaQueryWrapper<Dept>().eq("id", dept.getCoder())));
            if(depts!=null && depts.size()!=0){
                getDeleteDepts(depts, deptDtos);
            }
        }
        return deptDtos;
    }*/

    /**
     * 根据角色coder查询
     *
     * @param coder /
     * @return /
     */
    @Override
    public Set<Dept> findByRoleCodes(String coder) {
        return deptMapper.findDeptByRoleCode(coder);
    }

    @Override
    public Dept findByName(String name) {
        return null;
    }

    @Override
    public boolean updateByCode(Dept coder) {
        return false;
    }
}
