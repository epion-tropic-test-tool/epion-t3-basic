/* Copyright (c) 2017-2021 Nozomu Takashima. */
package com.epion_t3.basic.flow.runner;

import com.epion_t3.basic.flow.model.CounterIterateFlow;
import com.epion_t3.core.common.bean.ExecuteFlow;
import com.epion_t3.core.common.bean.ExecuteScenario;
import com.epion_t3.core.common.context.Context;
import com.epion_t3.core.common.context.ExecuteContext;
import com.epion_t3.core.flow.runner.impl.AbstractSimpleIterateFlowRunner;
import org.slf4j.Logger;

import java.util.ArrayList;

public class CounterIterateFlowRunner extends AbstractSimpleIterateFlowRunner<CounterIterateFlow> {

    @Override
    protected Iterable resolveIterateTarget(Context context, ExecuteContext executeContext,
            ExecuteScenario executeScenario, ExecuteFlow executeFlow, CounterIterateFlow flow, Logger logger) {
        logger.info(collectLoggingMarker(), "from : {} -> to : {}", flow.getFrom(), flow.getTo());
        var result = new ArrayList<>(flow.getTo() - flow.getFrom());
        for (int i = flow.getFrom(); i < flow.getTo(); i++) {
            result.add(i);
        }
        return result;
    }

}
