package com.yyok.share.admin.mnt.service.mapper;

import com.yyok.share.admin.mnt.domain.Server;
import com.yyok.share.framework.mapper.common.mapper.ICoreMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface IServerMapper extends ICoreMapper<Server> {

}
