package com.yyok.share.admin.system.service;

import com.yyok.share.admin.system.domain.Account;
import com.yyok.share.admin.system.service.dto.AccountDto;
import com.yyok.share.admin.system.service.dto.AccountQueryCriteria;
import com.yyok.share.framework.mapper.common.service.IBaseService;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IAccountService extends IBaseService<Account> {
    /**
     * 查询数据分页
     *
     * @param criteria 条件
     * @param pageable 分页参数
     * @return Map<String, Object>
     */
    Map<String, Object> queryAll(AccountQueryCriteria criteria, Pageable pageable);

    /**
     * 查询所有数据不分页
     *
     * @param criteria 条件参数
     * @return List<AccountDto>
     */
    List<Account> queryAll(AccountQueryCriteria criteria);

    /**
     * 导出数据
     *
     * @param all      待导出的数据
     * @param response /
     * @throws IOException /
     */
    void download(List<AccountDto> all, HttpServletResponse response) throws IOException;


    /**
     * 修改密码
     *
     * @param username        用户名
     * @param encryptPassword 密码
     */
    void updatePass(String username, String encryptPassword);

    /**
     * 修改头像
     *
     * @param multipartFile 文件
     */
    void updateAvatar(MultipartFile multipartFile);

    /**
     * 修改邮箱
     *
     * @param username 用户名
     * @param email    邮箱
     */
    void updateEmail(String username, String email);

    /**
     * 新增用户
     *
     * @param resources /
     * @return /
     */
    boolean create(Account resources);

    /**
     * 编辑用户
     *
     * @param resources /
     */
    void update(Account resources);

    void delete(Set<String> coders);
}
