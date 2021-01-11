/* Copyright (c) 2017-2020 Nozomu Takashima. */
package com.epion_t3.basic.flow.runner;

import com.epion_t3.basic.flow.model.ReadTextFileIterateFlow;
import com.epion_t3.core.common.bean.ExecuteFlow;
import com.epion_t3.core.common.bean.ExecuteScenario;
import com.epion_t3.core.common.context.Context;
import com.epion_t3.core.common.context.ExecuteContext;
import com.epion_t3.core.exception.SystemException;
import com.epion_t3.core.flow.runner.impl.AbstractSimpleIterateFlowRunner;
import org.slf4j.Logger;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * ファイルを読み込んで1行毎にループ処理を行う.
 *
 * @author takashno
 */
public class ReadFileIterateFlowRunner
        extends AbstractSimpleIterateFlowRunner<ReadTextFileIterateFlow> {

    @Override
    protected Iterable resolveIterateTarget(Context context, ExecuteContext ExecuteContext, ExecuteScenario executeScenario, ExecuteFlow executeFlow, ReadTextFileIterateFlow flow, Logger logger) {
        Path target = Paths.get(flow.getTarget());
        if (!Files.exists(target)) {
            throw new SystemException("not found target File...");
        }
        List<String> contents = null;
        try {
            contents = Files.readAllLines(target, Charset.forName(flow.getEncoding()));
            return contents;
        } catch (IOException e) {
            // 解析用
            logger.debug("error occurred...", e);
            throw new SystemException(e);
        }
    }
}
