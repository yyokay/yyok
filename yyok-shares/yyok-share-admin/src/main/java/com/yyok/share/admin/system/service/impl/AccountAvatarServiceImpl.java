package com.yyok.share.admin.system.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.github.pagehelper.PageInfo;
import com.yyok.share.admin.system.domain.AccountAvatar;
import com.yyok.share.admin.system.service.IAccountAvatarService;
import com.yyok.share.admin.system.service.dto.AccountAvatarDto;
import com.yyok.share.admin.system.service.dto.AccountAvatarQueryCriteria;
import com.yyok.share.admin.system.service.mapper.IAccountAvatarMapper;
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
//@CacheConfig(cacheNames = "userAvatar")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class AccountAvatarServiceImpl extends BaseServiceImpl<IAccountAvatarMapper, AccountAvatar> implements IAccountAvatarService {

    private final IGenerator generator;

    @Override
    //@Cacheable
    public Map<String, Object> queryAll(AccountAvatarQueryCriteria criteria, Pageable pageable) {
        getPage(pageable);
        PageInfo<AccountAvatar> page = new PageInfo<>(queryAll(criteria));
        Map<String, Object> map = new LinkedHashMap<>(2);
        map.put("content", generator.convert(page.getList(), AccountAvatarDto.class));
        map.put("totalElements", page.getTotal());
        return map;
    }


    @Override
    //@Cacheable
    public List<AccountAvatar> queryAll(AccountAvatarQueryCriteria criteria) {
        return baseMapper.selectList(QueryHelpPlus.getPredicate(AccountAvatar.class, criteria));
    }

    @Override
    public void download(List<AccountAvatarDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (AccountAvatarDto userAvatar : all) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("真实文件名", userAvatar.getRealName());
            map.put("路径", userAvatar.getPath());
            map.put("大小", userAvatar.getSize());
            map.put("创建时间", userAvatar.getCreateTime());
            map.put("真实文件名", userAvatar.getRealName());
            map.put("路径", userAvatar.getPath());
            map.put("大小", userAvatar.getSize());
            map.put("创建时间", userAvatar.getCreateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    public AccountAvatar saveFile(AccountAvatar userAvatar) {
        return null;
    }

    @Override
    public AccountAvatar findByName(String name) {
        return null;
    }

    @Override
    public boolean updateByCode(AccountAvatar coder) {
        return false;
    }
}
