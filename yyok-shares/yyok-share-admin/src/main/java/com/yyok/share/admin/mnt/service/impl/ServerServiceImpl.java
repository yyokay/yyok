package com.yyok.share.admin.mnt.service.impl;

import com.yyok.share.admin.mnt.domain.Server;
import com.yyok.share.admin.mnt.service.IServerService;
import com.yyok.share.admin.mnt.service.dto.ServerDto;
import com.yyok.share.admin.mnt.service.dto.ServerQueryCriteria;
import com.yyok.share.admin.mnt.service.mapper.IServerMapper;
import com.yyok.share.framework.mapper.common.service.impl.BaseServiceImpl;
import com.yyok.share.framework.utils.FileUtil;
import lombok.AllArgsConstructor;
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
public class ServerServiceImpl extends BaseServiceImpl<IServerMapper, Server> implements IServerService {


    @Override
    public Object queryAll(ServerQueryCriteria criteria, Pageable pageable) {
        return null;
    }

    @Override
    public List<ServerDto> queryAll(ServerQueryCriteria criteria) {
        return null;
    }

    @Override
    public ServerDto findById(Long id) {
        return null;
    }

    @Override
    public void create(Server resources) {

    }

    @Override
    public void update(Server resources) {

    }

    @Override
    public void delete(Set<Long> ids) {

    }

    @Override
    public void download(List<Server> queryAll, HttpServletResponse response) throws IOException {

    }

    @Override
    public Server findByName(String name) {
        return null;
    }

    @Override
    public boolean updateByCode(Server coder) {
        return false;
    }
}
