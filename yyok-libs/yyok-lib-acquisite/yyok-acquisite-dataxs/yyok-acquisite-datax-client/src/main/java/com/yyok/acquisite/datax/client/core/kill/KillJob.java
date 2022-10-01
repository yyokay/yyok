package com.yyok.acquisite.datax.client.core.kill;

import com.yyok.acquisite.datax.core.biz.model.ReturnT;
import com.yyok.acquisite.datax.core.biz.model.TriggerParam;
import com.yyok.acquisite.datax.core.enums.ExecutorBlockStrategyEnum;
import com.yyok.acquisite.datax.core.glue.GlueTypeEnum;
import com.yyok.acquisite.datax.client.core.trigger.JobTrigger;

import java.util.Date;

public class KillJob {

    /**
     * @param logId
     * @param address
     * @param processId
     */
    public static ReturnT<String> trigger(long logId, Date triggerTime, String address, String processId) {
        ReturnT<String> triggerResult;
        TriggerParam triggerParam = new TriggerParam();
        triggerParam.setJobId(-1);
        triggerParam.setExecutorHandler("killJobHandler");
        triggerParam.setProcessId(processId);
        triggerParam.setLogId(logId);
        triggerParam.setGlueType(GlueTypeEnum.BEAN.getDesc());
        triggerParam.setExecutorBlockStrategy(ExecutorBlockStrategyEnum.SERIAL_EXECUTION.getTitle());
        triggerParam.setLogDateTime(triggerTime.getTime());
        if (address != null) {
            triggerResult = JobTrigger.runExecutor(triggerParam, address);
        } else {
            triggerResult = new ReturnT<>(ReturnT.FAIL_CODE, null);
        }
        return triggerResult;
    }

}
