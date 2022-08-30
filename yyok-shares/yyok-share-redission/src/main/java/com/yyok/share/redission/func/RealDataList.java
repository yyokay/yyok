package com.yyok.share.redission.func;

import java.util.List;

/**
 * 获取集合实时数据
 */
@FunctionalInterface
public interface RealDataList<T> {
    List<T> get();
}
