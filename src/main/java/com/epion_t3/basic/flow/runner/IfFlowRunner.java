/* Copyright (c) 2017-2020 Nozomu Takashima. */
package com.epion_t3.basic.flow.runner;

import com.epion_t3.basic.flow.model.BreakFlow;
import com.epion_t3.basic.flow.model.IfFlow;
import com.epion_t3.core.common.bean.ExecuteFlow;
import com.epion_t3.core.common.bean.ExecuteScenario;
import com.epion_t3.core.common.context.Context;
import com.epion_t3.core.common.context.ExecuteContext;
import com.epion_t3.core.common.type.FlowResultStatus;
import com.epion_t3.core.exception.SystemException;
import com.epion_t3.core.flow.bean.FlowResult;
import com.epion_t3.core.flow.runner.impl.AbstractConditionalChildrenExecuteFlowRunner;
import com.epion_t3.core.message.impl.CoreMessages;
import org.slf4j.Logger;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * IF分岐を判断するためのFlowRunner.
 * <p>
 * シナリオに記載された任意のJavaScript式を評価し、 その結果が真である場合のみ、childrenに指定されたFlowを実行する.
 * </p>
 *
 * @author takashno
 */
public class IfFlowRunner extends AbstractConditionalChildrenExecuteFlowRunner<IfFlow> {

    @Override
    protected boolean evaluation(Context context, ExecuteContext executeContext, ExecuteScenario executeScenario, ExecuteFlow executeFlow, IfFlow flow, Logger logger) {

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
                // TODO:Error
                throw new SystemException(CoreMessages.CORE_ERR_0001);
            }
        } catch (ScriptException e) {
            throw new SystemException(e);
        }


    }
}
