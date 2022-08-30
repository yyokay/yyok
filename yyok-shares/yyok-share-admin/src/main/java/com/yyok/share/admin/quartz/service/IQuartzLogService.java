package com.yyok.share.admin.quartz.service;

import com.yyok.share.admin.quartz.domain.QuartzLog;
import com.yyok.share.admin.quartz.service.dto.QuartzLogDto;
import com.yyok.share.admin.quartz.service.dto.QuartzLogQueryCriteria;
import com.yyok.share.framework.mapper.common.service.IBaseService;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author yyok
 * @date 2020-05-13
 */
public interface IQuartzLogService extends IBaseService<QuartzLog> {

    /**
     * 查询数据分页
     *
     * @param criteria 条件
     * @param pageable 分页参数
     * @return Map<String, Object>
     */
    Map<String, Object> queryAll(QuartzLogQueryCriteria criteria, Pageable pageable);

    /**
     * 查询所有数据不分页
     *
     * @param criteria 条件参数
     * @return List<QuartzLogDto>
     */
    List<QuartzLog> queryAll(QuartzLogQueryCriteria criteria);

    /**
     * 导出数据
     *
     * @param all      待导出的数据
     * @param response /
     * @throws IOException /
     */
    void download(List<QuartzLogDto> all, HttpServletResponse response) throws IOException;
}
