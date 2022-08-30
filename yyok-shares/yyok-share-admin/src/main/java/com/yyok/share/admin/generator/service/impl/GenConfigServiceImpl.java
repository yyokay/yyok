package com.yyok.share.admin.generator.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yyok.share.admin.generator.service.mapper.IGenConfigMapper;
import com.yyok.share.framework.mapper.common.service.impl.BaseServiceImpl;
import com.yyok.share.framework.utils.StringUtils;
import com.yyok.share.admin.generator.domain.GenConfig;
import com.yyok.share.admin.generator.service.IGenConfigService;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * @author yyok
 * @date 2019-01-14
 */
@Service
//@CacheConfig(cacheNames = "genConfig")
public class GenConfigServiceImpl extends BaseServiceImpl<IGenConfigMapper, GenConfig> implements IGenConfigService {

    @Override
//    @Cacheable(key = "#p0")
    public GenConfig find(String tableName) {
        GenConfig genConfig = this.getOne(new LambdaQueryWrapper<GenConfig>().eq(GenConfig::getTableName, tableName));
        if (genConfig == null) {
            return new GenConfig(tableName);
        }
        return genConfig;
    }

    @Override
//    @CachePut(key = "#p0")
    public GenConfig update(String tableName, GenConfig genConfig) {
        // 如果 api 路径为空，则自动生成路径
        if (StringUtils.isBlank(genConfig.getApiPath())) {
            String separator = File.separator;
            String[] paths;
            String symbol = "\\";
            if (symbol.equals(separator)) {
                paths = genConfig.getPath().split("\\\\");
            } else {
                paths = genConfig.getPath().split(File.separator);
            }
            StringBuilder api = new StringBuilder();
            for (String path : paths) {
                api.append(path);
                api.append(separator);
                if ("src".equals(path)) {
                    api.append("api");
                    break;
                }
            }
            genConfig.setApiPath(api.toString());
        }
        this.saveOrUpdate(genConfig);
        return genConfig;
    }

    @Override
    public GenConfig findByName(String name) {
        GenConfig genConfig = this.getOne(new LambdaQueryWrapper<GenConfig>().eq(GenConfig::getTableName, name));
        if (genConfig == null) {
            return new GenConfig(name);
        }
        return genConfig;
    }

    @Override
    public boolean updateByCode(GenConfig coder) {
        return false;
    }
}
