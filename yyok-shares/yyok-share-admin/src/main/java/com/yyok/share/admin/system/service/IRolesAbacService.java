package com.yyok.share.admin.system.service;

import com.yyok.share.admin.system.domain.RoleAbac;
import com.yyok.share.admin.system.domain.vo.RoleAbacVo;
import com.yyok.share.framework.mapper.common.service.IBaseService;

import java.util.List;

public interface IRolesAbacService  extends IBaseService<RoleAbac> {

    public boolean grantAuthorizater(List<RoleAbacVo> roleAbacVo);
}
