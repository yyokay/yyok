package com.yyok.share.admin.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yyok.share.admin.quartz.domain.QuartzJob;
import com.yyok.share.admin.system.domain.AccountRole;
import com.yyok.share.admin.system.domain.vo.AccountRoleVo;
import com.yyok.share.admin.system.service.IAccountsRolesService;
import com.yyok.share.admin.system.service.mapper.IAccountsRolesMapper;
import com.yyok.share.framework.dozer.service.IGenerator;
import com.yyok.share.framework.mapper.common.service.impl.BaseServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yyok
 * @date 2020-05-16
 */
@Service
@AllArgsConstructor
//@CacheConfig(cacheNames = "usersRoles")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class AccountsRolesServiceImpl extends BaseServiceImpl<IAccountsRolesMapper, AccountRole> implements IAccountsRolesService {


    private final IGenerator generator;

    @Override
    public AccountRole findByName(String name) {
        return null;
    }

    @Override
    public boolean updateByCode(AccountRole coder) {
        return false;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = RuntimeException.class)
    public boolean grantAuthorization(List<AccountRoleVo> accountsRoles) {
        try {
            Collection<String> accountCodeList = accountsRoles.stream().map(AccountRoleVo::getAccountCode).collect(Collectors.toList());
            this.remove(new LambdaQueryWrapper<AccountRole>().in(AccountRole::getAccountCode, accountCodeList));
            Collection<AccountRole> cars = generator.convert(accountsRoles, AccountRole.class);
            this.saveBatch(cars);
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
      }

    @Override
    public HashMap verification(HashMap hs) {

        return hs;
    }
}
