package com.yyok.acquisite.datax.core.handler.impl;

import com.yyok.acquisite.datax.core.biz.model.ReturnT;
import com.yyok.acquisite.datax.core.biz.model.TriggerParam;
import com.yyok.acquisite.datax.core.handler.IJobHandler;
import com.yyok.acquisite.datax.core.log.JobLogger;

public class GlueJobHandler extends IJobHandler {

	private long glueUpdatetime;
	private IJobHandler jobHandler;
	public GlueJobHandler(IJobHandler jobHandler, long glueUpdatetime) {
		this.jobHandler = jobHandler;
		this.glueUpdatetime = glueUpdatetime;
	}
	public long getGlueUpdatetime() {
		return glueUpdatetime;
	}

	@Override
	public ReturnT<String> execute(TriggerParam tgParam) throws Exception {
		JobLogger.log("----------- glue.version:"+ glueUpdatetime +" -----------");
		return jobHandler.execute(tgParam);
	}
}
