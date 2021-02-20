/* Copyright (c) 2017-2021 Nozomu Takashima. */
package com.epion_t3.basic.flow.model;

import com.epion_t3.basic.flow.runner.BranchFlowRunner;
import com.epion_t3.basic.flow.runner.IfFlowRunner;
import com.epion_t3.core.common.annotation.FlowDefinition;
import com.epion_t3.core.common.bean.scenario.Flow;
import com.epion_t3.core.common.bean.scenario.HasChildrenFlow;
import lombok.Getter;
import lombok.Setter;
import org.apache.bval.constraints.NotEmpty;

/**
 * IF分岐Flow.
 *
 * @author takashno
 * @since 0.0.4
 */
@Getter
@Setter
@FlowDefinition(id = "If", runner = IfFlowRunner.class)
public class IfFlow extends HasChildrenFlow {

    /**
     * 条件評価式.
     */
    @NotEmpty
    private String condition;
}
