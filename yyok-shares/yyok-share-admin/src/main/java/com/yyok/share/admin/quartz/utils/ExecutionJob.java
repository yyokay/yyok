package com.yyok.share.admin.quartz.utils;

import com.yyok.share.admin.config.thread.ThreadPoolExecutorUtil;
import com.yyok.share.admin.quartz.domain.QuartzJob;
import com.yyok.share.admin.quartz.domain.QuartzLog;
import com.yyok.share.admin.quartz.service.IQuartzJobService;
import com.yyok.share.admin.quartz.service.IQuartzLogService;
import com.yyok.share.framework.utils.SpringContextHolder;
import com.yyok.share.framework.utils.ThrowableUtil;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 参考人人开源，https://gitee.com/renrenio/renren-security
 *
 * @author /
 * @date 2019-01-07
 */
@Async
public class ExecutionJob extends QuartzJobBean {

    /**
     * 该处仅供参考
     */
    private final static ThreadPoolExecutor EXECUTOR = ThreadPoolExecutorUtil.getPoll();
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    @SuppressWarnings("unchecked")
    protected void executeInternal(JobExecutionContext context) {
        QuartzJob quartzJob = (QuartzJob) context.getMergedJobDataMap().get(QuartzJob.JOB_KEY);
        // 获取spring bean
        IQuartzLogService quartzLogService = SpringContextHolder.getBean(IQuartzLogService.class);
        IQuartzJobService quartzJobService = SpringContextHolder.getBean(IQuartzJobService.class);

        QuartzLog log = new QuartzLog();
        log.setJobName(quartzJob.getJobName());
        log.setBaenName(quartzJob.getBeanName());
        log.setMethodName(quartzJob.getMethodName());
        log.setParams(quartzJob.getParamIn());
        long startTime = System.currentTimeMillis();
        log.setCronExpression(quartzJob.getCronExpression());
        try {
            // 执行任务
            logger.info("任务准备执行，任务名称：{}", quartzJob.getJobName());
            QuartzRunnable task = new QuartzRunnable(quartzJob.getBeanName(), quartzJob.getMethodName(),
                    quartzJob.getParamIn());
            Future<?> future = EXECUTOR.submit(task);
            future.get();
            long times = System.currentTimeMillis() - startTime;
            log.setTime(times);
            // 任务状态
            log.setIsSuccess(true);
            logger.info("任务执行完毕，任务名称：{} 总共耗时：{} 毫秒", quartzJob.getJobName(), times);
        } catch (Exception e) {
            logger.error("任务执行失败，任务名称：{}" + quartzJob.getJobName(), e);
            long times = System.currentTimeMillis() - startTime;
            log.setTime(times);
            // 任务状态 0：成功 1：失败
            log.setIsSuccess(false);
            log.setExceptionDetail(ThrowableUtil.getStackTrace(e));
            quartzJob.setIsPause(false);
            //更新状态
            quartzJobService.updateIsPause(quartzJob);
        } finally {
            quartzLogService.save(log);
        }
    }
}
