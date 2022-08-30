package com.yyok.share.admin.info.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageInfo;
import com.yyok.share.admin.info.domain.User;
import com.yyok.share.admin.info.domain.UserAvatar;
import com.yyok.share.admin.info.service.IDeptService;
import com.yyok.share.admin.info.service.IJobService;
import com.yyok.share.admin.info.service.IUserAvatarService;
import com.yyok.share.admin.info.service.IUserService;
import com.yyok.share.admin.info.service.dto.UserDto;
import com.yyok.share.admin.info.service.dto.UserQueryCriteria;
import com.yyok.share.admin.info.service.mapper.IUserMapper;
import com.yyok.share.admin.system.service.mapper.IRoleMapper;
import com.yyok.share.framework.dozer.service.IGenerator;
import com.yyok.share.framework.exception.BadRequestException;
import com.yyok.share.framework.exception.EntityExistException;
import com.yyok.share.framework.mapper.common.service.impl.BaseServiceImpl;
import com.yyok.share.framework.mapper.common.utils.QueryHelpPlus;
import com.yyok.share.framework.utils.*;
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
@Service
//@AllArgsConstructor
//@CacheConfig(cacheNames = "user")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class UserServiceImpl extends BaseServiceImpl<IUserMapper, User> implements IUserService {

    private final IGenerator generator;
    private final IUserMapper userMapper;
    private final IUserAvatarService userAvatarService;
    private final IJobService jobService;
    private final IDeptService deptService;
    private final IRoleMapper roleMapper;
    private final RedisUtils redisUtils;
    @Value("${file.avatar}")
    private String avatar;

    public UserServiceImpl(IGenerator generator, IUserMapper userMapper, IUserAvatarService userAvatarService, IJobService jobService, IDeptService deptService, IRoleMapper roleMapper, RedisUtils redisUtils) {
        this.generator = generator;
        this.userMapper = userMapper;
        this.userAvatarService = userAvatarService;
        this.jobService = jobService;
        this.deptService = deptService;
        this.roleMapper = roleMapper;
        this.redisUtils = redisUtils;
    }

    @Override
    //@Cacheable
    public Map<String, Object> queryAll(UserQueryCriteria criteria, Pageable pageable) {
        getPage(pageable);
        PageInfo<User> page = new PageInfo<>(queryAll(criteria));
        Map<String, Object> map = new LinkedHashMap<>(2);
        map.put("content", generator.convert(page.getList(), UserDto.class));
        map.put("totalElements", page.getTotal());
        return map;
    }


    @Override
    //@Cacheable
    public List<User> queryAll(UserQueryCriteria criteria) {
        List<User> userList = baseMapper.selectList(QueryHelpPlus.getPredicate(User.class, criteria));
        for (User user : userList) {
            user.setJob(jobService.findByCode(user.getJobCode()));
            user.setDept(deptService.findByCode(user.getDeptCode()));
        }
        return userList;
    }


    @Override
    public void download(List<UserDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (UserDto user : all) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("邮箱", user.getEmail());
            map.put("状态：1启用、0禁用", user.getEnabled());
            map.put("部门名称", user.getDeptCode());
            map.put("手机号码", user.getPhone());
            map.put("创建日期", user.getCreateTime());
            map.put("昵称", user.getNickName());
            map.put("性别", user.getSex());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    /**
     * 根据用户名查询
     *
     * @param userName /
     * @return /
     */
    @Override
    public User findByName(String userName) {
        User user = userMapper.findByName(userName);
        //用户所属岗位
        user.setJob(jobService.findByCode(user.getJobCode()));
        //用户所属部门
        user.setDept(deptService.findByCode(user.getDeptCode()));
        return user;
    }

    @Override
    public boolean updateByCode(User coder) {
        return false;
    }

    /**
     * 修改密码
     *
     * @param username        用户名
     * @param encryptPassword 密码
     */
    @Override
    public void updatePass(String username, String encryptPassword) {
        userMapper.updatePass(encryptPassword, DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"), username);
    }

    /**
     * 修改头像
     *
     * @param multipartFile 文件
     */
    @Override
    public void updateAvatar(MultipartFile multipartFile) {
        User user = this.getOne(new LambdaQueryWrapper<User>()
                .eq(User::getPhone, SecurityUtils.getAccountCode()));
        UserAvatar userAvatar = userAvatarService.getOne(new LambdaQueryWrapper<UserAvatar>()
                .eq(UserAvatar::getCoder, user.getAvatarCode()));
        String oldPath = "";
        if (userAvatar != null) {
            oldPath = userAvatar.getPath();
        } else {
            userAvatar = new UserAvatar();
        }
        File file = FileUtil.upload(multipartFile, avatar);
        assert file != null;
        userAvatar.setRealName(file.getName());
        userAvatar.setPath(file.getPath());
        userAvatar.setSize(FileUtil.getSize(multipartFile.getSize()));
        userAvatarService.saveOrUpdate(userAvatar);
        user.setAvatarCode(userAvatar.getCoder());
        this.saveOrUpdate(user);
        if (StringUtils.isNotBlank(oldPath)) {
            FileUtil.del(oldPath);
        }
    }

    /**
     * 修改邮箱
     *
     * @param username 用户名
     * @param email    邮箱
     */
    @Override
    public void updateEmail(String username, String email) {
        userMapper.updateEmail(email, username);
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
    public boolean create(User resources) {
        User userName = this.getOne(new LambdaQueryWrapper<User>()
                .eq(User::getPhone, resources.getPhone()));
        if (userName != null) {
            throw new EntityExistException(User.class, "username", resources.getPhone());
        }
        User userEmail = this.getOne(new LambdaQueryWrapper<User>()
                .eq(User::getEmail, resources.getEmail()));
        if (userEmail != null) {
            throw new EntityExistException(User.class, "email", resources.getEmail());
        }
        resources.setDeptCode(resources.getDept().getCoder());
        resources.setJobCode(resources.getJob().getCoder());
        boolean result = this.save(resources);
       /* UsersRoles usersRoles = new UsersRoles();
        usersRoles.setUserCode(resources.getCoder());
        Set<Role> set = resources.getRoles();
        for (Role roleIds : set) {
            usersRoles.setRoleCode(roleIds.getCoder());
        }
        if (result) {
            usersRolesService.save(usersRoles);
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
    public void update(User resources) {
        User user = this.getOne(new LambdaQueryWrapper<User>()
                .eq(User::getCoder, resources.getCoder()));
        ValidationUtil.isNull(user.getCoder(), "User", "id", resources.getCoder());
        User user1 = this.getOne(new LambdaQueryWrapper<User>()
                .eq(User::getPhone, resources.getPhone()));
        User user2 = this.getOne(new LambdaQueryWrapper<User>()
                .eq(User::getEmail, resources.getEmail()));

        if (user1 != null && !user.getCoder().equals(user1.getCoder())) {
            throw new BadRequestException("当前用户名已存在");
        }

        if (user2 != null && !user.getCoder().equals(user2.getCoder())) {
            throw new EntityExistException(User.class, "email", resources.getEmail());
        }
        user.setPhone(resources.getPhone());
        user.setEmail(resources.getEmail());
        user.setEnabled(resources.getEnabled());
        user.setDeptCode(resources.getDept().getCoder());
        user.setJobCode(resources.getJob().getCoder());
        user.setPhone(resources.getPhone());
        user.setNickName(resources.getNickName());
        user.setSex(resources.getSex());
        boolean result = this.saveOrUpdate(user);
      /*  usersRolesService.lambdaUpdate().eq(UsersRoles::getUserId, resources.getCoder()).remove();
        UsersRoles usersRoles = new UsersRoles();
        usersRoles.setUserCode(resources.getCoder());
        Set<Role> set = resources.getRoles();
        for (Role roleIds : set) {
            usersRoles.setRoleCode(roleIds.getCoder());
        }
        if (result) {
            usersRolesService.save(usersRoles);
        }*/

        // 如果用户的角色改变了，需要手动清理下缓存
       /* if (!resources.getRoles().equals(user.getRoles())) {
            String key = "role::loadPermissionByUser:" + user.getUsername();
            redisUtils.del(key);
            key = "role::findByUsers_Id:" + user.getCoder();
            redisUtils.del(key);
        }*/
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<String> coders) {
      /*  for (String coder : coders) {
            usersRolesService.lambdaUpdate().eq(UsersRoles::getUserId, coder).remove();
        }
        this.removeByCodes(coders);*/
    }

}
