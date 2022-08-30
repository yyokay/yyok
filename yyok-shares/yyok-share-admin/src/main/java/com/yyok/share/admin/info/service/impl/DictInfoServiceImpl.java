package com.yyok.share.admin.info.service.impl;

import com.github.pagehelper.PageInfo;
import com.yyok.share.admin.info.domain.DictInfo;
import com.yyok.share.admin.info.service.IDictInfoService;
import com.yyok.share.admin.info.service.dto.DictInfoDto;
import com.yyok.share.admin.info.service.dto.DictInfoQueryCriteria;
import com.yyok.share.admin.info.service.mapper.IDictInfoMapper;
import com.yyok.share.framework.dozer.service.IGenerator;
import com.yyok.share.framework.mapper.common.service.impl.BaseServiceImpl;
import com.yyok.share.framework.mapper.common.utils.QueryHelpPlus;
import com.yyok.share.framework.utils.FileUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
//@CacheConfig(cacheNames = "dict")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DictInfoServiceImpl extends BaseServiceImpl<IDictInfoMapper, DictInfo> implements IDictInfoService {

    private final IGenerator generator;

    @Override
    //@Cacheable
    public Map<String, Object> queryAll(DictInfoQueryCriteria criteria, Pageable pageable) {
        getPage(pageable);
        PageInfo<DictInfo> page = new PageInfo<>(queryAll(criteria));
        Map<String, Object> map = new LinkedHashMap<>(2);
        map.put("content", generator.convert(page.getList(), DictInfoDto.class));
        map.put("totalElements", page.getTotal());
        return map;
    }


    @Override
    //@Cacheable
    public List<DictInfo> queryAll(DictInfoQueryCriteria criteria) {
        return baseMapper.selectList(QueryHelpPlus.getPredicate(DictInfo.class, criteria));
    }


    @Override
    public void download(List<DictInfoDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (DictInfoDto dict : all) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("字典名称", dict.getName());
            map.put("描述", dict.getRemark());
            map.put("创建日期", dict.getCreateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    public DictInfo findByName(String name) {
        return null;
    }

    @Override
    public boolean updateByCode(DictInfo coder) {
        return false;
    }
}
