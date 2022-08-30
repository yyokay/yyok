package com.yyok.share.redission.func;

import java.util.Map;

/**
 * 获取集合实时数据
 */
@FunctionalInterface
public interface RealDataMap<K, V> {
    Map<K, V> get();
}
