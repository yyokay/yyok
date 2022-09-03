package com.yyok.share.admin.quartz.service.mapper;

import com.yyok.share.admin.quartz.domain.QuartzJob;
import com.yyok.share.framework.mapper.common.mapper.ICoreMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author yyok
 * @date 2020-05-13
 */
@Repository
@Mapper
public interface IQuartzJobMapper extends ICoreMapper<QuartzJob> {

}
