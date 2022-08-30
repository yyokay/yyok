package com.yyok.share.admin.system.service;

import com.yyok.share.admin.system.domain.AccountAvatar;
import com.yyok.share.admin.system.service.dto.AccountAvatarDto;
import com.yyok.share.admin.system.service.dto.AccountAvatarQueryCriteria;
import com.yyok.share.framework.mapper.common.service.IBaseService;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface IAccountAvatarService extends IBaseService<AccountAvatar> {
    /**
     * 查询数据分页
     *
     * @param criteria 条件
     * @param pageable 分页参数
     * @return Map<String, Object>
     */
    Map<String, Object> queryAll(AccountAvatarQueryCriteria criteria, Pageable pageable);

    /**
     * 查询所有数据不分页
     *
     * @param criteria 条件参数
     * @return List<AccountAvatarDto>
     */
    List<AccountAvatar> queryAll(AccountAvatarQueryCriteria criteria);

    /**
     * 导出数据
     *
     * @param all      待导出的数据
     * @param response /
     * @throws IOException /
     */
    void download(List<AccountAvatarDto> all, HttpServletResponse response) throws IOException;

    /**
     * @param accountAvatar
     * @return
     */
    AccountAvatar saveFile(AccountAvatar accountAvatar);
}
