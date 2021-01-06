package com.epion_t3.basic.flow.model;

import com.epion_t3.basic.flow.runner.CounterIterateFlowRunner;
import com.epion_t3.core.common.annotation.FlowDefinition;
import com.epion_t3.core.common.bean.scenario.IterateFlow;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@FlowDefinition(id = "CounterIterate", runner = CounterIterateFlowRunner.class)
public class CounterIterateFlow extends IterateFlow {

    private Integer from;

    private Integer to;
}
