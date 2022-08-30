package com.yyok.share.admin.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yyok.share.admin.system.domain.AccountRole;
import com.yyok.share.admin.system.domain.RoleResource;
import com.yyok.share.admin.system.domain.vo.AccountRoleVo;
import com.yyok.share.admin.system.domain.vo.RoleResourceVo;
import com.yyok.share.admin.system.service.IRolesResourcesService;
import com.yyok.share.admin.system.service.mapper.IRolesResourcesMapper;
import com.yyok.share.framework.dozer.service.IGenerator;
import com.yyok.share.framework.mapper.common.service.impl.BaseServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author yyok
 * @date 2020-05-16
 */
@Service
@AllArgsConstructor
//@CacheConfig(cacheNames = "rolesResources")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class RolesResourcesServiceImpl extends BaseServiceImpl<IRolesResourcesMapper, RoleResource> implements IRolesResourcesService {

    private final IGenerator generator;

    @Override
    public RoleResource findByName(String name) {
        return null;
    }

    @Override
    public boolean updateByCode(RoleResource coder) {
        return false;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = RuntimeException.class)
    public boolean grantAuthorizater(List<RoleResourceVo> roleResources) {
        try {
            Collection<String> roleList = roleResources.stream().map(RoleResourceVo::getRoleCode).collect(Collectors.toList());
            this.remove(new LambdaQueryWrapper<RoleResource>().in(RoleResource::getRoleCode,roleList));
            Collection<RoleResource> cars = generator.convert(roleResources, RoleResource.class);
            this.saveBatch(cars);
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

}
