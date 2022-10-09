package com.yyok.bbs.article.server;

import com.github.pagehelper.PageInfo;
import com.yyok.bbs.article.server.dto.ResourceNavigateDTO;
import com.yyok.bbs.article.server.dto.ResourceNavigateSearchDTO;

import java.util.List;

public interface IResourceNavigateService {
    /**
     * 获取资源导航
     *
     * @param resourceNavigateSearchDTO
     * @return
     */
    PageInfo<ResourceNavigateDTO> getList(ResourceNavigateSearchDTO resourceNavigateSearchDTO);

    /**
     * 新增资源导航
     *
     * @param resourceNavigateDTO
     * @param currentAccount
     * @return
     */
    Boolean create(ResourceNavigateDTO resourceNavigateDTO, String currentAccount);

    /**
     * 上传资源导航logo
     *
     * @param bytes
     * @param sourceFileName
     * @return
     */
    String uploadResourceNavigateLogo(byte[] bytes, String sourceFileName);

    /**
     * 更新资源导航
     *
     * @param resourceNavigateDTO
     * @param currentAccount
     * @return
     */
    Boolean update(ResourceNavigateDTO resourceNavigateDTO, String currentAccount);

    /**
     * 删除资源导航
     *
     * @param id
     * @return
     */
    Boolean delete(Integer id);

    /**
     * 获取资源导航所有类别
     *
     * @return
     */
    List<String> getCategorys();
}
