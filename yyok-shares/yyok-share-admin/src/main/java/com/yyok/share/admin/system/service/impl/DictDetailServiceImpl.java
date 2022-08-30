package com.yyok.share.admin.system.service.impl;

import com.github.pagehelper.PageInfo;
import com.yyok.share.admin.system.domain.DictDetail;
import com.yyok.share.admin.system.service.IDictDetailService;
import com.yyok.share.admin.system.service.dto.DictDetailDto;
import com.yyok.share.admin.system.service.dto.DictDetailQueryCriteria;
import com.yyok.share.admin.system.service.mapper.IDictDetailMapper;
import com.yyok.share.framework.dozer.service.IGenerator;
import com.yyok.share.framework.mapper.common.service.impl.BaseServiceImpl;
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
//@CacheConfig(cacheNames = "dictDetail")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DictDetailServiceImpl extends BaseServiceImpl<IDictDetailMapper, DictDetail> implements IDictDetailService {

    private final IGenerator generator;

    @Override
    //@Cacheable
    public Map<String, Object> queryAll(DictDetailQueryCriteria criteria, Pageable pageable) {
        getPage(pageable);
        PageInfo<DictDetail> page = new PageInfo<>(queryAll(criteria));
        Map<String, Object> map = new LinkedHashMap<>(2);
        map.put("content", generator.convert(page.getList(), DictDetailDto.class));
        map.put("totalElements", page.getTotal());
        return map;
    }


    @Override
    //@Cacheable
    public List<DictDetail> queryAll(DictDetailQueryCriteria criteria) {
        List<DictDetail> list = baseMapper.selectDictDetailList(criteria.getLabel(), criteria.getDictName());
        return list;
    }


    @Override
    public void download(List<DictDetailDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (DictDetailDto dictDetail : all) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("字典标签", dictDetail.getLabel());
            map.put("字典值", dictDetail.getValue());
            map.put("排序", dictDetail.getSorter());
            map.put("字典id", dictDetail.getDictCode());
            map.put("创建日期", dictDetail.getCreateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    public DictDetail findByName(String name) {
        return null;
    }

    @Override
    public boolean updateByCode(DictDetail coder) {
        return false;
    }
}
