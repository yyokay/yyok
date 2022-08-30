package com.yyok.share.admin.security.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yyok.share.admin.info.domain.User;
import com.yyok.share.admin.info.service.IUserService;
import com.yyok.share.admin.security.security.vo.JwtAccount;
import com.yyok.share.admin.system.domain.Account;
import com.yyok.share.admin.system.domain.Resource;
import com.yyok.share.admin.system.domain.Role;
import com.yyok.share.admin.system.domain.RoleResource;
import com.yyok.share.admin.system.service.IAccountService;
import com.yyok.share.admin.system.service.IAccountsRolesService;
import com.yyok.share.admin.system.service.IRoleService;
import com.yyok.share.admin.system.service.IRolesResourcesService;
import com.yyok.share.admin.system.service.dto.RoleDto;
import com.yyok.share.admin.system.service.dto.RoleSmallDto;
import com.yyok.share.admin.system.service.mapper.IResourceMapper;
import com.yyok.share.framework.dozer.service.IGenerator;
import com.yyok.share.framework.exception.BadRequestException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("userDetailsService")
@AllArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class UserDetailsServiceImpl implements UserDetailsService {

    private final IAccountService accountService;

    private final IUserService userService;
    private final IRoleService roleService;

    private final IAccountsRolesService accountRolesService;

    private final IResourceMapper resourceMapper;

    private final IRolesResourcesService rolesResourcesService;
    private final IGenerator generator;

    @Override
    public UserDetails loadUserByUsername(String username) {
        List<Account> accountList = accountService.list(new LambdaQueryWrapper<Account>().eq(Account::getAccount,username));//findByName(username);
        Account account =null;
        if(accountList.size()>=1) {
            account = accountList.get(0);
        }
        List<RoleSmallDto> roleSmallDtoList = roleService.findByAccountsCode(account.getCoder());
        Set<Resource> resources = new HashSet<Resource>();
        Set<RoleDto> roleSet = new HashSet<RoleDto>();
        for(RoleSmallDto rd : roleSmallDtoList) {
            resources.addAll(resourceMapper.findResourceByRoleCode(rd.getCoder()));
            RoleDto rdo = generator.convert(rd, RoleDto.class);
            rdo.setResources(resources);
            roleSet.add(rdo);
        }
        account.setRoles(generator.convert(roleSet,Role.class));
        UserDetails ud = null;
        if (account == null) {
            throw new BadRequestException("账号不存在");
        } else {
            if (account.getEnabled()!=1) {
                throw new BadRequestException("账号未激活");
            }
            return createJwtUser(account);
        }
    }

    private UserDetails createJwtUser(Account account) {
        account = roleService.mapToGrantedAuthorities(account);
        return new JwtAccount(
                account.getCoder(),
                account.getAccount(),
                account.getAccount(),
                account.getNickName(),
                account.getUserCode(),
                account.getAvatarPath(),
                account.getEmail(),
                account.getPhone(),
                account.getEnabled(),
                account.getPassword(),
                account.getAuthorities(),
                account.getUser(),
                account.getLastPasswordResetTime(),
                account.getToken(),
                account.getAuthentication(),
                account.getRoles()/*,
                account.getAcls(),
                account.getAbacs()*/
                //account.getAuthorities()
                //Optional.ofNullable(account.getDept()).map(DeptSmallDto::getName).orElse(null),
                //Optional.ofNullable(account.getJob()).map(JobSmallDto::getName).orElse(null),

        );
    }
}