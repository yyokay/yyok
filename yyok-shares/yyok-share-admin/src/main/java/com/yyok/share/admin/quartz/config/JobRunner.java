package com.yyok.share.admin.quartz.config;

import com.yyok.share.admin.quartz.domain.QuartzJob;
import com.yyok.share.admin.quartz.service.IQuartzJobService;
import com.yyok.share.admin.quartz.utils.QuartzManage;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author yyok
 * @date 2019-01-07
 */
@Component
public class JobRunner implements ApplicationRunner {

    private final IQuartzJobService quartzJobServiceJobRunner;

    private final QuartzManage quartzManage;

    public JobRunner(IQuartzJobService quartzJobServiceJobRunner, QuartzManage quartzManage) {
        this.quartzJobServiceJobRunner = quartzJobServiceJobRunner;
        this.quartzManage = quartzManage;
    }

    /**
     * 项目启动时重新激活启用的定时任务
     *
     * @param applicationArguments /
     */
    @Override
    public void run(ApplicationArguments applicationArguments) {
        List<QuartzJob> quartzJobs = quartzJobServiceJobRunner.findByIsPauseIsFalse();
        quartzJobs.forEach(quartzManage::addJob);
    }
}
