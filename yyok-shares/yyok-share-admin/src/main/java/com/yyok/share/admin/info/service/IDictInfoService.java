package com.yyok.share.admin.info.service;

import com.yyok.share.admin.info.domain.DictInfo;
import com.yyok.share.admin.info.service.dto.DictInfoDto;
import com.yyok.share.admin.info.service.dto.DictInfoQueryCriteria;
import com.yyok.share.admin.system.domain.Dict;
import com.yyok.share.admin.system.service.dto.DictDto;
import com.yyok.share.admin.system.service.dto.DictQueryCriteria;
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
public interface IDictInfoService extends IBaseService<DictInfo> {

    /**
     * 查询数据分页
     *
     * @param criteria 条件
     * @param pageable 分页参数
     * @return Map<String, Object>
     */
    Map<String, Object> queryAll(DictInfoQueryCriteria criteria, Pageable pageable);

    /**
     * 查询所有数据不分页
     *
     * @param criteria 条件参数
     * @return List<DictInfoDto>
     */
    List<DictInfo> queryAll(DictInfoQueryCriteria criteria);

    /**
     * 导出数据
     *
     * @param all      待导出的数据
     * @param response /
     * @throws IOException /
     */
    void download(List<DictInfoDto> all, HttpServletResponse response) throws IOException;
}
