package com.epion_t3.basic.flow.runner;

import com.epion_t3.core.common.bean.ExecuteFlow;
import com.epion_t3.core.common.bean.ExecuteScenario;
import com.epion_t3.core.common.bean.scenario.IterateFlow;
import com.epion_t3.core.common.context.Context;
import com.epion_t3.core.common.context.ExecuteContext;
import com.epion_t3.core.flow.runner.impl.AbstractIterateFlowRunner;
import org.slf4j.Logger;

import java.util.List;

public class CounterIterateFlowRunner extends AbstractIterateFlowRunner {

    @Override
    protected Iterable resolveIterateTarget(Context context, ExecuteContext executeContext, ExecuteScenario executeScenario, ExecuteFlow executeFlow, IterateFlow flow, Logger logger) {
        logger.info(collectLoggingMarker(), "start....");
        return List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    }

}
