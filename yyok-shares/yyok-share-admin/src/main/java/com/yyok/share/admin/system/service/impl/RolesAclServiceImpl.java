package com.yyok.share.admin.system.service.impl;

import com.yyok.share.admin.system.domain.RoleAcl;
import com.yyok.share.admin.system.domain.RoleResource;
import com.yyok.share.admin.system.domain.vo.RoleAclVo;
import com.yyok.share.admin.system.service.IRolesAclService;
import com.yyok.share.admin.system.service.IRolesResourcesService;
import com.yyok.share.admin.system.service.mapper.IRolesAclMapper;
import com.yyok.share.admin.system.service.mapper.IRolesResourcesMapper;
import com.yyok.share.framework.mapper.common.service.impl.BaseServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
//@CacheConfig(cacheNames = "rolesResources")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class RolesAclServiceImpl extends BaseServiceImpl<IRolesAclMapper, RoleAcl> implements IRolesAclService {
    @Override
    public boolean grantAuthorizater(List<RoleAclVo> roleAclVo) {
        return false;
    }

    @Override
    public RoleAcl findByName(String name) {
        return null;
    }

    @Override
    public boolean updateByCode(RoleAcl coder) {
        return false;
    }
}
