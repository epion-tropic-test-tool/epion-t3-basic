/* Copyright (c) 2017-2021 Nozomu Takashima. */
package com.epion_t3.basic.flow.model;

import com.epion_t3.core.common.annotation.FlowDefinition;
import com.epion_t3.core.common.bean.scenario.Flow;
import com.epion_t3.basic.flow.runner.CommandExecuteFlowRunner;
import lombok.Getter;
import lombok.Setter;
import org.apache.bval.constraints.NotEmpty;

/**
 * コマンド実行を行うためのFlow定義.
 *
 * @author takashno
 */
@Getter
@Setter
@FlowDefinition(id = "CommandExecute", runner = CommandExecuteFlowRunner.class)
public class CommandExecuteFlow extends Flow {

    /**
     * デフォルトシリアルバージョンUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 実行対象コマンドの参照ID.
     */
    @NotEmpty
    private String ref;

}
