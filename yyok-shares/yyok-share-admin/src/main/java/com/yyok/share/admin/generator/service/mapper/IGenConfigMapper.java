package com.yyok.share.admin.generator.service.mapper;

import com.yyok.share.framework.mapper.common.mapper.ICoreMapper;
import com.yyok.share.admin.generator.domain.GenConfig;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface IGenConfigMapper extends ICoreMapper<GenConfig> {
}
