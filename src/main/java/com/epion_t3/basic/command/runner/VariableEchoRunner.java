/* Copyright (c) 2017-2021 Nozomu Takashima. */
package com.epion_t3.basic.command.runner;

import com.epion_t3.basic.command.model.VariableEcho;
import com.epion_t3.core.command.bean.CommandResult;
import com.epion_t3.core.command.runner.impl.AbstractCommandRunner;
import org.slf4j.Logger;

public class VariableEchoRunner extends AbstractCommandRunner<VariableEcho> {
    @Override
    public CommandResult execute(VariableEcho command, Logger logger) throws Exception {
        var variable = resolveVariables(command.getTarget());
        logger.info("Variable Echo.");
        logger.info("Variable : {}", command.getTarget());
        logger.info("Value : {}", variable);
        return CommandResult.getSuccess();
    }
}
