package com.yyok.share.admin.quartz.task;

import com.yyok.share.admin.monitor.service.IVisitsService;
import org.springframework.stereotype.Component;

/**
 * @author yyok
 * @date 2022-12-25
 */
@Component
public class VisitsTask {

    private final IVisitsService visitsService;

    public VisitsTask(IVisitsService visitsService) {
        this.visitsService = visitsService;
    }

    public void run() {
        visitsService.save();
    }
}
