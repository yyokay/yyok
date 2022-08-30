package com.yyok.share.kpi.service.mapper;


import com.yyok.share.framework.mapper.common.mapper.ICoreMapper;
import com.yyok.share.kpi.domain.CAIdentityContainer;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface ICAIdentityContainerMapper extends ICoreMapper<CAIdentityContainer> {

}
