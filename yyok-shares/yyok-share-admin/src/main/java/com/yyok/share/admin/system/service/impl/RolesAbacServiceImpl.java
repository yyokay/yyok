package com.yyok.share.admin.system.service.impl;

import com.yyok.share.admin.system.domain.RoleAbac;
import com.yyok.share.admin.system.domain.RoleAcl;
import com.yyok.share.admin.system.domain.vo.RoleAbacVo;
import com.yyok.share.admin.system.domain.vo.RoleAclVo;
import com.yyok.share.admin.system.service.IRolesAbacService;
import com.yyok.share.admin.system.service.IRolesAclService;
import com.yyok.share.admin.system.service.mapper.IRolesAbacMapper;
import com.yyok.share.admin.system.service.mapper.IRolesAclMapper;
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
public class RolesAbacServiceImpl extends BaseServiceImpl<IRolesAbacMapper, RoleAbac> implements IRolesAbacService {

    @Override
    public boolean grantAuthorizater(List<RoleAbacVo> roleAbacVo) {
        return false;
    }

    @Override
    public RoleAbac findByName(String name) {
        return null;
    }

    @Override
    public boolean updateByCode(RoleAbac coder) {
        return false;
    }
}
