package com.yyok.acquisite.datax.client.core.route;

import com.yyok.acquisite.datax.core.biz.model.ReturnT;
import com.yyok.acquisite.datax.core.biz.model.TriggerParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public abstract class ExecutorRouter {
    protected static Logger logger = LoggerFactory.getLogger(ExecutorRouter.class);

    public abstract ReturnT<String> route(TriggerParam triggerParam, List<String> addressList);

}
