/* Copyright (c) 2017-2021 Nozomu Takashima. */
package com.epion_t3.basic.command.runner;

import org.apache.commons.lang3.time.FastDateFormat;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * {@link AddDateRunner} のテストケース.
 */
public class AddDateRunnerTest extends CommandTestBase {

    /**
     * フォーマッタ.
     */
    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss");
    /**
     * フォーマッタ.
     */
    private static final FastDateFormat FDF = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");

    @Test
    public void test_normal_001() {
        var targetDate = LocalDateTime.of(2020, 01, 01, 12, 34, 56, 0);
        var zdt = targetDate.atZone(ZoneId.systemDefault());
        executeContext.getGlobalVariables().put("target_date", new Date(zdt.toInstant().toEpochMilli()));
        scenarioRunner.execute(context, executeContext);
        var expected = targetDate.plus(1, ChronoUnit.DAYS);
        var actual = (Date) executeContext.getScenarios().get(0).getScenarioVariables().get("added_date");
        Assertions.assertEquals(DTF.format(expected), FDF.format(actual));
    }
}
