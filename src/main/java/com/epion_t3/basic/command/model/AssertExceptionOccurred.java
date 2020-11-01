/* Copyright (c) 2017-2020 Nozomu Takashima. */
package com.epion_t3.basic.command.model;

import com.epion_t3.basic.command.runner.AssertExceptionOccurredRunner;
import com.epion_t3.core.common.annotation.CommandDefinition;
import com.epion_t3.core.common.bean.scenario.Command;

@CommandDefinition(id = "AssertExceptionOccurred", runner = AssertExceptionOccurredRunner.class, assertCommand = true)
public class AssertExceptionOccurred extends Command {
}
