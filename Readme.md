## 説明

このプロジェクトは下記の内容を試すために作成されたサンプルのアプリケーションです。

- Spring Cloud Function
- Gradleのマルチプロジェクト
- SpringBoot,AWS SecretsManagerを活用してAWS RDSに接続

## Spring Cloud Function

Javaアプリケーションに関数型プログラミングを取り入れ、コードの再利用性を高めるためのフレームワーク

複数のクラウドプロバイダーやランタイム(AWS Lambda,Azure Functions,Google Cloud Function)に依存せず、

関数(Function,Consumer,Supplier)を簡単に実装・デプロイできる仕組み

### Handlerの設定

```Java
// config/Sample.java
// 設定ファイルに使用するhandlerの一覧を定義
@Configuration
public class Sample {

    @Bean
    Method1 method1() {
        return new Method1();
    }

    @Bean
    Method2 method2(DatabaseQueryServicve databaseQueryService) {
        return new Method2(databaseQueryService);
    }
}
```

```Java
// function/Method1.java
// 各処理詳細は下記のファイルに定義
// AbstractFunctionを継承することで関数(Function,Supplier,Consumer)として処理を定義することができる
@Component
public class Method1 extends AbstractFunction<Void>{
    
    private static final String PROCESS_NAME = "メソッド１";

    private final String message = "hello world from method1";

    private final Logger logger = LoggerFactory.getLogger(Method1.class);

    public Method1() {
        super(PROCESS_NAME);
    }

    @Override
    protected Void execute() throws Exception {
        logger.info(message);
        return null;
    }
}

// function/Method2.java
@Component
public class Method2 extends AbstractFunction<Void>{

    private static final String PROCESS_NAME = "メソッド２";

    private final String message = "hello world from method2";

    private final Logger logger = LoggerFactory.getLogger(Method2.class);

    private final DatabaseQueryServicve databaseQueryServicve;

    public Method2(DatabaseQueryServicve databaseQueryServicve) {
        super(PROCESS_NAME);
        this.databaseQueryServicve = databaseQueryServicve;
    }

    @Override
    protected Void execute() throws Exception {
        logger.info(message);
        List<SampleEntity> samples = databaseQueryServicve.getAllSamples();
        logger.info("name column : " + samples.get(0).getName());
        return null;
    }
}

// function/AbstractFunction.java
// 抽象クラスではSupplierを実装しており、Supplierのメソッドであるget関数に処理詳細を定義している
// getメソッドに処理を記載することで開始メッセージなどのログ出力を共通的に定義できるので、
// 継承クラスはそのクラスが行いたい処理のみに集中できる。
public abstract class AbstractFunction<O> implements Supplier<O>{

    private String processName;

    private LocalDateTime processDate;

    private final Logger looger = LoggerFactory.getLogger(AbstractFunction.class);

    public AbstractFunction(final String processName) {
        this.processName = processName;
    }

    public O get() {
        this.processDate = LocalDateTime.now();
        looger.info(processName + " starts at " + processDate.toString());
        try {
            O output = this.execute();
            return output;
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    protected abstract O execute() throws Exception;
}
```

## Gradleのマルチプロジェクト

複数のサブプロジェクトを1つの親プロジェクトにまとめて管理する構成のこと

プロジェクトの依存関係やビルドタスクを一元的に管理でき、各サブプロジェクト間のコード共有やモジュール化が容易

Confluenceにも詳細を記載しているので、確認してみてください。
※リンクは張りませんが、「Gradleのマルチプロジェクト」というタイトルです。

## SpringBoot,AWS SecretsManagerを活用してAWS RDSに接続

AWS SecretsManagerにDBの接続情報を閉じ込めることでデータの秘匿性を高めます

AWS SDK for Javaを活用すればAWS SecretsManagerに接続して認証情報を取得し、

RDSに接続することができますが、今回はもっと簡易的に接続できないか検討しました

aws-advanced-jdbc-wrapperというプラグインを使用することで、

SpringBootのプロパティファイルに設定を行うだけでSpringBootの初期処理の中で自動的に接続情報を取得してくれるというものです。(だと思っています)

[プラグインの詳細](https://github.com/aws/aws-advanced-jdbc-wrapper)
[使用例の記事_SpringBoot活用](https://mazyu36.hatenablog.com/entry/2023/08/01/230016#:~:text=%E6%9C%80%E8%BF%91Java%EF%BC%88Spr)
[使用例の記事_PureJava](https://dev.classmethod.jp/articles/aws-jdbc-driver-for-postgresql-secrets-manager/)

下記はapplication.ymlの設定です

DB周りの処理はSpringのmybatisを使ったシンプルなものなので、記載は割愛します

### aws-advanced-jdbc-wrapperの設定

```yaml
# /resources/application.yml
spring:
  batch:
    job:
      enabled: false
    jdbc:
      initialize-schema: never
  datasource:
    url: jdbc:aws-wrapper:postgresql://si-dev-pgsql153.cayq42jmc6ys.ap-northeast-1.rds.amazonaws.com:5432/sample_db
    driver-class-name: software.amazon.jdbc.Driver
    hikari:
      # 下記にsecretsmanagerの情報を定義することで、プラグインが自動的にSecretsManagerに定義されている
      # 認証情報を取得することができる
      data-source-properties:
        wrapperPlugins: awsSecretsManager
        secretsManagerSecretId: dev/si-dev-pgsql153/super-user
        secretsManagerRegion: ap-northeast-1
      exception-override-class-name: software.amazon.jdbc.util.HikariCPSQLException
  config:
    import:
      - optional:application-module.yml
  main:
    # allow-bean-definition-overriding: true
   lazy-initialization: true
custom:
  app:
    message: "Application.yml default config"
```
