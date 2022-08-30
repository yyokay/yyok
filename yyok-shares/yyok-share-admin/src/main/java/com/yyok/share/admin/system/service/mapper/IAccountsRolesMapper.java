package com.yyok.share.admin.system.service.mapper;

import com.yyok.share.admin.system.domain.AccountRole;
import com.yyok.share.framework.mapper.common.mapper.ICoreMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author yyok
 * @date 2020-05-16
 */
@Repository
@Mapper
public interface IAccountsRolesMapper extends ICoreMapper<AccountRole> {

}
