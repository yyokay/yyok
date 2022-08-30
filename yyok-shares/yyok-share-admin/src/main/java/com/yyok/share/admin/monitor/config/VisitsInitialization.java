package com.yyok.share.admin.monitor.config;

import com.yyok.share.admin.monitor.service.IVisitsService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 初始化站点统计
 *
 * @author yyok
 */
@Component
public class VisitsInitialization implements ApplicationRunner {

    private final IVisitsService visitsService;

    public VisitsInitialization(IVisitsService visitsService) {
        this.visitsService = visitsService;
    }

    @Override
    public void run(ApplicationArguments args) {
        visitsService.save();
        System.out.println("初始化站点统计");
    }
}