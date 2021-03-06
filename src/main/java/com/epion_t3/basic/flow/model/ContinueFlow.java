/* Copyright (c) 2017-2021 Nozomu Takashima. */
package com.epion_t3.basic.flow.model;

import com.epion_t3.basic.flow.runner.ContinueFlowRunner;
import com.epion_t3.core.common.annotation.FlowDefinition;
import com.epion_t3.core.common.bean.scenario.IterateTypeControlFlow;
import lombok.Getter;
import lombok.Setter;
import org.apache.bval.constraints.NotEmpty;

/**
 * 繰り返し処理中のContinueを行うための制御Flow.
 *
 * @since 0.0.4
 * @author Nozomu.Takashima
 */
@Getter
@Setter
@FlowDefinition(id = "Continue", runner = ContinueFlowRunner.class)
public class ContinueFlow extends IterateTypeControlFlow {

    /**
     * 繰り返し処理判定条件式.
     */
    @NotEmpty
    private String condition;
}
