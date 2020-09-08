/* Copyright (c) 2017-2020 Nozomu Takashima. */
package com.epion_t3.basic.command.model;

import com.epion_t3.basic.command.runner.ExecuteLocalCommandRunner;
import com.epion_t3.core.common.annotation.CommandDefinition;
import com.epion_t3.core.common.bean.scenario.Command;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@CommandDefinition(id = "ExecuteLocalCommand", runner = ExecuteLocalCommandRunner.class)
public class ExecuteLocalCommand extends Command {

    private List<String> args;

    private Long timeout = 60000L;

    private List<Integer> successExitCodes;

}
