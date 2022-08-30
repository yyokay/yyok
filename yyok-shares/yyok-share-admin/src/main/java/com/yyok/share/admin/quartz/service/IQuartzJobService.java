package com.yyok.share.admin.quartz.service;

import com.yyok.share.admin.quartz.domain.QuartzJob;
import com.yyok.share.admin.quartz.service.dto.QuartzJobDto;
import com.yyok.share.admin.quartz.service.dto.QuartzJobQueryCriteria;
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
public interface IQuartzJobService extends IBaseService<QuartzJob> {

    /**
     * 查询数据分页
     *
     * @param criteria 条件
     * @param pageable 分页参数
     * @return Map<String, Object>
     */
    Map<String, Object> queryAll(QuartzJobQueryCriteria criteria, Pageable pageable);

    /**
     * 查询所有数据不分页
     *
     * @param criteria 条件参数
     * @return List<QuartzJobDto>
     */
    List<QuartzJob> queryAll(QuartzJobQueryCriteria criteria);

    /**
     * 导出数据
     *
     * @param all      待导出的数据
     * @param response /
     * @throws IOException /
     */
    void download(List<QuartzJobDto> all, HttpServletResponse response) throws IOException;


    /**
     * 更改定时任务状态
     *
     * @param quartzJob /
     */
    void updateIsPause(QuartzJob quartzJob);

    /**
     * 立即执行定时任务
     *
     * @param quartzJob /
     */
    void execution(QuartzJob quartzJob);

    /**
     * 查询启用的任务
     *
     * @return List
     */
    List<QuartzJob> findByIsPauseIsFalse();

    void removeByCodes(List<String> idList);
}
