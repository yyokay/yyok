package com.yyok.acquisite.datax.client.service;

import com.yyok.acquisite.datax.client.dto.DataXJsonBuildDto;

/**
 * com.yyok.acquisite.datax json构建服务层接口
 *
 */
public interface IDataxJsonService {

    /**
     * build datax json
     *
     * @param dto
     * @return
     */
    String buildJobJson(DataXJsonBuildDto dto);
}
