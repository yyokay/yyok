package com.yyok.share.admin.logging.service.mapper;

import com.yyok.share.admin.logging.domain.Log;
import com.yyok.share.framework.mapper.common.mapper.ICoreMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author yyok
 * @date 2019-5-22
 */
@Repository
@Mapper
public interface ILogMapper extends ICoreMapper<Log> {

    @Delete("delete from sys_log where typer = #{typer}")
    void deleteByLogType(@Param("typer") String typer);

    @Select("<script>select l.coder,l.create_time as createTime,l.remark, l.request_ip as requestIp,l.address,u.nickname from sys_log l  " +
            " left join sys_account u on u.coder=l.account_code where l.typer=1 " +
            " <if test = \"nickname !=null\"> and u.nickname LIKE CONCAT('%',#{nickname},'%')</if> order by l.coder desc</script>")
    List<Log> findAllByPageable(@Param("nickname") String nickname);

    @Select("select count(*) FROM (select request_ip FROM sys_log where create_time between #{date1} and #{date2} GROUP BY request_ip) as s")
    long findIp(@Param("date1") String date1, @Param("date2") String date2);
}
