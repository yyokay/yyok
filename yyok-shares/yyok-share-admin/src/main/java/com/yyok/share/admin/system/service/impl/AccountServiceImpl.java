package com.yyok.share.admin.system.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageInfo;
import com.yyok.share.admin.info.service.mapper.IUserMapper;
import com.yyok.share.admin.system.domain.Account;
import com.yyok.share.admin.system.domain.AccountAvatar;
import com.yyok.share.admin.system.service.IAccountAvatarService;
import com.yyok.share.admin.system.service.IAccountService;
import com.yyok.share.admin.system.service.dto.AccountDto;
import com.yyok.share.admin.system.service.dto.AccountQueryCriteria;
import com.yyok.share.admin.system.service.mapper.IAccountMapper;
import com.yyok.share.admin.system.service.mapper.IRoleMapper;
import com.yyok.share.framework.dozer.service.IGenerator;
import com.yyok.share.framework.exception.BadRequestException;
import com.yyok.share.framework.exception.EntityExistException;
import com.yyok.share.framework.mapper.common.service.impl.BaseServiceImpl;
import com.yyok.share.framework.mapper.common.utils.QueryHelpPlus;
import com.yyok.share.framework.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;

// 默认不使用缓存
//import org.springframework.cache.annotation.CacheConfig;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;

/**
 * @author yyok
 * @date 2020-05-14
 */
@Slf4j
@Service
//@AllArgsConstructor
//@CacheConfig(cacheNames = "account")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class AccountServiceImpl extends BaseServiceImpl<IAccountMapper, Account> implements IAccountService {

    private final IGenerator generator;
    private final IAccountMapper accountMapper;
    private final IAccountAvatarService accountAvatarService;
    private final IRoleMapper roleMapper;
    private final IUserMapper userMapper;
    private final RedisUtils redisUtils;
    @Value("${file.avatar}")
    private String avatar;

    public AccountServiceImpl(IUserMapper userMapper, IGenerator generator, IAccountMapper accountMapper, IAccountAvatarService accountAvatarService, IRoleMapper roleMapper, RedisUtils redisUtils) {
        this.generator = generator;
        this.accountMapper = accountMapper;
        this.accountAvatarService = accountAvatarService;
        this.roleMapper = roleMapper;
        this.redisUtils = redisUtils;
        this.userMapper = userMapper;
    }

    @Override
    //@Cacheable
    public Map<String, Object> queryAll(AccountQueryCriteria criteria, Pageable pageable) {
        getPage(pageable);
        PageInfo<Account> page = new PageInfo<>(queryAll(criteria));
        Map<String, Object> map = new LinkedHashMap<>(2);
        try {
            map.put("content", generator.convert(page.getList(), AccountDto.class));
            map.put("totalElements", page.getTotal());
        } catch (BadRequestException e) {
            log.error("" + e);
            map = null;
        }

        return map;
    }


    @Override
    //@Cacheable
    public List<Account> queryAll(AccountQueryCriteria criteria) {
        List<Account> accountList = baseMapper.selectList(QueryHelpPlus.getPredicate(Account.class, criteria));
    /*    for (Account account : accountList) {
            account.setJob(jobService.findByCode(account.getJobCode()));
            account.setDept(deptService.findByCode(account.getDeptCode()));
        }*/
        return accountList;
    }


    @Override
    public void download(List<AccountDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (AccountDto account : all) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("邮箱", account.getEmail());
            map.put("状态：1启用、0禁用", account.getEnabled());
            map.put("用户名", account.getUsername());
            map.put("部门名称", account.getDeptCode());
            map.put("手机号码", account.getPhone());
            map.put("创建日期", account.getCreateTime());
            map.put("昵称", account.getNickName());
            map.put("性别", account.getSex());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    /**
     * 根据用户名查询
     *
     * @param accountName /
     * @return /
     */
    @Override
    public Account findByName(String accountName) {
        Account account = accountMapper.findByName(accountName);
        //用户所属岗位
        //account.setJob(jobService.findByCode(account.getJobCode()));
        //用户所属部门
        //account.setDept(deptService.findByCode(account.getDeptCode()));

        return account;
    }

    @Override
    public boolean updateByCode(Account coder) {
        return false;
    }

    /**
     * 修改密码
     *
     * @param accountname     用户名
     * @param encryptPassword 密码
     */
    @Override
    public void updatePass(String accountname, String encryptPassword) {
        accountMapper.updatePass(encryptPassword, DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"), accountname);
    }

    /**
     * 修改头像
     *
     * @param multipartFile 文件
     */
    @Override
    public void updateAvatar(MultipartFile multipartFile) {
        Account account = this.getOne(new LambdaQueryWrapper<Account>()
                .eq(Account::getAccount, SecurityUtils.getAccountName()));
        AccountAvatar accountAvatar = accountAvatarService.getOne(new LambdaQueryWrapper<AccountAvatar>()
                .eq(AccountAvatar::getCoder, account.getCoder()));
        String oldPath = "";
        if (accountAvatar != null) {
            oldPath = accountAvatar.getPath();
        } else {
            accountAvatar = new AccountAvatar();
        }
        File file = FileUtil.upload(multipartFile, avatar);
        assert file != null;
        accountAvatar.setRealName(file.getName());
        accountAvatar.setPath(file.getPath());
        accountAvatar.setSize(FileUtil.getSize(multipartFile.getSize()));
        accountAvatarService.saveOrUpdate(accountAvatar);
        account.setCoder(accountAvatar.getCoder());
        this.saveOrUpdate(account);
        if (StringUtils.isNotBlank(oldPath)) {
            FileUtil.del(oldPath);
        }
    }

    /**
     * 修改邮箱
     *
     * @param accountname 用户名
     * @param email       邮箱
     */
    @Override
    public void updateEmail(String accountname, String email) {
        accountMapper.updateEmail(email, accountname);
    }

    /**
     * 新增用户
     *
     * @param resources /
     * @return /
     */
    @Override
    //@CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public boolean create(Account resources) {
        Account accountName = this.getOne(new LambdaQueryWrapper<Account>()
                .eq(Account::getAccount, resources.getAccount()));
        if (accountName != null) {
            throw new EntityExistException(Account.class, "account", resources.getAccount());
        }
        Account accountEmail = this.getOne(new LambdaQueryWrapper<Account>()
                .eq(Account::getEmail, resources.getEmail()));
        if (accountEmail != null) {
            throw new EntityExistException(Account.class, "email", resources.getEmail());
        }
        //resources.setDeptCode(resources.getDept().getCoder());
        //resources.setJobCode(resources.getJob().getCoder());
        boolean result = this.save(resources);
       /* AccountsRoles accountsRoles = new AccountsRoles();
        accountsRoles.setaccountCode(resources.getCoder());
        Set<Role> set = resources.getRoles();
        for (Role roleIds : set) {
            accountsRoles.setRoleCode(roleIds.getCoder());
        }
        if (result) {
            accountsRolesService.save(accountsRoles);
        }*/
        return result;
    }

    /**
     * 编辑用户
     *
     * @param resources /
     */
    @Override
    //@CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void update(Account resources) {
        Account account = this.getOne(new LambdaQueryWrapper<Account>()
                .eq(Account::getCoder, resources.getCoder()));
        ValidationUtil.isNull(account.getCoder(), "Account", "id", resources.getCoder());
        Account account1 = this.getOne(new LambdaQueryWrapper<Account>()
                .eq(Account::getAccount, resources.getAccount()));
        Account account2 = this.getOne(new LambdaQueryWrapper<Account>()
                .eq(Account::getEmail, resources.getEmail()));

        if (account1 != null && !account.getCoder().equals(account1.getCoder())) {
            throw new BadRequestException("当前用户名已存在");
        }

        if (account2 != null && !account.getCoder().equals(account2.getCoder())) {
            throw new EntityExistException(Account.class, "email", resources.getEmail());
        }
        account.setAccount(resources.getAccount());
        account.setEmail(resources.getEmail());
        account.setEnabled(resources.getEnabled());
        //account.setDeptCode(resources.getDept().getCoder());
        //account.setJobCode(resources.getJob().getCoder());
        account.setPhone(resources.getPhone());
        account.setNickName(resources.getNickName());
        boolean result = this.saveOrUpdate(account);
      /*  accountsRolesService.lambdaUpdate().eq(AccountAccountsRoles::getaccountId, resources.getCoder()).remove();
        AccountsRoles accountsRoles = new AccountsRoles();
        accountsRoles.setAccountCode(resources.getCoder());
        Set<Role> set = resources.getRoles();
        for (Role roleIds : set) {
            accountsRoles.setRoleCode(roleIds.getCoder());
        }
        if (result) {
            accountsRolesService.save(accountsRoles);
        }*/

        // 如果用户的角色改变了，需要手动清理下缓存
       /* if (!resources.getRoles().equals(account.getRoles())) {
            String key = "role::loadPermissionByAccount:" + account.getAccountname();
            redisUtils.del(key);
            key = "role::findByAccounts_Id:" + account.getCoder();
            redisUtils.del(key);
        }*/
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<String> coders) {
      /*  for (String coder : coders) {
            accountsRolesService.lambdaUpdate().eq(AccountsRoles::getAccountId, coder).remove();
        }
        this.removeByCodes(coders);*/
    }

}
