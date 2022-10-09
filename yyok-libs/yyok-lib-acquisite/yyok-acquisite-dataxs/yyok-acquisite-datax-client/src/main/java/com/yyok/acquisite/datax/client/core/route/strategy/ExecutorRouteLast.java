package com.yyok.acquisite.datax.client.core.route.strategy;

import com.yyok.acquisite.datax.core.biz.model.ReturnT;
import com.yyok.acquisite.datax.core.biz.model.TriggerParam;
import com.yyok.acquisite.datax.client.core.route.ExecutorRouter;

import java.util.List;

public class ExecutorRouteLast extends ExecutorRouter {

    @Override
    public ReturnT<String> route(TriggerParam triggerParam, List<String> addressList) {
        return new ReturnT<String>(addressList.get(addressList.size()-1));
    }

}
