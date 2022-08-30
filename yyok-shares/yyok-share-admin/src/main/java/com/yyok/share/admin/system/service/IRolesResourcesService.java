package com.yyok.share.admin.system.service;

import com.yyok.share.admin.system.domain.RoleResource;
import com.yyok.share.admin.system.domain.vo.AccountRoleVo;
import com.yyok.share.admin.system.domain.vo.RoleResourceVo;
import com.yyok.share.framework.mapper.common.service.IBaseService;

import java.util.List;

/**
 * @author yyok
 * @date 2020-05-16
 */
public interface IRolesResourcesService extends IBaseService<RoleResource> {

    public boolean grantAuthorizater(List<RoleResourceVo> roleResources);
}
