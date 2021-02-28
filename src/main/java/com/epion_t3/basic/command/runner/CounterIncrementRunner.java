/* Copyright (c) 2017-2021 Nozomu Takashima. */
package com.epion_t3.basic.command.runner;

import com.epion_t3.basic.command.model.ConsoleInput;
import com.epion_t3.basic.command.model.CounterIncrement;
import com.epion_t3.basic.messages.BasicMessages;
import com.epion_t3.core.command.bean.CommandResult;
import com.epion_t3.core.command.runner.impl.AbstractCommandRunner;
import com.epion_t3.core.exception.SystemException;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;

public class CounterIncrementRunner extends AbstractCommandRunner<CounterIncrement> {
    @Override
    public CommandResult execute(CounterIncrement command, Logger logger) throws Exception {
        var counterVariable = resolveVariables(command.getTarget());

        // 数値でなければエラー
        if (counterVariable != null && !NumberUtils.isDigits(counterVariable.toString())) {
            throw new SystemException(BasicMessages.BASIC_ERR_9015);
        }

        // 数値でなければエラー
        if (!NumberUtils.isDigits(command.getValue())) {
            throw new SystemException(BasicMessages.BASIC_ERR_9016);
        }

        int targetValue = 0;

        if (counterVariable != null) {
            targetValue = NumberUtils.toInt(counterVariable.toString());
        }

        targetValue = targetValue + NumberUtils.toInt(command.getValue());

        setVariable(command.getTarget(), String.valueOf(targetValue));

        return CommandResult.getSuccess();
    }
}
