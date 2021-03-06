/* Copyright (c) 2017-2021 Nozomu Takashima. */
package com.epion_t3.basic.command.runner;

import com.epion_t3.basic.command.model.ExtractArrayValue;
import com.epion_t3.basic.messages.BasicMessages;
import com.epion_t3.core.command.bean.CommandResult;
import com.epion_t3.core.command.runner.impl.AbstractCommandRunner;
import com.epion_t3.core.exception.SystemException;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;

import java.util.Arrays;
import java.util.Collections;

/**
 * 変数に設定されている配列から指定インデックスの値を取得し、変数に設定します。
 *
 * @author takashno
 */
public class ExtractArrayValueRunner extends AbstractCommandRunner<ExtractArrayValue> {

    /**
     * {@inheritDoc}
     */
    @Override
    public CommandResult execute(final ExtractArrayValue command, final Logger logger) throws Exception {

        var targetValue = resolveVariables(command.getTarget());

        if (targetValue == null) {
            throw new SystemException(BasicMessages.BASIC_ERR_9017, command.getTarget());
        } else if (!targetValue.getClass().isArray()) {
            throw new SystemException(BasicMessages.BASIC_ERR_9019, command.getTarget(),
                    targetValue.getClass().getName());
        }

        try {
            var stringArray = ArrayUtils.toStringArray((Object[]) targetValue);
            var extractValue = stringArray[command.getIndex()];
            logger.info(collectLoggingMarker(), "extract values : {}", extractValue);
            setVariable(command.getValue(), extractValue);
        } catch (IndexOutOfBoundsException e) {
            throw new SystemException(e, BasicMessages.BASIC_ERR_9020, command.getIndex());
        }
        return CommandResult.getSuccess();
    }

}
