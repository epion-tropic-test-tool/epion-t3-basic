/* Copyright (c) 2017-2021 Nozomu Takashima. */
package com.epion_t3.basic.command.runner;

import com.epion_t3.basic.command.model.StringSplit;
import com.epion_t3.basic.messages.BasicMessages;
import com.epion_t3.core.command.bean.CommandResult;
import com.epion_t3.core.command.runner.impl.AbstractCommandRunner;
import com.epion_t3.core.exception.SystemException;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;

/**
 * 変数に設定されている文字列を指定された文字列で分割後、変数格納します。
 *
 * @author takashno
 */
public class StringSplitRunner extends AbstractCommandRunner<StringSplit> {

    /**
     * {@inheritDoc}
     */
    @Override
    public CommandResult execute(final StringSplit command, final Logger logger) throws Exception {

        var targetValue = resolveVariables(command.getTarget());

        if (targetValue == null) {
            throw new SystemException(BasicMessages.BASIC_ERR_9017, command.getTarget());
        } else if (!String.class.isAssignableFrom(targetValue.getClass())) {
            throw new SystemException(BasicMessages.BASIC_ERR_9018, command.getTarget());
        }
        var splitValue = ((String) targetValue).split(command.getValue());
        logger.info(collectLoggingMarker(), "split values : {}", ToStringBuilder.reflectionToString(splitValue));
        setVariable(command.getTarget() + "_split", splitValue);
        return CommandResult.getSuccess();
    }

}
