/* Copyright (c) 2017-2021 Nozomu Takashima. */
package com.epion_t3.basic.messages;

import com.epion_t3.core.message.Messages;

/**
 * basic用メッセージ定義Enum.<br>
 *
 * @author epion-t3-devtools
 */
public enum BasicMessages implements Messages {

    /** 指定されたインデックスが有効範囲にありません。インデックス：{0} */
    BASIC_ERR_9020("com.epion_t3.basic.err.9020"),

    /** フォーマット後の格納先変数の指定は必須です. */
    BASIC_ERR_9010("com.epion_t3.basic.err.9010"),

    /** 指定パターンに合致する文字列が含まれています.指定パターン:{0} */
    BASIC_INF_0001("com.epion_t3.basic.inf.0001"),

    /** 対象のファイルの読み込みに失敗しました.パス：{0} */
    BASIC_ERR_9008("com.epion_t3.basic.err.9008"),

    /** 変数は配列である必要があります。変数：{0}, タイプ：{1} */
    BASIC_ERR_9019("com.epion_t3.basic.err.9019"),

    /** 本コマンドはjava.util.Dateを扱うためのコマンドです.変数に格納されているものは型が異なります. */
    BASIC_ERR_9009("com.epion_t3.basic.err.9009"),

    /** 日付演算後の格納先変数の指定は必須です. */
    BASIC_ERR_9011("com.epion_t3.basic.err.9011"),

    /** 参照する変数のスコープが不正です.スコープ:{0} */
    BASIC_ERR_9001("com.epion_t3.basic.err.9001"),

    /** ローカルコマンド実行でエラーが発生しました. */
    BASIC_ERR_9012("com.epion_t3.basic.err.9012"),

    /** 指定パターンに合致する文字列が含まれていません.指定パターン:{0} */
    BASIC_ERR_9002("com.epion_t3.basic.err.9002"),

    /** ローカルコマンド実行でタイムアウトが発生しました. */
    BASIC_ERR_9013("com.epion_t3.basic.err.9013"),

    /** 値（value）は必須です. */
    BASIC_ERR_9003("com.epion_t3.basic.err.9003"),

    /** 条件式が正しく評価できていません。評価結果が真偽値出ない場合があります。 */
    BASIC_ERR_9014("com.epion_t3.basic.err.9014"),

    /** 値（value）は数値で指定してください. */
    BASIC_ERR_9004("com.epion_t3.basic.err.9004"),

    /** 対象の変数の値はint型の正数である必要があります。 */
    BASIC_ERR_9015("com.epion_t3.basic.err.9015"),

    /** 対象（target）は必須です. */
    BASIC_ERR_9005("com.epion_t3.basic.err.9005"),

    /** インクリメント幅の値はint型の正数である必要があります。 */
    BASIC_ERR_9016("com.epion_t3.basic.err.9016"),

    /** ユーザー入力にてエラーが発生しました. */
    BASIC_ERR_9006("com.epion_t3.basic.err.9006"),

    /** 指定された変数は存在しませんでした。変数：{0} */
    BASIC_ERR_9017("com.epion_t3.basic.err.9017"),

    /** 対象のファイルが見つかりません.パス：{0} */
    BASIC_ERR_9007("com.epion_t3.basic.err.9007"),

    /** 変数は文字列である必要があります。変数：{0} */
    BASIC_ERR_9018("com.epion_t3.basic.err.9018");

    /** メッセージコード */
    private final String messageCode;

    /**
     * プライベートコンストラクタ<br>
     *
     * @param messageCode メッセージコード
     */
    private BasicMessages(final String messageCode) {
        this.messageCode = messageCode;
    }

    /**
     * messageCodeを取得します.<br>
     *
     * @return messageCode
     */
    public String getMessageCode() {
        return this.messageCode;
    }
}
