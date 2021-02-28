/* Copyright (c) 2017-2021 Nozomu Takashima. */
package com.epion_t3.basic.flow.runner;

import com.epion_t3.basic.flow.model.BranchFlow;
import com.epion_t3.core.common.bean.ExecuteFlow;
import com.epion_t3.core.common.bean.ExecuteScenario;
import com.epion_t3.core.common.context.Context;
import com.epion_t3.core.common.context.ExecuteContext;
import com.epion_t3.core.exception.SystemException;
import com.epion_t3.core.flow.bean.FlowResult;
import com.epion_t3.core.flow.runner.impl.AbstractSimpleFlowRunner;
import com.epion_t3.core.message.impl.CoreMessages;
import org.slf4j.Logger;

import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * 条件分岐を判断するためのFlowRunner.
 * <p>
 * シナリオに記載された任意のJavaScript式を評価し、 その結果をもとに、どのFlowへ実行するべきかを判定する.
 * </p>
 *
 * @author takashno
 */
public class BranchFlowRunner extends AbstractSimpleFlowRunner<BranchFlow> {

    @Override
    protected FlowResult execute(final Context context, final ExecuteContext executeContext,
            final ExecuteScenario executeScenario, final ExecuteFlow executeFlow, final BranchFlow flow,
            final Logger logger) {

        var factory = new ScriptEngineManager();
        var engine = factory.getEngineByName("JavaScript");

        engine.put("global", executeContext.getGlobalVariables());
        engine.put("scenario", executeScenario.getScenarioVariables());
        engine.put("flow", executeFlow.getFlowVariables());

        try {
            var scriptResult = engine.eval(flow.getCondition());
            if (scriptResult != null && Boolean.class.isAssignableFrom(scriptResult.getClass())) {
                var flowResult = new FlowResult();
//                flowResult.setStatus(FlowStatus.CHOICE);
//                flowResult.setChoiceId((Boolean) scriptResult ? flow.getTrueRef() : flow.getFalseRef());
                return flowResult;
            } else {
                // TODO:Error
                throw new SystemException(CoreMessages.CORE_ERR_0001);
            }
        } catch (ScriptException e) {
            throw new SystemException(e);
        }
    }
}
