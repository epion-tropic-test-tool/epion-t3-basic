package com.epion_t3.basic.command.runner;

import com.epion_t3.basic.command.model.AssertExistsStringInText;
import com.epion_t3.basic.messages.BasicMessages;
import com.epion_t3.core.command.bean.AssertCommandResult;
import com.epion_t3.core.command.bean.CommandResult;
import com.epion_t3.core.command.runner.impl.AbstractCommandRunner;
import com.epion_t3.core.exception.SystemException;
import com.epion_t3.core.message.MessageManager;
import com.epion_t3.core.common.type.AssertStatus;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AssertExistsStringInTextRunner extends AbstractCommandRunner<AssertExistsStringInText> {

    @Override
    public CommandResult execute(
            AssertExistsStringInText command,
            Logger logger) throws Exception {

        AssertCommandResult commandResult = AssertCommandResult.getSuccess();
        commandResult.setExpected("指定テキストファイルに、指定した文字列が含まれている");

        Path targetFile = referFileEvidence(command.getTarget());

        Pattern p = null;
        if (command.getRegexp()) {
            p = Pattern.compile(command.getValue());
        }

        Integer assertNum = 0;

        boolean existsFlg = false;
        List<String> lineList = null;
        try {
            lineList = FileUtils.readLines(targetFile.toFile(), command.getEncoding());
            for (String line : lineList) {
                if (p != null) {
                    Matcher m = p.matcher(line);
                    if (m.find()) {
                        existsFlg = true;
                        assertNum++;
                        if (command.getNum() == null) {
                            break;
                        }
                    }
                } else {
                    if (line.contains(command.getValue())) {
                        existsFlg = true;
                        assertNum++;
                        if (command.getNum() == null) {
                            break;
                        }
                    }
                }
            }

            if (existsFlg) {
                if (command.getNum() == null) {
                    commandResult.setMessage(MessageManager.getInstance().getMessage(
                            BasicMessages.BASIC_INF_0001, command.getValue()));
                    commandResult.setAssertStatus(AssertStatus.OK);
                    commandResult.setActual("指定テキストファイルに、指定した文字列が含まれている");
                } else {
                    if (assertNum == command.getNum()) {
                        commandResult.setMessage(MessageManager.getInstance().getMessage(
                                BasicMessages.BASIC_INF_0001, command.getValue()));
                        commandResult.setAssertStatus(AssertStatus.OK);
                        commandResult.setActual(
                                String.format("指定テキストファイルに、指定した文字列が%s回含まれている", command.getNum()));
                    } else {
                        commandResult.setMessage(MessageManager.getInstance().getMessage(
                                BasicMessages.BASIC_ERR_9012, command.getNum(), command.getValue()));
                        commandResult.setAssertStatus(AssertStatus.OK);
                        commandResult.setActual(
                                String.format("指定テキストファイルに、指定した文字列が%s回含まれていることを期待したが、実際は%s回であった", command.getNum(), assertNum));
                    }
                }
            } else {
                commandResult.setMessage(MessageManager.getInstance().getMessage(
                        BasicMessages.BASIC_ERR_9002, command.getValue()));
                commandResult.setAssertStatus(AssertStatus.NG);
                commandResult.setActual("指定テキストファイルに、指定した文字列が含まれていない");
            }


        } catch (IOException e) {
            throw new SystemException(BasicMessages.BASIC_ERR_9008, targetFile.toString());
        }
        return commandResult;
    }
}