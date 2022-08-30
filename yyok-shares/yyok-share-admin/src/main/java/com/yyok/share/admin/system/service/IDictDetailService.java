package com.yyok.share.admin.system.service;

import com.yyok.share.admin.system.domain.DictDetail;
import com.yyok.share.admin.system.service.dto.DictDetailDto;
import com.yyok.share.admin.system.service.dto.DictDetailQueryCriteria;
import com.yyok.share.framework.mapper.common.service.IBaseService;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author yyok
 * @date 2020-05-14
 */
public interface IDictDetailService extends IBaseService<DictDetail> {

    /**
     * 查询数据分页
     *
     * @param criteria 条件
     * @param pageable 分页参数
     * @return Map<String, Object>
     */
    Map<String, Object> queryAll(DictDetailQueryCriteria criteria, Pageable pageable);

    /**
     * 查询所有数据不分页
     *
     * @param criteria 条件参数
     * @return List<DictDetailDto>
     */
    List<DictDetail> queryAll(DictDetailQueryCriteria criteria);

    /**
     * 导出数据
     *
     * @param all      待导出的数据
     * @param response /
     * @throws IOException /
     */
    void download(List<DictDetailDto> all, HttpServletResponse response) throws IOException;
}
