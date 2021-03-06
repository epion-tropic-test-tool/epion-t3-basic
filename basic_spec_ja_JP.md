# Basic カスタム機能ドキュメント

このドキュメントは、Basic のカスタム機能が提供する、
Flow、コマンド、設定定義についての説明及び出力するメッセージの定義について説明する。

- Contents
  - [Information](#Information)
  - [Description](#Description)
  - [Flow List](#Flow-List)
  - [Command List](#Command-List)
  - [Configuration List](#Configuration-List)
  - [Message List](#Message-List)

## Information

本カスタム機能の基本情報は以下の通り。

基本的な動作を行うコマンドを提供します。

- Name : `basic`
- Custom Package : `com.epion_t3.basic`

## Description
ETTTを扱うための基本的な動作を行うコマンドおよびFlowを提供します。

## Flow List

本カスタム機能が提供するFlowの一覧及び詳細。

|Name|Summary|
|:---|:---|
|[CounterIterate](#CounterIterate)|指定されたカウンタ分ループ処理を行います。  |
|[Branch](#Branch)|シナリオ中で分岐を行います。  任意の条件式を判定して分岐を行います。  条件は、評価の結果明示的に真偽を出せるものが対象となります。  |
|[ReadFileIterate](#ReadFileIterate)|ファイルを読み込んで、１行毎にループ処理を行います。  テキストやCSVといったファイルに情報を記載し、処理を繰り返したい場合に有効利用できます。  |
|[Break](#Break)|シナリオ中で繰り返し処理を行うFlowの子Flowでの利用を想定したコマンドです。  子Flowのループを明示的に抜ける（break）するためのFlowです。  |
|[DoWhile](#DoWhile)|シナリオ中で繰り返し処理を行うFlowです。  指定された条件式が真である限り、実行し続けます。  |
|[CommandExecute](#CommandExecute)|コマンドを実行します。  単純にコマンドを実行するのみの最も基本的なFlowです。  |
|[While](#While)|シナリオ中で繰り返し処理を行うFlowです。  指定された条件式が真である限り、実行し続けます。  |
|[Continue](#Continue)|シナリオ中で繰り返し処理を行うFlowの子Flowでの利用を想定したコマンドです。  子Flowのループを明示的に次要素へ進める（continue）するためのFlowです。  |
|[If](#If)|シナリオ中で分岐を行い、評価条件が真の場合に子Flowを実行します。  |

------

### CounterIterate
指定されたカウンタ分ループ処理を行います。
#### Functions
- ファイルを読み込んで、１行毎にループ処理を行います。

#### Structure
```yaml
commands : 
  id : FlowのID
  type : 「CounterIterate」固定
  summary : Flowの概要（任意）
  from : カウンタ始値
  to : カウンタ終値

```

------

### Branch
シナリオ中で分岐を行います。任意の条件式を判定して分岐を行います。条件は、評価の結果明示的に真偽を出せるものが対象となります。
#### Functions
- シナリオ中で分岐を行います。
- 任意の条件式を判定して分岐を行います。
- 条件は、評価の結果明示的に真偽を出せるものが対象となります。
- 条件式には変数が利用可能です。

#### Structure
```yaml
commands : 
  id : FlowのID
  type : 「Branch」固定
  summary : Flowの概要（任意）
  condition : 判定する条件式（JavaScriptで指定）
  trueRef : 条件式の判定結果が真の場合に実行するコマンドの指定
  falseRef : 条件式の判定結果が偽の場合に実行するコマンドの指定

```

1. 条件式は、JavaScriptで記載を行ってください。
1. 判定する条件式には変数が利用可能です。様々な値をリアルタイムに利用して判定を行うことが可能となります。
1. 同じシナリオ定義内に存在するコマンドを呼ぶ場合は、コマンドIDをそのまま指定します。別のシナリオ定義内に存在するコマンドを呼ぶ場合は、「対象シナリオファイルのinfo.id + @ + コマンドID」を指定します。
1. 同じシナリオ定義内に存在するコマンドを呼ぶ場合は、コマンドIDをそのまま指定します。別のシナリオ定義内に存在するコマンドを呼ぶ場合は、「対象シナリオファイルのinfo.id + @ + コマンドID」を指定します。
------

### ReadFileIterate
ファイルを読み込んで、１行毎にループ処理を行います。テキストやCSVといったファイルに情報を記載し、処理を繰り返したい場合に有効利用できます。
#### Functions
- ファイルを読み込んで、１行毎にループ処理を行います。

#### Structure
```yaml
commands : 
  id : FlowのID
  type : 「Branch」固定
  summary : Flowの概要（任意）
  target : 読み込む対象のファイルパス
  fileType : ファイルの種別を指定
  encoding : ファイルのエンコーディングを指定
  lineSeparator : 改行コードを指定
  wrapstring : 囲い文字列

```

------

### Break
シナリオ中で繰り返し処理を行うFlowの子Flowでの利用を想定したコマンドです。子Flowのループを明示的に抜ける（break）するためのFlowです。
#### Functions
- シナリオ中で繰り返し処理を行うFlowの子Flowでの利用を想定したコマンドです。
- 子Flowのループを明示的に抜ける（break）するためのFlowです。
- このFlowが実行された場合、繰り返し処理を行うFlowは中断してループを抜けます。
- 通常、このFlowは「If」と組み合わせて使うことで効果が出るものです。

#### Structure
```yaml
commands : 
  id : FlowのID
  type : 「Break」固定
  summary : Flowの概要（任意）
  target : 対象のFlowIDを指定 # (1)
  condition : 判定する条件式（JavaScriptで指定）

```

1. 変数名は「スコープ.変数名」の形式で指定します。「global.hoge」であればグローバルスコープにhogeという変数名で値を定義することになります。
1. 条件式は、JavaScriptで記載を行ってください。
1. 判定する条件式には変数が利用可能です。様々な値をリアルタイムに利用して判定を行うことが可能となります。
------

### DoWhile
シナリオ中で繰り返し処理を行うFlowです。指定された条件式が真である限り、実行し続けます。
#### Functions
- シナリオ中で繰り返し処理を行うFlowです。
- 指定された条件式が真である限り、実行し続けます。
- このWhileを抜けるためには、条件式が false となるか、子Flowが「Break」を実行するか、タイムアウトで指定した時間が経過するかのいずれかを満たす必要があります。
- タイムアウトは、デフォルトが30秒（30000ms）となっています。

#### Structure
```yaml
commands : 
  id : FlowのID
  type : 「DoWhile」固定
  summary : Flowの概要（任意）
  condition : 判定する条件式（JavaScriptで指定）
  children : 条件式の判定結果が真の場合に実行するコマンドの指定
  timeout : タイムアウト時間（ms）

```

1. 条件式は、JavaScriptで記載を行ってください。
1. 判定する条件式には変数が利用可能です。様々な値をリアルタイムに利用して判定を行うことが可能となります。
1. 同じシナリオ定義内に存在するコマンドを呼ぶ場合は、コマンドIDをそのまま指定します。別のシナリオ定義内に存在するコマンドを呼ぶ場合は、「対象シナリオファイルのinfo.id + @ + コマンドID」を指定します。
1. タイムアウト時間は、デフォルトで30秒（30000ms）となります。
------

### CommandExecute
コマンドを実行します。単純にコマンドを実行するのみの最も基本的なFlowです。
#### Functions
- 指定されたコマンドを実行します。
- 同一シナリオファイルに存在するコマンドを実行します。
- 他のシナリオファイルに存在するコマンドも実行できます。

#### Structure
```yaml
commands : 
  id : FlowのID
  type : 「CommandExecute」固定
  summary : Flowの概要（任意）
  ref : 実行するコマンドの指定

```

1. 同じシナリオ定義内に存在するコマンドを呼ぶ場合は、コマンドIDをそのまま指定します。別のシナリオ定義内に存在するコマンドを呼ぶ場合は、「対象シナリオファイルのinfo.id + @ + コマンドID」を指定します。
------

### While
シナリオ中で繰り返し処理を行うFlowです。指定された条件式が真である限り、実行し続けます。
#### Functions
- シナリオ中で繰り返し処理を行うFlowです。
- 指定された条件式が真である限り、実行し続けます。
- このWhileを抜けるためには、条件式が false となるか、子Flowが「Break」を実行するか、タイムアウトで指定した時間が経過するかのいずれかを満たす必要があります。
- タイムアウトは、デフォルトが30秒（30000ms）となっています。

#### Structure
```yaml
commands : 
  id : FlowのID
  type : 「While」固定
  summary : Flowの概要（任意）
  condition : 判定する条件式（JavaScriptで指定）
  children : 条件式の判定結果が真の場合に実行するコマンドの指定
  timeout : タイムアウト時間（ms）

```

1. 条件式は、JavaScriptで記載を行ってください。
1. 判定する条件式には変数が利用可能です。様々な値をリアルタイムに利用して判定を行うことが可能となります。
1. 同じシナリオ定義内に存在するコマンドを呼ぶ場合は、コマンドIDをそのまま指定します。別のシナリオ定義内に存在するコマンドを呼ぶ場合は、「対象シナリオファイルのinfo.id + @ + コマンドID」を指定します。
1. タイムアウト時間は、デフォルトで30秒（30000ms）となります。
------

### Continue
シナリオ中で繰り返し処理を行うFlowの子Flowでの利用を想定したコマンドです。子Flowのループを明示的に次要素へ進める（continue）するためのFlowです。
#### Functions
- シナリオ中で繰り返し処理を行うFlowの子Flowでの利用を想定したコマンドです。
- 子Flowのループを明示的に次要素へ進める（continue）するためのFlowです。
- このFlowが実行された場合、繰り返し処理を行うFlowは次の要素へループを進めます。同列の以降のFlow処理は行われません。
- 通常、このFlowは「If」と組み合わせて使うことで効果が出るものです。

#### Structure
```yaml
commands : 
  id : FlowのID
  type : 「Continue」固定
  summary : Flowの概要（任意）
  target : 対象のFlowIDを指定 # (1)
  condition : 判定する条件式（JavaScriptで指定）

```

1. 変数名は「スコープ.変数名」の形式で指定します。「global.hoge」であればグローバルスコープにhogeという変数名で値を定義することになります。
1. 条件式は、JavaScriptで記載を行ってください。
1. 判定する条件式には変数が利用可能です。様々な値をリアルタイムに利用して判定を行うことが可能となります。
------

### If
シナリオ中で分岐を行い、評価条件が真の場合に子Flowを実行します。
#### Functions
- シナリオ中で分岐を行います。
- 任意の条件式を判定して分岐を行います。
- 条件は、評価の結果明示的に真偽を出せるものが対象となります。
- 条件式には変数が利用可能です。
- 条件式が真の場合に、子Flowを実行します。

#### Structure
```yaml
commands : 
  id : FlowのID
  type : 「If」固定
  summary : Flowの概要（任意）
  condition : 判定する条件式（JavaScriptで指定）
  children : 条件式の判定結果が真の場合に実行するコマンドの指定

```

1. 条件式は、JavaScriptで記載を行ってください。
1. 判定する条件式には変数が利用可能です。様々な値をリアルタイムに利用して判定を行うことが可能となります。
1. 同じシナリオ定義内に存在するコマンドを呼ぶ場合は、コマンドIDをそのまま指定します。別のシナリオ定義内に存在するコマンドを呼ぶ場合は、「対象シナリオファイルのinfo.id + @ + コマンドID」を指定します。

## Command List

本カスタム機能が提供するコマンドの一覧及び詳細。

|Name|Summary|Assert|Evidence|
|:---|:---|:---|:---|
|[FileDelete](#FileDelete)|指定されたファイルを削除します。試験時に不要なファイルを消す目的で利用できます。  |||
|[RemoveVariable](#RemoveVariable)|任意のスコープ変数に設定してある変数を削除します。  |||
|[StringConcat](#StringConcat)|複数の文字列を結合して１つの文字列へ加工します。  |||
|[CreateNowDate](#CreateNowDate)|現在日付を作成して変数に設定します。  |||
|[AssertNotExistsStringInText](#AssertNotExistsStringInText)|指定したテキストファイルの中に指定した文字列を含まれないことを確認するためのコマンドです。ログファイルに対する確認などに利用可能です。  |X||
|[FileCopy](#FileCopy)|指定されたファイルをコピーします。  |||
|[ExceptionOccurred](#ExceptionOccurred)|Exceptionを意図的に発生させます。  ツールの動作確認などでの利用を想定しています。  |||
|[VariableEcho](#VariableEcho)|変数をログ出力します。  |||
|[ExtractArrayValue](#ExtractArrayValue)|変数に設定されている配列から指定インデックスの値を取得し、変数に設定します。  |||
|[StringSplit](#StringSplit)|変数の文字列を特定文字で分割して変数に設定しなおします。  |||
|[Sleep](#Sleep)|指定された時間休止します。  |||
|[CounterIncrement](#CounterIncrement)|int型の正数をインクリメントします。  汎用的なカウンタとして利用出来ます。  |||
|[AddDate](#AddDate)|日付に対して指定した日付を足します。足した結果を変数に設定します。  |||
|[ExecuteLocalCommand](#ExecuteLocalCommand)|本ツールが実行されているマシンのローカルコマンドを同期実行します。  ||X|
|[CreateUUID](#CreateUUID)|UUIDを作成します。  |||
|[FormatDateString](#FormatDateString)|日付を指定されたフォーマットの文字列に変換します。  |||
|[ConsoleInput](#ConsoleInput)|ユーザーのコンソール入力を行うための機能です。自動テストでは利用用途がありませんが、動作確認時にには有効です。  |||
|[FileGet](#FileGet)|ファイルシステムを利用したファイル取得機能です。 ローカルテスト時等でしか役に立たないかもしれないですが、初期ツール導入時にご活用ください。  ||X|
|[AssertExceptionOccurred](#AssertExceptionOccurred)|アサートコマンド時のExceptionを意図的に発生させます。  ツールの動作確認などでの利用を想定しています。  |||
|[CreateUUIDString](#CreateUUIDString)|UUIDを作成し、文字列に変換します。  |||
|[SetVariable](#SetVariable)|任意のスコープ変数に対して値を設定します。  設定する値自体も変数のバインドが利用できますが、基本的には固定値で利用されることを想定してます。  |||
|[DirectoryCreate](#DirectoryCreate)|ディレクトリの作成を行います。  |||
|[AssertExistsStringInText](#AssertExistsStringInText)|指定したテキストファイルの中に指定した文字列を含むことを確認するためのコマンドです。ログファイルに対する確認などに利用可能です。  |X||

------

### FileDelete
指定されたファイルを削除します。試験時に不要なファイルを消す目的で利用できます。
#### Command Type
- Assert : No
- Evidence : No

#### Functions
- 指定されたファイルを削除します。

#### Structure
```yaml
commands : 
  id : コマンドのID
  command : 「FileDelete」固定
  summary : コマンドの概要（任意）
  description : コマンドの詳細（任意）
  target : 削除対象ファイルを指定 # (1)

```

1. 削除対象のファイルのパスを指定します。このパスには変数をバインド可能です。絶対パスで指定するようにしてください。
------

### RemoveVariable
任意のスコープ変数に設定してある変数を削除します。
#### Command Type
- Assert : No
- Evidence : No

#### Functions
- 任意のスコープ変数に設定してある変数を削除します。

#### Structure
```yaml
commands : 
  id : コマンドのID
  command : 「RemoveVariable」固定
  summary : コマンドの概要（任意）
  description : コマンドの詳細（任意）
  target : 変数名を指定 # (1)

```

1. 変数名は「スコープ.変数名」の形式で指定します。「global.hoge」であればグローバルスコープにhogeという変数名で値を定義することになります。
------

### StringConcat
複数の文字列を結合して１つの文字列へ加工します。
#### Command Type
- Assert : No
- Evidence : No

#### Functions
- 複数の文字列を結合して１つの文字列へ加工します。

#### Structure
```yaml
commands : 
  id : コマンドのID
  command : 「StringConcat」固定
  summary : コマンドの概要（任意）
  description : コマンドの詳細（任意）
  target : 結合後の文字列を格納する変数名を指定
  referenceVariables : 結合する文字列を指定

```

------

### CreateNowDate
現在日付を作成して変数に設定します。
#### Command Type
- Assert : No
- Evidence : No

#### Functions
- 現在日付を作成して変数に設定します。

#### Structure
```yaml
commands : 
  id : コマンドのID
  command : 「CreateNowDate」固定
  summary : コマンドの概要（任意）
  description : コマンドの詳細（任意）
  target : 現在日付を設定する変数を指定

```

------

### AssertNotExistsStringInText
指定したテキストファイルの中に指定した文字列を含まれないことを確認するためのコマンドです。ログファイルに対する確認などに利用可能です。
#### Command Type
- Assert : __Yes__
- Evidence : No

#### Functions
- 指定したテキストファイル中に任意の文字列が含まれないことを確認します。
- 対象とするテキストファイルはあらかじめエビデンスとして保存されている必要があります。

#### Structure
```yaml
commands : 
  id : コマンドのID
  command : 「AssertNotExistsStringInText」固定
  summary : コマンドの概要（任意）
  description : コマンドの詳細（任意）
  target : 対象のFlowIDを指定 # (1)
  value : 含まれないことを確認する値

```

1. 変数名は「スコープ.変数名」の形式で指定します。「global.hoge」であればグローバルスコープにhogeという変数名で値を定義することになります。
------

### FileCopy
指定されたファイルをコピーします。
#### Command Type
- Assert : No
- Evidence : No

#### Functions
- 指定されたファイルをコピーします。

#### Structure
```yaml
commands : 
  id : コマンドのID
  command : 「FileCopy」固定
  summary : コマンドの概要（任意）
  description : コマンドの詳細（任意）
  from : コピー元の対象を指定 # (1)
  to : コピー先の対象を指定 # (2)

```

1. コピー元の対象の指定します。このパスには変数をバインド可能です。絶対パスで指定するようにしてください。
1. コピー先の対象の指定します。このパスには変数をバインド可能です。絶対パスで指定するようにしてください。
------

### ExceptionOccurred
Exceptionを意図的に発生させます。ツールの動作確認などでの利用を想定しています。
#### Command Type
- Assert : No
- Evidence : No

#### Functions
- Exceptionを意図的に発生させます。

#### Structure
```yaml
commands : 
  id : コマンドのID
  command : 「ExceptionOccurred」固定
  summary : コマンドの概要（任意）
  description : コマンドの詳細（任意）
  target : 設定不要。
  value : 設定不要。

```

1. 設定不要。
1. 設定不要。
------

### VariableEcho
変数をログ出力します。
#### Command Type
- Assert : No
- Evidence : No

#### Functions
- 変数をログ出力します。

#### Structure
```yaml
commands : 
  id : コマンドのID
  command : 「VariableEcho」固定
  summary : コマンドの概要（任意）
  description : コマンドの詳細（任意）
  target : 対象の変数名を指定。

```

1. 変数名は「スコープ.変数名」の形式で指定します。「global.hoge」であればグローバルスコープにhogeという変数名で値を定義することになります。
------

### ExtractArrayValue
変数に設定されている配列から指定インデックスの値を取得し、変数に設定します。
#### Command Type
- Assert : No
- Evidence : No

#### Functions
- 変数に設定されている配列から指定インデックスの値を取得し、変数に設定します。

#### Structure
```yaml
commands : 
  id : コマンドのID
  command : 「ExtractArrayValue」固定
  summary : コマンドの概要（任意）
  description : コマンドの詳細（任意）
  target : 対象の変数名を指定。
  value : 抽出後の変数名を指定。
  index : 抽出するインデックスを指定。

```

1. 変数名は「スコープ.変数名」の形式で指定します。「global.hoge」であればグローバルスコープにhogeという変数名で値を定義することになります。
------

### StringSplit
変数の文字列を特定文字で分割して変数に設定しなおします。
#### Command Type
- Assert : No
- Evidence : No

#### Functions
- 変数の文字列を特定文字で分割して変数に設定しなおします。

#### Structure
```yaml
commands : 
  id : コマンドのID
  command : 「StringSplit」固定
  summary : コマンドの概要（任意）
  description : コマンドの詳細（任意）
  target : 対象の変数名を指定。
  value : 分割区切り文字列を指定します。

```

1. 変数名は「スコープ.変数名」の形式で指定します。「global.hoge」であればグローバルスコープにhogeという変数名で値を定義することになります。
------

### Sleep
指定された時間休止します。
#### Command Type
- Assert : No
- Evidence : No

#### Functions
- 指定されたミリ秒の間休止します。全ての動作が止まります。

#### Structure
```yaml
commands : 
  id : コマンドのID
  command : 「Sleep」固定
  summary : コマンドの概要（任意）
  description : コマンドの詳細（任意）
  value : 休止する時間をミリ秒で指定

```

------

### CounterIncrement
int型の正数をインクリメントします。汎用的なカウンタとして利用出来ます。
#### Command Type
- Assert : No
- Evidence : No

#### Functions
- int型の正数をインクリメントします。
- インクリメント幅は任意に設定可能です。

#### Structure
```yaml
commands : 
  id : コマンドのID
  command : 「CounterIncrement」固定
  summary : コマンドの概要（任意）
  description : コマンドの詳細（任意）
  target : 対象の変数名を指定。
  value : インクリメント幅を指定します。int型の正数の数値である必要があります。

```

1. 変数名は「スコープ.変数名」の形式で指定します。「global.hoge」であればグローバルスコープにhogeという変数名で値を定義することになります。
------

### AddDate
日付に対して指定した日付を足します。足した結果を変数に設定します。
#### Command Type
- Assert : No
- Evidence : No

#### Functions
- 日付に対して指定した日付を足します。足した結果を変数に設定します。

#### Structure
```yaml
commands : 
  id : コマンドのID
  command : 「AddDate」固定
  summary : コマンドの概要（任意）
  description : コマンドの詳細（任意）
  target : 加算対象の日付が格納されている変数名を指定
  value : 加算する日付を数値で指定
  addedTarget : 加算した結果日付を格納する変数名を指定

```

------

### ExecuteLocalCommand
本ツールが実行されているマシンのローカルコマンドを同期実行します。
#### Command Type
- Assert : No
- Evidence : __Yes__

#### Functions
- 本ツールが実行されているマシンのローカルコマンドを同期実行します。
- 実行ファイル及び引数を指定することができます。
- 引数には変数バインドを指定することができます。
- 実行時間には制限（タイムアウト）を設けることができます。省略した場合「60000」を制限とします。
- 標準出力及びエラー出力はエビデンスとして保存されます。
- 正常として扱う終了コードを指定できます。省略した場合「0」を正常とします。

#### Structure
```yaml
commands : 
  id : コマンドのID
  command : 「EexecuteLocalCommand」固定
  summary : コマンドの概要（任意）
  description : コマンドの詳細（任意）
  target : 実行する実行形式ファイルもしくはコマンドを指定します。
  args : 引数を指定したい場合は指定します。
  timeout : 実行時間の制限（タイムアウト）の値を設定（ミリ秒）します。
  successExitCodes : 正常終了とする終了コードを指定します。

```

1. 変数名は「スコープ.変数名」の形式で指定します。「global.hoge」であればグローバルスコープにhogeという変数名で値を定義することになります。
------

### CreateUUID
UUIDを作成します。
#### Command Type
- Assert : No
- Evidence : No

#### Functions
- UUIDを作成します。
- 任意の変数へ設定可能です。

#### Structure
```yaml
commands : 
  id : コマンドのID
  command : 「CreateUUID」固定
  summary : コマンドの概要（任意）
  description : コマンドの詳細（任意）
  target : UUIDを設定する変数を指定 # (1)

```

1. UUIDを生成した後に設定する変数を指定します。
------

### FormatDateString
日付を指定されたフォーマットの文字列に変換します。
#### Command Type
- Assert : No
- Evidence : No

#### Functions
- 日付を指定されたフォーマットの文字列に変換します。
- 指定できる日付フォーマットは、[Java標準](https://docs.oracle.com/javase/jp/8/docs/api/java/util/regex/Pattern.html)となります。

#### Structure
```yaml
commands : 
  id : コマンドのID
  command : 「FormatDateString」固定
  summary : コマンドの概要（任意）
  description : コマンドの詳細（任意）
  target : 現在日付を設定する変数を指定
  value : 日付フォーマットのパターンを指定
  formattedTarget : フォーマット処理後の値を格納する変数名を指定

```

------

### ConsoleInput
ユーザーのコンソール入力を行うための機能です。自動テストでは利用用途がありませんが、動作確認時にには有効です。
#### Command Type
- Assert : No
- Evidence : No

#### Functions
- ユーザのコンソール入力が行われるまで待ち続けます。
- 入力された文字列は指定した変数へ設定します。

#### Structure
```yaml
commands : 
  id : コマンドのID
  command : 「ConsoleInput」固定
  summary : コマンドの概要（任意）
  description : コマンドの詳細（任意）
  target : 入力を促す文字列を指定。ユーザーが何を入力すれば良いかをわかりやすく指定。
  value : 入力した文字列を格納する対象の変数名を指定。

```

------

### FileGet
ファイルシステムを利用したファイル取得機能です。 ローカルテスト時等でしか役に立たないかもしれないですが、初期ツール導入時にご活用ください。
#### Command Type
- Assert : No
- Evidence : __Yes__

#### Functions
- ファイルシステムを利用してファイルを取得します
- 取得したファイルはエビデンスとして保存します

#### Structure
```yaml
commands : 
  id : コマンドのID
  command : 「FileGet」固定
  summary : コマンドの概要（任意）
  description : コマンドの詳細（任意）
  target : ファイルパス(絶対パス) # (1)

```

1. 取得するファイルパスを絶対パスにて指定します。変数のバインドにも対応していますので、絶対パスのベースパス等の解決にも利用可能です。
------

### AssertExceptionOccurred
アサートコマンド時のExceptionを意図的に発生させます。ツールの動作確認などでの利用を想定しています。
#### Command Type
- Assert : No
- Evidence : No

#### Functions
- アサートコマンド時のExceptionを意図的に発生させます。

#### Structure
```yaml
commands : 
  id : コマンドのID
  command : 「AssertExceptionOccurred」固定
  summary : コマンドの概要（任意）
  description : コマンドの詳細（任意）
  target : 設定不要。
  value : 設定不要。

```

1. 設定不要。
1. 設定不要。
------

### CreateUUIDString
UUIDを作成し、文字列に変換します。
#### Command Type
- Assert : No
- Evidence : No

#### Functions
- UUIDを作成します。
- 任意の変数へ設定可能です。
- 文字列へ変換します。

#### Structure
```yaml
commands : 
  id : コマンドのID
  command : 「CreateUUIDString」固定
  summary : コマンドの概要（任意）
  description : コマンドの詳細（任意）
  target : UUIDを設定する変数を指定 # (1)

```

1. UUIDを生成した後に設定する変数を指定します。
------

### SetVariable
任意のスコープ変数に対して値を設定します。設定する値自体も変数のバインドが利用できますが、基本的には固定値で利用されることを想定してます。
#### Command Type
- Assert : No
- Evidence : No

#### Functions
- 任意のスコープ変数に対して値を設定します。
- 設定する値自体も変数のバインドが利用できますが、基本的には固定値で利用されることを想定してます。

#### Structure
```yaml
commands : 
  id : コマンドのID
  command : 「SetVariable」固定
  summary : コマンドの概要（任意）
  description : コマンドの詳細（任意）
  target : 変数名を指定
  value : 値を指定

```

1. 変数名は「スコープ.変数名」の形式で指定します。「global.hoge」であればグローバルスコープにhogeという変数名で値を定義することになります。
1. 値は基本的に固定値を指定します。「あああ」と定義した場合は文字列で「あああ」と登録されます。
------

### DirectoryCreate
ディレクトリの作成を行います。
#### Command Type
- Assert : No
- Evidence : No

#### Functions
- ディレクトリの作成を行います。

#### Structure
```yaml
commands : 
  id : コマンドのID
  command : 「DirectoryCreate」固定
  summary : コマンドの概要（任意）
  description : コマンドの詳細（任意）
  target : 作成するディレクトリパスを指定（絶対パス）

```

1. 作成するディレクトリパスを指定。このパスには変数をバインド可能です。絶対パスで指定するようにしてください。
------

### AssertExistsStringInText
指定したテキストファイルの中に指定した文字列を含むことを確認するためのコマンドです。ログファイルに対する確認などに利用可能です。
#### Command Type
- Assert : __Yes__
- Evidence : No

#### Functions
- 指定したテキストファイル中に任意の文字列が含まれていることを確認します。
- 対象とするテキストファイルはあらかじめエビデンスとして保存されている必要があります。

#### Structure
```yaml
commands : 
  id : コマンドのID
  command : 「AssertExistsStringInText」固定
  summary : コマンドの概要（任意）
  description : コマンドの詳細（任意）
  target : 対象のFlowIDを指定 # (1)
  value : 含まれていることを確認する値

```

1. 変数名は「スコープ.変数名」の形式で指定します。「global.hoge」であればグローバルスコープにhogeという変数名で値を定義することになります。

## Configuration List

本カスタム機能が提供する設定定義の一覧及び詳細。

|Name|Summary|
|:---|:---|


## Message List

本カスタム機能が出力するメッセージの一覧及び内容。

|MessageID|MessageContents|
|:---|:---|
|com.epion_t3.basic.err.9020|指定されたインデックスが有効範囲にありません。インデックス：{0}|
|com.epion_t3.basic.err.9010|フォーマット後の格納先変数の指定は必須です.|
|com.epion_t3.basic.inf.0001|指定パターンに合致する文字列が含まれています.指定パターン:{0}|
|com.epion_t3.basic.err.9008|対象のファイルの読み込みに失敗しました.パス：{0}|
|com.epion_t3.basic.err.9019|変数は配列である必要があります。変数：{0}, タイプ：{1}|
|com.epion_t3.basic.err.9009|本コマンドはjava.util.Dateを扱うためのコマンドです.変数に格納されているものは型が異なります.|
|com.epion_t3.basic.err.9011|日付演算後の格納先変数の指定は必須です.|
|com.epion_t3.basic.err.9001|参照する変数のスコープが不正です.スコープ:{0}|
|com.epion_t3.basic.err.9012|ローカルコマンド実行でエラーが発生しました.|
|com.epion_t3.basic.err.9002|指定パターンに合致する文字列が含まれていません.指定パターン:{0}|
|com.epion_t3.basic.err.9013|ローカルコマンド実行でタイムアウトが発生しました.|
|com.epion_t3.basic.err.9003|値（value）は必須です.|
|com.epion_t3.basic.err.9014|条件式が正しく評価できていません。評価結果が真偽値出ない場合があります。|
|com.epion_t3.basic.err.9004|値（value）は数値で指定してください.|
|com.epion_t3.basic.err.9015|対象の変数の値はint型の正数である必要があります。|
|com.epion_t3.basic.err.9005|対象（target）は必須です.|
|com.epion_t3.basic.err.9016|インクリメント幅の値はint型の正数である必要があります。|
|com.epion_t3.basic.err.9006|ユーザー入力にてエラーが発生しました.|
|com.epion_t3.basic.err.9017|指定された変数は存在しませんでした。変数：{0}|
|com.epion_t3.basic.err.9007|対象のファイルが見つかりません.パス：{0}|
|com.epion_t3.basic.err.9018|変数は文字列である必要があります。変数：{0}|
