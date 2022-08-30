package com.yyok.share.kpi.service.mapper;

import com.yyok.share.framework.mapper.common.mapper.ICoreMapper;
import com.yyok.share.kpi.domain.PKI;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface IPKIReMapper extends ICoreMapper<PKI> {
	
	PKI findOneByName(String pkiName);
}
