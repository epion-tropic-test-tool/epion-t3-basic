/* Copyright (c) 2017-2021 Nozomu Takashima. */
package com.epion_t3.basic.flow.runner;

import com.epion_t3.basic.flow.model.DoWhileFlow;
import com.epion_t3.basic.messages.BasicMessages;
import com.epion_t3.core.common.bean.ExecuteFlow;
import com.epion_t3.core.common.bean.ExecuteScenario;
import com.epion_t3.core.common.context.Context;
import com.epion_t3.core.common.context.ExecuteContext;
import com.epion_t3.core.exception.SystemException;
import com.epion_t3.core.flow.runner.impl.AbstractWhileFlowRunner;
import lombok.NonNull;
import org.slf4j.Logger;

import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * {@link DoWhileFlow}の実行処理.
 */
public class DoWhileFlowRunner extends AbstractWhileFlowRunner<DoWhileFlow> {

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean evaluation(@NonNull Context context, @NonNull ExecuteContext executeContext,
            @NonNull ExecuteScenario executeScenario, @NonNull ExecuteFlow executeFlow, @NonNull DoWhileFlow flow,
            @NonNull Logger logger) {

        var factory = new ScriptEngineManager();
        var engine = factory.getEngineByName("JavaScript");

        engine.put("global", executeContext.getGlobalVariables());
        engine.put("scenario", executeScenario.getScenarioVariables());
        engine.put("flow", executeFlow.getFlowVariables());

        try {
            var scriptResult = engine.eval(flow.getCondition());
            if (scriptResult != null && Boolean.class.isAssignableFrom(scriptResult.getClass())) {
                return (boolean) scriptResult;
            } else {
                throw new SystemException(BasicMessages.BASIC_ERR_9014);
            }
        } catch (ScriptException e) {
            throw new SystemException(e);
        }

    }
}
