/* Copyright (c) 2017-2021 Nozomu Takashima. */
package com.epion_t3.basic.command.model;

import com.epion_t3.basic.command.runner.ExtractArrayValueRunner;
import com.epion_t3.basic.command.runner.StringSplitRunner;
import com.epion_t3.core.common.annotation.CommandDefinition;
import com.epion_t3.core.common.bean.scenario.Command;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@CommandDefinition(id = "ExtractArrayValue", runner = ExtractArrayValueRunner.class)
public class ExtractArrayValue extends Command {
    private Integer index;
}
