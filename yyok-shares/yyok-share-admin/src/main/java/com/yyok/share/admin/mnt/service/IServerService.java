package com.yyok.share.admin.mnt.service;

import com.yyok.share.admin.mnt.domain.Server;
import com.yyok.share.admin.mnt.service.dto.ServerDto;
import com.yyok.share.admin.mnt.service.dto.ServerQueryCriteria;
import com.yyok.share.framework.mapper.common.service.IBaseService;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface IServerService extends IBaseService<Server> {

    /**
     * 分页查询
     * @param criteria 条件
     * @param pageable 分页参数
     * @return /
     */
    Object queryAll(ServerQueryCriteria criteria, Pageable pageable);

    /**
     * 查询全部数据
     * @param criteria 条件
     * @return /
     */
    List<ServerDto> queryAll(ServerQueryCriteria criteria);

    /**
     * 根据ID查询
     * @param id /
     * @return /
     */
    ServerDto findById(Long id);

    /**
     * 创建
     * @param resources /
     */
    void create(Server resources);

    /**
     * 编辑
     * @param resources /
     */
    void update(Server resources);

    /**
     * 删除
     * @param ids /
     */
    void delete(Set<Long> ids);

    /**
     * 导出数据
     * @param queryAll /
     * @param response /
     * @throws IOException /
     */
    void download(List<Server> queryAll, HttpServletResponse response) throws IOException;
}
