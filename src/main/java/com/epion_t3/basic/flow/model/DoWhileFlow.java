/* Copyright (c) 2017-2021 Nozomu Takashima. */
package com.epion_t3.basic.flow.model;

import com.epion_t3.basic.flow.runner.DoWhileFlowRunner;
import com.epion_t3.core.common.annotation.FlowDefinition;
import com.epion_t3.core.common.bean.scenario.AbstractWhileFlow;
import lombok.Getter;
import lombok.Setter;
import org.apache.bval.constraints.NotEmpty;

/**
 * DoWhile制御を行うFlow.
 *
 * @author Nozomu.Takashima
 * @since 0.0.4
 */
@Getter
@Setter
@FlowDefinition(id = "DoWhile", runner = DoWhileFlowRunner.class)
public class DoWhileFlow extends AbstractWhileFlow {

    /**
     * 繰り返し処理判定条件式.
     */
    @NotEmpty
    private String condition;

}
