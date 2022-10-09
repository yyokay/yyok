package com.yyok.acquisite.datax.client.mapper;

import com.yyok.acquisite.datax.client.entity.JobLogReport;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;


@Mapper
public interface JobLogReportMapper {

    int save(JobLogReport xxlJobLogReport);

    int update(JobLogReport xxlJobLogReport);

    List<JobLogReport> queryLogReport(@Param("triggerDayFrom") Date triggerDayFrom,
                                      @Param("triggerDayTo") Date triggerDayTo);

    JobLogReport queryLogReportTotal();

}
