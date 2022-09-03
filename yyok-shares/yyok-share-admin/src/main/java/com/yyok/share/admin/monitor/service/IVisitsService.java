package com.yyok.share.admin.monitor.service;

import com.yyok.share.admin.monitor.domain.Visits;
import com.yyok.share.framework.mapper.common.service.IBaseService;
import org.springframework.scheduling.annotation.Async;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yyok
 * @date 2022-12-13
 */
public interface IVisitsService extends IBaseService<Visits> {

    /**
     * 提供给定时任务，每天0点执行
     */
    void save(String role);

    /**
     * 新增记录
     *
     * @param request /
     */
    @Async
    void count(HttpServletRequest request);

    /**
     * 获取数据
     *
     * @return /
     */
    Object get();

    /**
     * getChartData
     *
     * @return /
     */
    Object getChartData();
}
