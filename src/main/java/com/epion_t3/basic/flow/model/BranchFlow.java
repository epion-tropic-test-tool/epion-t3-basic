/* Copyright (c) 2017-2020 Nozomu Takashima. */
package com.epion_t3.basic.flow.model;

import com.epion_t3.core.common.annotation.FlowDefinition;
import com.epion_t3.core.common.bean.scenario.Flow;
import com.epion_t3.basic.flow.runner.BranchFlowRunner;
import lombok.Getter;
import lombok.Setter;
import org.apache.bval.constraints.NotEmpty;

@Getter
@Setter
@FlowDefinition(id = "Branch", runner = BranchFlowRunner.class)
public class BranchFlow extends Flow {

    @NotEmpty
    private String condition;

    @NotEmpty
    private String trueRef;

    @NotEmpty
    private String falseRef;

}
