/* Copyright (c) 2017-2021 Nozomu Takashima. */
package com.epion_t3.basic.command.runner;

import com.epion_t3.basic.command.model.RegistrationFileEvidence;
import com.epion_t3.core.command.bean.CommandResult;
import com.epion_t3.core.command.runner.impl.AbstractCommandRunner;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;

import java.nio.file.Files;
import java.nio.file.Paths;

public class RegistrationFileEvidenceRunner extends AbstractCommandRunner<RegistrationFileEvidence> {
    @Override
    public CommandResult execute(RegistrationFileEvidence command, Logger logger) throws Exception {

        // 登録対象のファイル
        var targetPath = Paths.get(getCommandBelongScenarioDirectory(), command.getTarget());

        // エビデンスパスの取得
        var evidencePath = getEvidencePath(FilenameUtils.getName(targetPath.toString()));

        logger.info(collectLoggingMarker(), "copy from : {}, to : {}", targetPath, evidencePath);

        // コピー
        Files.copy(targetPath, evidencePath);

        // ファイルエビデンス登録
        registrationFileEvidence(evidencePath);

        return CommandResult.getSuccess();
    }
}
