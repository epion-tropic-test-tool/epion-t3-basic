/* Copyright (c) 2017-2021 Nozomu Takashima. */
package com.epion_t3.basic.command.runner;

import com.epion_t3.core.common.context.Context;
import com.epion_t3.core.common.context.ExecuteContext;
import com.epion_t3.core.custom.parser.impl.CustomParserImpl;
import com.epion_t3.core.scenario.parser.impl.ScenarioParserImpl;
import com.epion_t3.core.scenario.runner.ScenarioRunner;
import com.epion_t3.core.scenario.runner.impl.ScenarioRunnerImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.nio.file.Path;

public class CommandTestBase {

    /** コンテキスト. */
    Context context;

    /** 実行コンテキスト */
    ExecuteContext executeContext;

    /** シナリオ実行処理. */
    ScenarioRunner scenarioRunner;

    /**
     * テストケース毎の初期処理を行います.
     * 
     * @param testInfo テスト情報
     * @param tempDir 仮ディレクトリ
     */
    @BeforeEach
    void beforeEachTest(TestInfo testInfo, @TempDir Path tempDir) {
        context = new Context();
        context.getOption()
                .setRootPath(System.getProperty("user.dir") + File.separator + "src/test/resources/CommandTestBase/"
                        + testInfo.getTestClass().get().getSimpleName() + "/scenario");
        context.getOption().setTarget(testInfo.getTestMethod().get().getName());
        context.getOption().setResultRootPath(tempDir.toAbsolutePath().toString());
        context.getOption().setNoReport(true);
        executeContext = new ExecuteContext();
        executeContext.setResultRootPath(tempDir);
        CustomParserImpl.getInstance().parse(context, executeContext);
        // シナリオの解析（パース処理）
        ScenarioParserImpl.getInstance().parse(context, executeContext);
        // シナリオ実行処理の生成
        scenarioRunner = new ScenarioRunnerImpl();
    }

}
