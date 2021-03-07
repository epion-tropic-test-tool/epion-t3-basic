/* Copyright (c) 2017-2021 Nozomu Takashima. */
package com.epion_t3.basic.command.model;

import com.epion_t3.basic.command.runner.RegistrationFileEvidenceRunner;
import com.epion_t3.core.common.annotation.CommandDefinition;
import com.epion_t3.core.common.bean.scenario.Command;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@CommandDefinition(id = "RegistrationFileEvidence", runner = RegistrationFileEvidenceRunner.class)
public class RegistrationFileEvidence extends Command {
}
