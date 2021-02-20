/* Copyright (c) 2017-2021 Nozomu Takashima. */
package com.epion_t3.basic.flow.runner;

import com.epion_t3.basic.flow.model.CommandExecuteFlow;
import com.epion_t3.core.command.bean.AssertCommandResult;
import com.epion_t3.core.command.logging.factory.CommandLoggerFactory;
import com.epion_t3.core.command.logging.holder.CommandLoggingHolder;
import com.epion_t3.core.command.resolver.impl.CommandRunnerResolverImpl;
import com.epion_t3.core.common.bean.ExecuteCommand;
import com.epion_t3.core.common.bean.ExecuteFlow;
import com.epion_t3.core.common.bean.ExecuteScenario;
import com.epion_t3.core.common.bean.scenario.Command;
import com.epion_t3.core.common.context.Context;
import com.epion_t3.core.common.context.ExecuteContext;
import com.epion_t3.core.common.type.CommandStatus;
import com.epion_t3.core.common.type.FlowScopeVariables;
import com.epion_t3.core.common.type.FlowStatus;
import com.epion_t3.core.common.util.BindUtils;
import com.epion_t3.core.common.util.ErrorUtils;
import com.epion_t3.core.common.util.IDUtils;
import com.epion_t3.core.exception.CommandNotFoundException;
import com.epion_t3.core.exception.SystemException;
import com.epion_t3.core.flow.bean.FlowResult;
import com.epion_t3.core.flow.runner.impl.AbstractSimpleFlowRunner;
import com.epion_t3.core.message.impl.CoreMessages;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * コマンド実行のフロー実行処理の基底クラス.
 *
 * @author takashno
 */
@Slf4j
public class CommandExecuteFlowRunner extends AbstractSimpleFlowRunner<CommandExecuteFlow> {

    /**
     * {@inheritDoc}
     */
    @Override
    protected FlowResult execute(Context context, ExecuteContext executeContext, ExecuteScenario executeScenario,
            ExecuteFlow executeFlow, CommandExecuteFlow flow, Logger logger) {

        // コマンド識別子
        String fqcn = flow.getRef();

        // FQCNであるか判断する
        if (!IDUtils.getInstance().isFullQueryCommandName(flow.getRef())) {
            // FQCNでなければ構築する
            fqcn = IDUtils.getInstance().createFullCommandId(executeScenario.getFqsn(), fqcn);
        }

        // コマンド参照
        Command command = context.getOriginal().getCommands().get(fqcn);

        if (command == null) {
            log.error("not found command: {}", flow.getRef());
            throw new CommandNotFoundException(flow.getRef());
        }

        // コマンドは使い回されるため、bindなどを1度行うと再利用ができなくなるためクローンして利用する
        var cloneCommand = SerializationUtils.clone(command);

        // コマンド実行情報を生成
        var executeCommand = new ExecuteCommand();
        executeFlow.getCommands().add(executeCommand);
        executeCommand.setCommand(cloneCommand);
        executeCommand.setFqcn(fqcn);

        // Logger生成
        var commandLogger = CommandLoggerFactory.getInstance().getCommandLogger(this.getClass());

        // シナリオ実行開始時間を設定
        executeCommand.setStart(LocalDateTime.now());

        try {

            // プロセス開始ログ出力
            outputStartCommandLog(context, executeContext, executeScenario, executeFlow, executeCommand);

            // シナリオスコープ変数の設定
            settingFlowVariables(context, executeContext, executeScenario, executeFlow, executeCommand);

            // コマンド解決
            var commandId = executeCommand.getCommand().getCommand();

            // コマンド実行クラスを解決
            var runner = CommandRunnerResolverImpl.getInstance()
                    .getCommandRunner(commandId, context, executeContext, executeScenario, executeFlow, executeCommand);

            // 変数バインド
            bind(context, executeContext, executeScenario, executeFlow, executeCommand);

            // コマンド実行
            runner.execute(executeCommand.getCommand(), context, executeContext, executeScenario, executeFlow,
                    executeCommand, commandLogger);

            // プロセス成功
            executeCommand.getCommandResult().setStatus(CommandStatus.SUCCESS);

            // Flow成功
            executeFlow.setStatus(FlowStatus.SUCCESS);

        } catch (Throwable t) {

            log.debug("Error Occurred...", t);

            // 発生したエラーを設定
            executeCommand.setError(t);

            executeCommand.setStackTrace(ErrorUtils.getInstance().getStacktrace(t));

            // プロセス失敗
            executeCommand.getCommandResult().setStatus(CommandStatus.ERROR);

        } finally {

            // 掃除
            cleanFlowVariables(context, executeContext, executeScenario, executeFlow, executeCommand);

            // シナリオ実行終了時間を設定
            executeCommand.setEnd(LocalDateTime.now());

            // 所用時間を設定
            executeCommand.setDuration(Duration.between(executeCommand.getStart(), executeCommand.getEnd()));

            // コマンド終了ログ出力
            outputEndCommandLog(context, executeContext, executeScenario, executeFlow, executeCommand);

            // コマンドのログを収集
            var commandLogs = CommandLoggingHolder.get(executeCommand.getExecuteId().toString());
            executeCommand.setCommandLogs(commandLogs);

            // コマンドのログは収集し終えたらクリアする（ThreadLocalにて保持）
            CommandLoggingHolder.clear(executeCommand.getExecuteId().toString());

        }

        return FlowResult.getDefault();

    }

    /**
     * コマンドに対して、変数をバインドする.
     *
     * @param context
     * @param executeScenario
     * @param executeCommand
     */
    private void bind(final Context context, final ExecuteContext executeContext, final ExecuteScenario executeScenario,
            final ExecuteFlow executeFlow, final ExecuteCommand executeCommand) {

        final Map<String, String> profiles = new ConcurrentHashMap<>();

        if (StringUtils.isNotEmpty(context.getOption().getProfile())) {
            // プロファイルを抽出
            Arrays.stream(context.getOption().getProfile().split(",")).forEach(x -> {
                if (context.getOriginal().getProfiles().containsKey(x)) {
                    profiles.putAll(context.getOriginal().getProfiles().get(x));
                } else {
                    log.warn("not exists option profile -> {}", x);
                }
            });
        }

        executeFlow.getFlowVariables()
                .entrySet()
                .stream()
                .forEach(e -> profiles.put(e.getKey(), e.getValue().toString()));

        BindUtils.getInstance()
                .bind(executeCommand.getCommand(), profiles, executeContext.getGlobalVariables(),
                        executeScenario.getScenarioVariables(), executeFlow.getFlowVariables());
    }

    /**
     * Flowスコープの変数を設定する. プロセス実行時に指定を行うべきFlowスコープ変数の設定処理.
     *
     * @param context
     * @param scenario
     * @param executeCommand
     */
    private void settingFlowVariables(final Context context, final ExecuteContext executeContext,
            final ExecuteScenario scenario, final ExecuteFlow executeFlow, final ExecuteCommand executeCommand) {

        // 現在の処理コマンドのID
        executeFlow.getFlowVariables()
                .put(FlowScopeVariables.CURRENT_COMMAND.getName(), executeCommand.getCommand().getId());

        // 現在の処理コマンドの実行ID
        executeFlow.getFlowVariables()
                .put(FlowScopeVariables.CURRENT_COMMAND_EXECUTE_ID.getName(), executeCommand.getExecuteId());

        // 現在の処理コマンドの格納パス（文字列）
        executeFlow.getFlowVariables()
                .put(FlowScopeVariables.CURRENT_COMMAND_DIR.getName(),
                        getCommandBelongScenarioDirectory(context, executeCommand));

    }

    /**
     * シナリオスコープの変数を掃除する. プロセス実行時にのみ指定すべきシナリオスコープの変数を確実に除去するための処理.
     *
     * @param context
     * @param scenario
     * @param executeFlow
     * @param executeCommand
     */
    private void cleanFlowVariables(final Context context, final ExecuteContext executeContext,
            final ExecuteScenario scenario, final ExecuteFlow executeFlow, final ExecuteCommand executeCommand) {

        // 現在の処理コマンドのID
        executeFlow.getFlowVariables().remove(FlowScopeVariables.CURRENT_COMMAND.getName());

        // 現在の処理プロセスの実行ID
        executeFlow.getFlowVariables().remove(FlowScopeVariables.CURRENT_COMMAND_EXECUTE_ID.getName());

        // 現在の処理コマンドの格納パス（文字列）
        executeFlow.getFlowVariables().remove(FlowScopeVariables.CURRENT_COMMAND_DIR.getName());

    }

    /**
     * コマンド開始ログ出力.
     *
     * @param context
     * @param executeScenario
     * @param executeFlow
     * @param executeCommand
     */
    protected void outputStartCommandLog(final Context context, final ExecuteContext executeContext,
            final ExecuteScenario executeScenario, final ExecuteFlow executeFlow, final ExecuteCommand executeCommand) {
        log.info("■ Start Command ■ Scenario ID : {}, Command ID : {}, Execute Command ID : {}",
                executeScenario.getInfo().getId(), executeCommand.getCommand().getId(), executeCommand.getExecuteId());
    }

    /**
     * コマンド終了ログ出力.
     *
     * @param context
     * @param executeScenario
     * @param executeCommand
     */
    protected void outputEndCommandLog(final Context context, final ExecuteContext executeContext,
            final ExecuteScenario executeScenario, final ExecuteFlow executeFlow, final ExecuteCommand executeCommand) {
        if (executeCommand.getCommandResult().getStatus() == CommandStatus.SUCCESS) {
            if (executeCommand.isAssertCommand()) {
                log.info(
                        "■ End Command   ■ Scenario ID : {}, Command ID : {}, Execute Command ID : {}, Process Status : {}, Assert Status : {}",
                        executeScenario.getInfo().getId(), executeCommand.getCommand().getId(),
                        executeCommand.getExecuteId(), executeCommand.getCommandResult().getStatus().name(),
                        ((AssertCommandResult) executeCommand.getCommandResult()).getAssertStatus().name());
            } else {
                log.info(
                        "■ End Command   ■ Scenario ID : {}, Command ID : {}, Execute Command ID : {}, Process Status : {}",
                        executeScenario.getInfo().getId(), executeCommand.getCommand().getId(),
                        executeCommand.getExecuteId(), executeCommand.getCommandResult().getStatus().name());
            }
        } else if (executeCommand.getCommandResult().getStatus() == CommandStatus.ERROR) {
            if (executeCommand.isAssertCommand()) {
                log.error(
                        "■ End Command   ■ Scenario ID : {}, Command ID : {}, Execute Command ID : {}, Process Status : {}, Assert Status : {}",
                        executeScenario.getInfo().getId(), executeCommand.getCommand().getId(),
                        executeCommand.getExecuteId(), executeCommand.getCommandResult().getStatus().name(),
                        ((AssertCommandResult) executeCommand.getCommandResult()).getAssertStatus().name());
            } else {
                log.error(
                        "■ End Command   ■ Scenario ID : {}, Command ID : {}, Execute Command ID : {}, Process Status : {}",
                        executeScenario.getInfo().getId(), executeCommand.getCommand().getId(),
                        executeCommand.getExecuteId(), executeCommand.getCommandResult().getStatus().name());
            }
        } else {
            if (executeCommand.isAssertCommand()) {
                log.warn(
                        "■ End Command   ■ Scenario ID : {}, Command ID : {}, Execute Command ID : {}, Process Status : {}, Assert Status : {}",
                        executeScenario.getInfo().getId(), executeCommand.getCommand().getId(),
                        executeCommand.getExecuteId(), executeCommand.getCommandResult().getStatus().name(),
                        ((AssertCommandResult) executeCommand.getCommandResult()).getAssertStatus().name());
            } else {
                log.warn(
                        "■ End Command   ■ Scenario ID : {}, Command ID : {}, Execute Command ID : {}, Process Status : {}",
                        executeScenario.getInfo().getId(), executeCommand.getCommand().getId(),
                        executeCommand.getExecuteId(), executeCommand.getCommandResult().getStatus().name());
            }
        }
        if (context.getOption().getConsoleReport() && executeCommand.getError() != null) {
            log.error("Error Occurred...", executeCommand.getError());
        }
    }

    /**
     * 実行中のコマンドが属するシナリオ格納ディレクトリを取得する.
     * コマンドに定義されているファイル等を参照する場合には、このメソッドで解決したパスからの相対パスを利用する.
     *
     * @return シナリオ配置パス
     */
    protected String getCommandBelongScenarioDirectory(Context context, ExecuteCommand executeCommand) {
        // シナリオ識別子はシステムで投入する値のため、存在しないことは不正
        var belongScenarioId = IDUtils.getInstance().extractBelongScenarioIdFromFqcn(executeCommand.getFqcn());
        if (StringUtils.isEmpty(belongScenarioId)) {
            throw new SystemException(CoreMessages.CORE_ERR_0018, executeCommand.getFqcn());
        }
        if (context.getOriginal().getScenarioPlacePaths().containsKey(belongScenarioId)) {
            Path belongScenarioPath = context.getOriginal().getScenarioPlacePaths().get(belongScenarioId);
            if (Files.notExists(belongScenarioPath)) {
                throw new SystemException(CoreMessages.CORE_ERR_0017, belongScenarioId.toString());
            }
            return belongScenarioPath.toString();
        } else {
            throw new SystemException(CoreMessages.CORE_ERR_0016, belongScenarioId);
        }
    }

}
