/* Copyright (c) 2017-2021 Nozomu Takashima. */
package com.epion_t3.basic.command.runner;

import com.epion_t3.basic.command.model.AssertExceptionOccurred;
import com.epion_t3.core.command.bean.CommandResult;
import com.epion_t3.core.command.runner.impl.AbstractCommandRunner;
import org.slf4j.Logger;

public class AssertExceptionOccurredRunner extends AbstractCommandRunner<AssertExceptionOccurred> {
    @Override
    public CommandResult execute(AssertExceptionOccurred command, Logger logger) throws Exception {
        throw new Exception("Exception occurred...");
    }
}
