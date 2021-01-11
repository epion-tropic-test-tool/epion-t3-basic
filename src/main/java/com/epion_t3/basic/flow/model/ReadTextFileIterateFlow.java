/* Copyright (c) 2017-2020 Nozomu Takashima. */
package com.epion_t3.basic.flow.model;

import com.epion_t3.basic.flow.runner.ReadFileIterateFlowRunner;
import com.epion_t3.core.common.annotation.FlowDefinition;
import com.epion_t3.core.common.bean.scenario.HasChildrenFlow;
import lombok.Getter;
import lombok.Setter;

/**
 *
 */
@Getter
@Setter
@FlowDefinition(id = "ReadFileIterate", runner = ReadFileIterateFlowRunner.class)
public class ReadTextFileIterateFlow extends HasChildrenFlow {

    /**
     * デフォルトシリアルバージョンUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    private String target;

    /**
     * ファイル種別.
     */
    private String fileType;

    /**
     *
     */
    private String encoding;

    /**
     *
     */
    private String lineSeparator;

    /**
     *
     */
    private String wrapstring;

}
