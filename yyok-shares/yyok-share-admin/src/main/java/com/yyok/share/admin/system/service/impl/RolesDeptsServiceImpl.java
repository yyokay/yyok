package com.yyok.share.admin.system.service.impl;

import com.yyok.share.admin.system.domain.RolesDepts;
import com.yyok.share.admin.system.service.IRolesDeptsService;
import com.yyok.share.admin.system.service.mapper.IRolesDeptsMapper;
import com.yyok.share.framework.mapper.common.service.impl.BaseServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author yyok
 * @date 2020-05-16
 */
@Service
@AllArgsConstructor
//@CacheConfig(cacheNames = "rolesDepts")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class RolesDeptsServiceImpl extends BaseServiceImpl<IRolesDeptsMapper, RolesDepts> implements IRolesDeptsService {

    @Override
    public RolesDepts findByName(String name) {
        return null;
    }

    @Override
    public boolean updateByCode(RolesDepts coder) {
        return false;
    }
}
