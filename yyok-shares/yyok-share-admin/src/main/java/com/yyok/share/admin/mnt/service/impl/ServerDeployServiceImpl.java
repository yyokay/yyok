package com.yyok.share.admin.mnt.service.impl;

import com.yyok.share.admin.mnt.domain.ServerDeploy;
import com.yyok.share.admin.mnt.service.IServerDeployService;
import com.yyok.share.admin.mnt.service.dto.ServerDeployDto;
import com.yyok.share.admin.mnt.service.dto.ServerDeployQueryCriteria;
import com.yyok.share.admin.mnt.service.mapper.IServerDeployMapper;
import com.yyok.share.framework.mapper.common.service.impl.BaseServiceImpl;
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
public class ServerDeployServiceImpl extends BaseServiceImpl<IServerDeployMapper, ServerDeploy> implements IServerDeployService {

    @Override
    public Object queryAll(ServerDeployQueryCriteria criteria, Pageable pageable) {
        return null;
    }

    @Override
    public List<ServerDeployDto> queryAll(ServerDeployQueryCriteria criteria) {
        return null;
    }

    @Override
    public ServerDeployDto findById(Long id) {
        return null;
    }

    @Override
    public void create(ServerDeploy resources) {

    }

    @Override
    public void update(ServerDeploy resources) {

    }

    @Override
    public void delete(Set<Long> ids) {

    }

    @Override
    public ServerDeployDto findByIp(String ip) {
        return null;
    }

    @Override
    public Boolean testConnect(ServerDeploy resources) {
        return null;
    }

    @Override
    public void download(List<ServerDeployDto> queryAll, HttpServletResponse response) throws IOException {

    }

    @Override
    public ServerDeploy findByName(String name) {
        return null;
    }

    @Override
    public boolean updateByCode(ServerDeploy coder) {
        return false;
    }
}
