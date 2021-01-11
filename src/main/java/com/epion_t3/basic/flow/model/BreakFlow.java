package com.epion_t3.basic.flow.model;

import com.epion_t3.basic.flow.runner.BreakFlowRunner;
import com.epion_t3.core.common.annotation.FlowDefinition;
import com.epion_t3.core.common.bean.scenario.Flow;
import lombok.Getter;
import lombok.Setter;
import org.apache.bval.constraints.NotEmpty;

@Getter
@Setter
@FlowDefinition(id = "Break", runner = BreakFlowRunner.class)
public class BreakFlow extends Flow {

    @NotEmpty
    private String condition;
}
