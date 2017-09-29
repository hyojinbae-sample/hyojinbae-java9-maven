# java9モジュールに対する疑問
- 前のバージョンとの互換性
  - javaのバージョンは9に上げたいけど、モジュール機能は別に要らないと思っている場合など
- mavenとの相性
  - mavenのdependency管理機能と微妙に被っているんじゃない？
  - mavenのマルチプロジェクトは今まで通り使える？
 
# まずはjava9のモジュールの簡単紹介
- https://www.journaldev.com/13106/java-9-modules
- https://www.slideshare.net/rcuprak/preparing-for-java-9-modules-upload
- http://www.oracle.com/webfolder/technetwork/jp/javamagazine/Java-JF16-Java9Modules.pdf

## publicがpublicすぎる問題
![](https://image.slidesharecdn.com/preparingforjava9modulesupload-170620090845/95/preparing-for-java-9-modules-upload-18-1024.jpg?cb=1497949799)

publicなタイプであっても、モジュールをまたがって参照するには、module-info.javaにて定義が必要。

## モジュールの宣言(module-info.java作成)
最上位ディレクトリーに格納する。
### 構成要素
- ユニークな名前
- requires(このモジュールが利用するDependencies)
- exports（このモジュールが公開するパッケージ）
### module-info.javaの書き方
![](https://image.slidesharecdn.com/preparingforjava9modulesupload-170620090845/95/preparing-for-java-9-modules-upload-20-638.jpg?cb=1497949799)

### module-info.java実例
![](https://4.bp.blogspot.com/-r5sITT_wYPI/WPQ4Rtruz0I/AAAAAAAAEfo/0DhTsUQoaVw3pYNdeKZz6ZNDydxiPaJKwCLcB/s1600/BasicModuleGraph1.png) 

```java
module com.opengamma.util {
  requires org.joda.beans;  // モジュール名を書く（パッケージ名を書くのではない)
  requires com.google.guava;

  exports com.opengamma.util;  // パッケージ名を書く(モジュール名を書くのではない）
}
```

他のモジュールに定義されているタイプ（クラス・インタフェース）を参照するには

- publicなタイプであり
- requiresにて定義が必要であり
- 該当のタイプが属するパッケージが、該当モジュールにてexportsされている

というすべての条件を満たす必要があります。
※importするにはclasspathを通す必要があるのは今までと変わらない
※参照循環は禁止になった

もっと多くの例が下記のサイトに紹介されています。
[Java 9 modules - JPMS basics](http://blog.joda.org/2017/04/java-9-modules-jpms-basics.html)

- http://blog.joda.org/2017/04/java-9-modules-jpms-basics.html

# 他のモジュールを利用する(参照)
下記のように、参照元と参照先のモジュール種別によって可視性が変わってきます。

![](https://image.slidesharecdn.com/java9migrationjavaland2017-170329080118/95/migrating-to-java-9-modules-35-638.jpg?cb=1490775663)
![](https://image.slidesharecdn.com/java9migrationjavaland2017-170329080118/95/migrating-to-java-9-modules-36-638.jpg?cb=1490775663)
![](https://image.slidesharecdn.com/java9migrationjavaland2017-170329080118/95/migrating-to-java-9-modules-37-638.jpg?cb=1490775663)

可視性を理解するために、モジュールの種別を簡単に説明します。

## JARとモジュールとの関係
- Modular JAR
  - ルートディレクトリに、Module Descriptor（module-info.class）が含まれている
- Non-modular JAR
  - モジュール化されていない(module-info.javaが含まれていない)JAR
  
詳細は [JAR File Specification](http://download.java.net/java/jdk9/docs/specs/jar/jar.html)

## クラスパスとモジュールパス
java9ではクラスパスに加えて、モジュールパスというのが存在します。
※同時に使うこともできる

- modular JARを
  - モジュールパスに通した場合は、explicit module
  - クラスパスに通した場合は、unnamed module
- non-module JARを
  - モジュールパスに通した場合は、automatic module
  - クラスパスに通した場合は、unnamed module
  
## モジュールの種別
- explicit module(named module?)
  - modular JARをモジュールパスに通している
  - platform module(JDKに含まれているモジュール)
  - application module(我々が作成したモジュール)
  - exportするpackageはmodule descriptor定義通りである
- automatic module
  - non-modular JARをモジュールパスに通している
  - モジュールの名前は自動で付く（[automatic module のモジュール名の自動決定ロジック](https://qiita.com/opengl-8080/items/1007c2b2543c2fe0d7d5#automatic-module-%E3%81%AE%E3%83%A2%E3%82%B8%E3%83%A5%E3%83%BC%E3%83%AB%E5%90%8D%E3%81%AE%E8%87%AA%E5%8B%95%E6%B1%BA%E5%AE%9A%E3%83%AD%E3%82%B8%E3%83%83%E3%82%AF)）
  - すべてのpackageがexportされ、module descriptorのrequiresによって参照できる
- unnamed module
  - クラスパスに通している
  - ALL-UNNAMEDという名前が付く？
  - すべてのpackageがexportされるが、module descriptorのrequiredに定義できない？

※結論を言うと、explicit moduleからunnamed moduleを参照することはできない。他のすべてのパターンでは参照できる。

# mavenのマルチプロジェクト機能を使って、java9のモジュールを複数作ってみる
- https://github.com/cfdobber/maven-java9-jigsaw/

## pom.xml
下記を追加する

```
    <properties>
        <maven.compiler.source>1.9</maven.compiler.source>
        <maven.compiler.target>1.9</maven.compiler.target>
        <maven.compiler.release>9</maven.compiler.release>
    </properties>
```
※IDEでjava9機能がうまくいかない気がしたら、上記の設定が漏れていないかを確認

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
いろいろ長い話になったのでまとめると

- モジュールパスに含まれているModular JARの中では、クラスパスに通しているUnnamedモジュールが参照できない
  - つまり、クラスパスに通していたJarをモジュールパスに通せば、参照できるようになる？
- java9のモジュールではない場合は、今まで通り使える
- java9のモジュールにした場合は、他のjarを利用するためにrequiresが必要
- requiredsする方法は、他のjarがモジュールであるかどうかで少し異なる

# その他
## クラスパス・モジュールパスの通し方
クラスパスに通してコンパイルする例(今まで通り)

```
javac -cp xx\xx\sample-module.jar xxx/Sample.java
```

モジュールパスを通してコンパイルする例

```
javac -p xx\xx\sample-module.jar xxx/Sample.java
```
## より詳しい機能
- transitive, static
- open
- jlink : 必要最低限のモジュールだけを集めてJavaランタイムを作成し、配布先にJREがインストールされていなくてもアプリの起動ができる。

https://qiita.com/opengl-8080/items/1007c2b2543c2fe0d7d5#automatic-module-%E3%81%AE%E3%83%A2%E3%82%B8%E3%83%A5%E3%83%BC%E3%83%AB%E5%90%8D%E3%81%AE%E8%87%AA%E5%8B%95%E6%B1%BA%E5%AE%9A%E3%83%AD%E3%82%B8%E3%83%83%E3%82%AF

## intellijでmodulepathを指定する方法
![](https://cdn-images-1.medium.com/max/1600/1*mvRcVe4X2MqtUO_dIX-TIA.png)
[](https://medium.com/@andrey_cheptsov/using-jdk-9-with-project-jigsaw-in-intellij-idea-95e495c825aa)