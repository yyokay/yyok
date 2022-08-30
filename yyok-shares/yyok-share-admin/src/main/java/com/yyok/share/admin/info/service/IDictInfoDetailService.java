package com.yyok.share.admin.info.service;

import com.yyok.share.admin.info.domain.DictInfoDetail;
import com.yyok.share.admin.info.service.dto.DictInfoDetailDto;
import com.yyok.share.admin.info.service.dto.DictInfoDetailQueryCriteria;
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
public interface IDictInfoDetailService extends IBaseService<DictInfoDetail> {

    /**
     * 查询数据分页
     *
     * @param criteria 条件
     * @param pageable 分页参数
     * @return Map<String, Object>
     */
    Map<String, Object> queryAll(DictInfoDetailQueryCriteria criteria, Pageable pageable);

    /**
     * 查询所有数据不分页
     *
     * @param criteria 条件参数
     * @return List<DictInfoDetailDto>
     */
    List<DictInfoDetail> queryAll(DictInfoDetailQueryCriteria criteria);

    /**
     * 导出数据
     *
     * @param all      待导出的数据
     * @param response /
     * @throws IOException /
     */
    void download(List<DictInfoDetailDto> all, HttpServletResponse response) throws IOException;
}
