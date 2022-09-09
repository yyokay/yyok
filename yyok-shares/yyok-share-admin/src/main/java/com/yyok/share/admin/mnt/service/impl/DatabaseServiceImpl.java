package com.yyok.share.admin.mnt.service.impl;

import cn.hutool.core.util.IdUtil;
import com.yyok.share.admin.mnt.domain.Database;
import com.yyok.share.admin.mnt.service.IDatabaseService;
import com.yyok.share.admin.mnt.service.dto.DatabaseDto;
import com.yyok.share.admin.mnt.service.dto.DatabaseQueryCriteria;
import com.yyok.share.admin.mnt.service.mapper.IDatabaseMapper;
import com.yyok.share.framework.mapper.common.service.impl.BaseServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Service
@AllArgsConstructor
//@CacheConfig(cacheNames = "userAvatar")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DatabaseServiceImpl extends BaseServiceImpl<IDatabaseMapper, Database> implements IDatabaseService {

    @Override
    public Object queryAll(DatabaseQueryCriteria criteria, Pageable pageable) {
        return null;
    }

    @Override
    public List<DatabaseDto> queryAll(DatabaseQueryCriteria criteria) {
        return null;
    }

    @Override
    public DatabaseDto findById(String id) {
        return null;
    }

    @Override
    public void create(Database resources) {

    }

    @Override
    public void update(Database resources) {

    }

    @Override
    public void delete(Set<String> ids) {

    }

    @Override
    public boolean testConnection(Database resources) {
        return false;
    }

    @Override
    public void download(List<DatabaseDto> queryAll, HttpServletResponse response) throws IOException {

    }

    @Override
    public Database findByName(String name) {
        return null;
    }

    @Override
    public boolean updateByCode(Database coder) {
        return false;
    }
}
