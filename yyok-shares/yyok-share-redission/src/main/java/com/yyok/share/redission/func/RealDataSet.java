package com.yyok.share.redission.func;

import java.util.Set;

/**
 * 获取集合实时数据
 */
@FunctionalInterface
public interface RealDataSet<T> {
    Set<T> get();
}
