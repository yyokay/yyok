package com.yyok.share.admin.system.service;

import com.yyok.share.admin.system.domain.RoleAcl;
import com.yyok.share.admin.system.domain.vo.RoleAclVo;
import com.yyok.share.framework.mapper.common.service.IBaseService;

import java.util.List;

public interface IRolesAclService extends IBaseService<RoleAcl> {
    public boolean grantAuthorizater(List<RoleAclVo> roleAclVo);
}
