package com.yyok.share.framework.mapper.common.service;

import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface IBaseService<T> extends IService<T> {

    void removeByCodes(List<String> coders);

    void removeByCode(String coder);

    T findByCode(String coder);

    T findByName(String name);

    boolean updateByCode(T coder);
}
