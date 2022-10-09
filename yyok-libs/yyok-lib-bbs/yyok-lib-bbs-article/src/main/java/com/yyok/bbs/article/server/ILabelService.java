package com.yyok.bbs.article.server;

import com.github.pagehelper.PageInfo;
import com.yyok.bbs.article.server.dto.LabelDTO;
import com.yyok.bbs.article.server.dto.LabelSearchDTO;

import java.util.List;

public interface ILabelService {
    /**
     * 获取标签
     *
     * @param labelSearchDTO
     * @return
     */
    PageInfo<LabelDTO> getList(LabelSearchDTO labelSearchDTO);

    /**
     * 通过标签id集合获取标签信息
     *
     * @param ids
     * @return
     */
    List<LabelDTO> getByIds(List<Integer> ids);

    /**
     * 新增标签
     *
     * @param labelDTO
     * @param currentAccount
     * @return
     */
    Boolean create(LabelDTO labelDTO, List<String> currentAccount);

    /**
     * 上传标签logo
     *
     * @param bytes
     * @param sourceFileName
     * @return
     */
    String uploadLabelLogo(byte[] bytes, String sourceFileName);

    /**
     * 更新标签
     *
     * @param labelDTO
     * @param currentAccount
     * @return
     */
    Boolean update(LabelDTO labelDTO, List<String> currentAccount);

    /**
     * 删除标签
     *
     * @param id
     * @return
     */
    Boolean delete(Integer id);
}
