package com.yyok.share.admin.monitor.service.mapper;

import com.yyok.share.admin.monitor.domain.Visits;
import com.yyok.share.framework.mapper.common.mapper.ICoreMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface IVisitsMapper extends ICoreMapper<Visits> {
    @Select("select * FROM visits where create_time between #{time1} and #{time2}")
    List<Visits> findAllVisits(@Param("time1") String time1, @Param("time2") String time2);
}
