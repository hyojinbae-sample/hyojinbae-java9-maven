# mavenを利用して、java9のモジュールを作ってみる
hyojinbae-java9-maven
- https://www.journaldev.com/13106/java-9-modules
- https://github.com/cfdobber/maven-java9-jigsaw/
# 今まで通りのmaven multi projectを作ってみる
## pom.xml
下記を追加する

```
    <properties>
        <maven.compiler.source>1.9</maven.compiler.source>
        <maven.compiler.target>1.9</maven.compiler.target>
        <maven.compiler.release>9</maven.compiler.release>
    </properties>
```
Java9でやっても、特に問題なく動く。

# java9のモジュールを作ってみる
## pom.xml
上記の設定がないとIDEがうまいことやってくれないので注意

IntelliJのmodule dependencyグラフなど便利
[Support for Java 9 in IntelliJ IDEA 2017.2](https://blog.jetbrains.com/idea/2017/07/support-for-java-9-in-intellij-idea-2017-2/)

## ソースコード作成
- module-info.javaは、各プロジェクトのsrc/main/java直下(intellijの場合popメニューもある)
- モジュール名はユニークに（artifactIdでいいはず？）
ユニークな名前とmodule descriptor(module-info.java)が必要
※配置場所は、モジュールと同じ名前のフォルダ直下


- java9のモジュールにした場合は、module-info.javaにて明示的に利用するモジュールを定義する必要がある
  - 指定しない場合、importできない
  - まだjava9のモジュールでないjarを利用する場合は、automatic module?(file名からモジュール名が作られる)を利用できる
    - http://openjdk.java.net/projects/jigsaw/spec/sotms/#automatic-modules

# まとめ
- java9のモジュールではない場合は、今まで通り使える
- java9のモジュールにした場合は、他のjarを利用するためにrequiresが必要
- requiredsする方法は、他のjarがモジュールであるかどうかで少し異なる