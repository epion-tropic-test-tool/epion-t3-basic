/* Copyright (c) 2017-2020 Nozomu Takashima. */
package com.epion_t3.basic.flow.model;

import com.epion_t3.basic.flow.runner.CounterIterateFlowRunner;
import com.epion_t3.core.common.annotation.FlowDefinition;
import com.epion_t3.core.common.bean.scenario.HasChildrenFlow;
import lombok.Getter;
import lombok.Setter;

/**
 * 指定されたカウンタに沿って繰り返すためのFlow.
 *
 * @author takashno
 * @since 0.0.4
 */
@Getter
@Setter
@FlowDefinition(id = "CounterIterate", runner = CounterIterateFlowRunner.class)
public class CounterIterateFlow extends HasChildrenFlow {

    /**
     * カウンタ始値.
     */
    private Integer from;

    /**
     * カウンタ終値.
     */
    private Integer to;
}
