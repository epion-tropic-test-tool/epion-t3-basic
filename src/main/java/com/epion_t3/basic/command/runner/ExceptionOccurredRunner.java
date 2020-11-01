/* Copyright (c) 2017-2020 Nozomu Takashima. */
package com.epion_t3.basic.command.runner;

import com.epion_t3.basic.command.model.ExceptionOccurred;
import com.epion_t3.core.command.bean.CommandResult;
import com.epion_t3.core.command.runner.impl.AbstractCommandRunner;
import org.slf4j.Logger;

public class ExceptionOccurredRunner extends AbstractCommandRunner<ExceptionOccurred> {
    @Override
    public CommandResult execute(ExceptionOccurred command, Logger logger) throws Exception {
        throw new Exception("Exception occurred...");
    }
}
