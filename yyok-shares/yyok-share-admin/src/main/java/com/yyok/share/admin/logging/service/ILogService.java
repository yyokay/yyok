package com.yyok.share.admin.logging.service;

import com.yyok.share.framework.mapper.common.service.IBaseService;
import com.yyok.share.admin.logging.domain.Log;
import com.yyok.share.admin.logging.service.dto.LogQueryCriteria;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author yyok
 * @date 2022-05-24
 */
public interface ILogService extends IBaseService<Log> {


    /**
     * 分页查询日志
     *
     * @param criteria 标准
     * @param pageable 可分页
     * @return {@link Object}
     */
    Object findAllByPageable(LogQueryCriteria criteria, Pageable pageable);

    /**
     * 分页查询
     *
     * @param criteria 查询条件
     * @param pageable 分页参数
     * @return /
     */
    Object queryAll(LogQueryCriteria criteria, Pageable pageable);

    /**
     * 查询全部数据
     *
     * @param criteria 查询条件
     * @return /
     */
    List<Log> queryAll(LogQueryCriteria criteria);

    /**
     * 查询用户日志
     *
     * @param criteria 查询条件
     * @param pageable 分页参数
     * @return -
     */
    Object queryAllByUser(LogQueryCriteria criteria, Pageable pageable);

    /**
     * 保存日志数据
     *
     * @param account     用户
     * @param ip          请求IP
     * @param joinPoint   /
     * @param log         日志实体
     * @param accountCode accountCode
     */
    @Async
    void save(String account, String ip, ProceedingJoinPoint joinPoint, Log log, String accountCode);

    /**
     * 查询异常详情
     *
     * @param coder 日志ID
     * @return Object
     */
    Object findByErrDetail(String coder);

    /**
     * 导出日志
     *
     * @param logs     待导出的数据
     * @param response /
     * @throws IOException /
     */
    void download(List<Log> logs, HttpServletResponse response) throws IOException;

    /**
     * 删除所有错误日志
     */
    void delAllByError();

    /**
     * 删除所有INFO日志
     */
    void delAllByInfo();
}
