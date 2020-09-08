/* Copyright (c) 2017-2020 Nozomu Takashima. */
package com.epion_t3.basic.command.runner;

import com.epion_t3.basic.command.model.ConsoleInput;
import com.epion_t3.basic.messages.BasicMessages;
import com.epion_t3.core.command.bean.CommandResult;
import com.epion_t3.core.command.runner.impl.AbstractCommandRunner;
import com.epion_t3.core.exception.SystemException;
import org.slf4j.Logger;

import java.io.*;

/**
 * コンソール入力コマンド実行クラス. ユーザからのコンソール入力を受付、入力された文字列をシナリオスコープの変数に設定する.
 *
 * @author takashno
 */
public class ConsoleInputRunner extends AbstractCommandRunner<ConsoleInput> {

    /**
     * {@inheritDoc}
     */
    @Override
    public CommandResult execute(final ConsoleInput command, final Logger logger) throws Exception {

        Console c = System.console();
        if (c != null) {
            // JDK1.6以降からのコンソールからシステムコンソールが取得できたならば、こちらを利用する
            String s = c.readLine(command.getTarget() + ": ");
            getScenarioScopeVariables().put(command.getValue(), s);
        } else {
            // コンソールが取得できない場合は、従来の方法でコンソールからユーザ入力を取得する
            try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
                System.out.print(command.getTarget() + ": ");
                String s = br.readLine();
                setVariable(command.getValue(), s);
            } catch (Exception e) {
                throw new SystemException(e, BasicMessages.BASIC_ERR_9006);
            }
        }
        return CommandResult.getSuccess();
    }

}
