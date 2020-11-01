/* Copyright (c) 2017-2020 Nozomu Takashima. */
package com.epion_t3.basic.command.model;

import com.epion_t3.basic.command.runner.ExceptionOccurredRunner;
import com.epion_t3.core.common.annotation.CommandDefinition;
import com.epion_t3.core.common.bean.scenario.Command;

@CommandDefinition(id = "ExceptionOccurred", runner = ExceptionOccurredRunner.class)
public class ExceptionOccurred extends Command {
}
