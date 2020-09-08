/* Copyright (c) 2017-2020 Nozomu Takashima. */
package com.epion_t3.basic.command.runner;

import com.epion_t3.basic.command.model.ExecuteLocalCommand;
import com.epion_t3.basic.messages.BasicMessages;
import com.epion_t3.core.command.bean.CommandResult;
import com.epion_t3.core.command.runner.impl.AbstractCommandRunner;
import com.epion_t3.core.exception.SystemException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.PumpStreamHandler;
import org.slf4j.Logger;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;

/**
 * ローカルコマンド実行処理.
 *
 * @author takashno
 */
public class ExecuteLocalCommandRunner extends AbstractCommandRunner<ExecuteLocalCommand> {

    /**
     * {@inheritDoc}
     */
    @Override
    public CommandResult execute(ExecuteLocalCommand command, Logger logger) throws Exception {

        CommandLine cmdLine = new CommandLine(command.getTarget());
        if (CollectionUtils.isNotEmpty(command.getArgs())) {
            for (String arg : command.getArgs()) {
                cmdLine.addArgument(arg);
            }
        }

        DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();

        DefaultExecutor executor = new DefaultExecutor();

        try {
            Path stdoutPath = getEvidencePath("stdout.log");
            Path errPath = getEvidencePath("err.log");
            PumpStreamHandler streamHandler = new PumpStreamHandler(new FileOutputStream(stdoutPath.toFile()),
                    new FileOutputStream(errPath.toFile()));
            executor.setStreamHandler(streamHandler);

            // 正常として扱う終了コードを指定
            if (CollectionUtils.isEmpty(command.getSuccessExitCodes())) {
                executor.setExitValue(0);
            } else {
                executor.setExitValues(command.getSuccessExitCodes().stream().mapToInt(x -> x.intValue()).toArray());
            }

            ExecuteWatchdog watchdog = new ExecuteWatchdog(command.getTimeout());
            executor.setWatchdog(watchdog);

            // execute
            executor.execute(cmdLine, resultHandler);

            resultHandler.waitFor();

            // 終了コード設定
            logger.debug("exit code -> {}", resultHandler.getExitValue());

            // 例外が発生している場合は、エラーとする
            if (resultHandler.getException() != null) {
                throw new SystemException(resultHandler.getException(), BasicMessages.BASIC_ERR_9012);
            }

        } catch (ExecuteException ee) {
            throw new SystemException(ee, BasicMessages.BASIC_ERR_9013);
        } catch (InterruptedException | IOException e) {
            throw new SystemException(e, BasicMessages.BASIC_ERR_9012);
        }

        return CommandResult.getSuccess();
    }
}
