/* Copyright (c) 2017-2021 Nozomu Takashima. */
package com.epion_t3.basic.flow.runner;

import com.epion_t3.basic.flow.model.BreakFlow;
import com.epion_t3.basic.flow.model.ContinueFlow;
import com.epion_t3.basic.messages.BasicMessages;
import com.epion_t3.core.common.bean.ExecuteFlow;
import com.epion_t3.core.common.bean.ExecuteScenario;
import com.epion_t3.core.common.context.Context;
import com.epion_t3.core.common.context.ExecuteContext;
import com.epion_t3.core.common.type.FlowResultStatus;
import com.epion_t3.core.exception.SystemException;
import com.epion_t3.core.flow.bean.FlowResult;
import com.epion_t3.core.flow.runner.impl.AbstractSimpleFlowRunner;
import org.slf4j.Logger;

import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * {@link ContinueFlow}の実行処理.
 */
public class ContinueFlowRunner extends AbstractSimpleFlowRunner<ContinueFlow> {

    /**
     * {@link BreakFlow}
     */
    @Override
    protected FlowResult execute(final Context context, final ExecuteContext executeContext,
            final ExecuteScenario executeScenario, final ExecuteFlow executeFlow, final ContinueFlow flow,
            final Logger logger) {

        var factory = new ScriptEngineManager();
        var engine = factory.getEngineByName("JavaScript");

        engine.put("global", executeContext.getGlobalVariables());
        engine.put("scenario", executeScenario.getScenarioVariables());
        engine.put("flow", executeFlow.getFlowVariables());

        try {
            var scriptResult = engine.eval(flow.getCondition());
            if (scriptResult != null && Boolean.class.isAssignableFrom(scriptResult.getClass())) {
                var evaluationResult = (Boolean) scriptResult;
                logger.info(collectLoggingMarker(), "condition evaluation result -> {}", evaluationResult);
                var flowResult = FlowResult.getDefault();
                if (evaluationResult) {
                    flowResult.setStatus(FlowResultStatus.CONTINUE);
                }
                return flowResult;
            } else {
                throw new SystemException(BasicMessages.BASIC_ERR_9014);
            }
        } catch (ScriptException e) {
            throw new SystemException(e);
        }
    }
}
