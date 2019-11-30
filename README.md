# Command Line Builder With Phanthom Types

In this project I have designed two simple frameworks to build and use shell commands.

- Command Builder Using generic builder template to build execute and pipe multple commands.
- Builder framework with phanthom types where **every command is type checked for correctness with respect to command's schema/syntax enforcing right usage during the compilation phase using phanthom types**

---

### 1. Generic builder template
 
The following is the type hierarchy for generic template for the builder

```scala
/**
  * Generic builder framework to build any command
  *
  * @tparam C Type representing the implemented command
  */
trait CommandBuilder[C <: CommandType] {
  def command(a: => C): CommandBuilder[C]

  def addOption(o: CommandOption): CommandBuilder[C]

  def build: CommandExecutor[C]

  def pipe: CommandBuilder[C]


}

trait CommandType

trait CommandOption

trait CommandExecutor[C <: CommandType] {

  def project(c: Int): CommandExecutor[C]
  
  /**
  * Can include other processing methods/monads
*/

  def execute()
}
```

Created SCHEMA's for commands like:
- Man
- PS
- Ssh
- Docker
- Grep

Sample usage for building PS using this pattern and accessing specific 
constructs of the result.

```scala
val resultProcessor = new PsCommand().addOption(a()).addOption(u()).addOption(x()).build
```

```
USER               PID  %CPU %MEM      VSZ    RSS   TT  STAT STARTED      TIME COMMAND
mohammedsiddiq   63350 118.4  1.1  6422748  95048   ??  R     8:53PM   0:01.22 /Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/bin/java -Xmx700m -Djava.awt.headless=true -Djava.endorsed.dirs="" -Djdt.compiler.useSingleThread=true -Dpreload.project.path=/Users/mohammedsiddiq/Masters/Fall2019/OOLE/Hw3 -Dpreload.config.path=/Users/mohammedsiddiq/Library/Preferences/IntelliJIdea2018.3/options -Dcompile.parallel=false -Drebuild.on.dependency.change=true -Djava.net.preferIPv4Stack=true -Dio.netty.initialSeedUniquifier=-2991813464524539102 -Dfile.encoding=UTF-8 -Duser.language=en -Duser.country=US -Didea.paths.selector=IntelliJIdea2018.3 -Didea.home.path=/Applications/IntelliJ IDEA.app/Contents -Didea.config.path=/Users/mohammedsiddiq/Library/Preferences/IntelliJIdea2018.3 -Didea.plugins.path=/Users/mohammedsiddiq/Library/Application Support/IntelliJIdea2018.3 -Djps.log.dir=/Users/mohammedsiddiq/Library/Logs/IntelliJIdea2018.3/build-log -Djps.fallback.jdk.home=/Applications/IntelliJ IDEA.app/Contents/jdk/Contents/Home/jre -Djps.fallback.jdk.version=1.8.0_152-release -Dio.netty.noUnsafe=true -Djava.io.tmpdir=/Users/mohammedsiddiq/Library/Caches/IntelliJIdea2018.3/compile-server/hw3_5560cef3/_temp_ -Djps.backward.ref.index.builder=true -Dkotlin.incremental.compilation=true -Dkotlin.daemon.enabled -Dkotlin.daemon.client.alive.path="/var/folders/sk/g608jk411qx6_0jtsfdjpks00000gn/T/kotlin-idea-6093298434353377083-is-running" -classpath /Applications/IntelliJ IDEA.app/Contents/lib/jps-launcher.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/lib/tools.jar:/Applications/IntelliJ IDEA.app/Contents/lib/optimizedFileManager.jar org.jetbrains.jps.cmdline.Launcher /Applications/IntelliJ IDEA.app/Contents/lib/plexus-component-annotations-1.6.jar:/Applications/IntelliJ IDEA.app/Contents/lib/aether-api-1.1.0.jar:/Applications/IntelliJ IDEA.app/Contents/lib/aether-transport-http-1.1.0.jar:/Applications/IntelliJ IDEA.app/Contents/lib/aether-util-1.1.0.jar:/Applications/IntelliJ IDEA.app/Contents/lib/lz4-1.3.0.jar:/Applications/IntelliJ IDEA.app/Contents/lib/httpcore-4.4.10.jar:/Applications/IntelliJ IDEA.app/Contents/lib/guava-25.1-jre.jar:/Applications/IntelliJ IDEA.app/Contents/lib/asm-all-7.0.jar:/Applications/IntelliJ IDEA.app/Contents/lib/util.jar:/Applications/IntelliJ IDEA.app/Contents/lib/platform-api.jar:/Applications/IntelliJ IDEA.app/Contents/lib/plexus-interpolation-1.21.jar:/Applications/IntelliJ IDEA.app/Contents/lib/aether-spi-1.1.0.jar:/Applications/IntelliJ IDEA.app/Contents/lib/protobuf-java-3.4.0.jar:/Applications/IntelliJ IDEA.app/Contents/lib/aether-connector-basic-1.1.0.jar:/Applications/IntelliJ IDEA.app/Contents/lib/jna.jar:/Applications/IntelliJ IDEA.app/Contents/lib/trove4j.jar:/Applications/IntelliJ IDEA.app/Contents/lib/aether-transport-file-1.1.0.jar:/Applications/IntelliJ IDEA.app/Contents/lib/jps-builders.jar:/Applications/IntelliJ IDEA.app/Contents/lib/nanoxml-2.2.3.jar:/Applications/IntelliJ IDEA.app/Contents/lib/netty-transport-4.1.30.Final.jar:/Applications/IntelliJ IDEA.app/Contents/lib/jdom.jar:/Applications/IntelliJ IDEA.app/Contents/lib/netty-codec-4.1.30.Final.jar:/Applications/IntelliJ IDEA.app/Contents/lib/commons-codec-1.10.jar:/Applications/IntelliJ IDEA.app/Contents/lib/forms-1.1-preview.jar:/Applications/IntelliJ IDEA.app/Contents/lib/annotations.jar:/Applications/IntelliJ IDEA.app/Contents/lib/maven-model-3.3.9.jar:/Applications/IntelliJ IDEA.app/Contents/lib/resources_en.jar:/Applications/IntelliJ IDEA.app/Contents/lib/netty-buffer-4.1.30.Final.jar:/Applications/IntelliJ IDEA.app/Contents/lib/javac2.jar:/Applications/IntelliJ IDEA.app/Contents/lib/maven-builder-support-3.3.9.jar:/Applications/IntelliJ IDEA.app/Contents/lib/aether-impl-1.1.0.jar:/Applications/IntelliJ IDEA.app/Contents/lib/jna-platform.jar:/Applications/IntelliJ IDEA.app/Contents/lib/oro-2.0.8.jar:/Applications/IntelliJ IDEA.app/Contents/lib/maven-aether-provider-3.3.9.jar:/Applications/IntelliJ IDEA.app/Contents/lib/jps-model.jar:/Applications/IntelliJ IDEA.app/Contents/lib/maven-model-builder-3.3.9.jar:/Applications/IntelliJ IDEA.app/Contents/lib/commons-lang3-3.4.jar:/Applications/IntelliJ IDEA.app/Contents/lib/plexus-utils-3.0.22.jar:/Applications/IntelliJ IDEA.app/Contents/lib/netty-common-4.1.30.Final.jar:/Applications/IntelliJ IDEA.app/Contents/lib/commons-logging-1.2.jar:/Applications/IntelliJ IDEA.app/Contents/lib/netty-resolver-4.1.30.Final.jar:/Applications/IntelliJ IDEA.app/Contents/lib/jps-builders-6.jar:/Applications/IntelliJ IDEA.app/Contents/lib/httpclient-4.5.6.jar:/Applications/IntelliJ IDEA.app/Contents/lib/maven-artifact-3.3.9.jar:/Applications/IntelliJ IDEA.app/Contents/lib/maven-repository-metadata-3.3.9.jar:/Applications/IntelliJ IDEA.app/Contents/lib/log4j.jar:/Applications/IntelliJ IDEA.app/Contents/lib/aether-dependency-resolver.jar:/Applications/IntelliJ IDEA.app/Contents/lib/slf4j-api-1.7.25.jar:/Applications/IntelliJ IDEA.app/Contents/lib/idea_rt.jar::/Applications/IntelliJ IDEA.app/Contents/lib/gson-2.8.5.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/android/lib/jarutils.jar:/Applications/IntelliJ IDEA.app/Contents/lib/guava-25.1-jre.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/android/lib/common-26.1.2.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/android/lib/manifest-merger-26.1.2.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/android/lib/sdk-common-26.1.2.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/android/lib/builder-model-3.1.2.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/android/lib/builder-test-api-3.1.2.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/android/lib/ddmlib-26.1.2.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/android/lib/repository-26.1.2.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/gradle/lib/gradle-api-4.10.jar:/Applications/IntelliJ IDEA.app/Contents/lib/gson-2.8.5.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/android/lib/jarutils.jar:/Applications/IntelliJ IDEA.app/Contents/lib/guava-25.1-jre.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/android/lib/common-26.1.2.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/android/lib/manifest-merger-26.1.2.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/android/lib/sdk-common-26.1.2.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/android/lib/builder-model-3.1.2.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/android/lib/builder-test-api-3.1.2.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/android/lib/ddmlib-26.1.2.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/android/lib/repository-26.1.2.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/gradle/lib/gradle-api-4.10.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/ant/lib/ant-jps-plugin.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/uiDesigner/lib/jps/ui-designer-jps-plugin.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/IntelliLang/lib/intellilang-jps-plugin.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/Groovy/lib/groovy-jps-plugin.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/Groovy/lib/groovy-rt-constants.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/eclipse/lib/eclipse-jps-plugin.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/eclipse/lib/common-eclipse-util.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/maven/lib/maven-jps-plugin.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/osmorc/lib/osmorc-jps-plugin.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/osmorc/lib/biz.aQute.bndlib-4.0.0.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/osmorc/lib/biz.aQute.repository-4.0.0.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/osmorc/lib/biz.aQute.resolve-4.0.0.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/osmorc/lib/bundlor-all.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/aspectj/lib/aspectj-jps-plugin.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/gradle/lib/gradle-jps-plugin.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/devkit/lib/devkit-jps-plugin.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/JavaEE/lib/javaee-jps-plugin.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/JavaEE/lib/jps/jpa-jps-plugin.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/webSphereIntegration/lib/jps/webSphere-jps-plugin.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/weblogicIntegration/lib/jps/weblogic-jps-plugin.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/android/lib/jps/android-jps-plugin.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/android/lib/android-common.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/android/lib/build-common.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/android/lib/android-rt.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/android/lib/sdklib.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/android/lib/common-26.1.2.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/android/lib/jarutils.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/android/lib/layoutlib-api.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/javaFX/lib/javaFX-jps-plugin.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/javaFX/lib/common-javaFX-plugin.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/Kotlin/lib/jps/kotlin-jps-plugin.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/Kotlin/lib/kotlin-stdlib.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/Kotlin/lib/kotlin-reflect.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/Kotlin/lib/kotlin-plugin.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/Kotlin/lib/android-extensions-ide.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/Kotlin/lib/android-extensions-compiler.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/flex/lib/flex-jps-plugin.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/flex/lib/flex-shared.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/dmServer/lib/dmServer-jps-plugin.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/GwtStudio/lib/gwt-jps-plugin.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/GoogleAppEngine/lib/google-app-engine-jps-plugin.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/GoogleAppEngine/lib/appEngine-runtime.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/Grails/lib/grails-jps-plugin.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/Grails/lib/grails-compiler-patch.jar:/Users/mohammedsiddiq/Library/Application Support/IntelliJIdea2018.3/Scala/lib/scala-library.jar:/Users/mohammedsiddiq/Library/Application Support/IntelliJIdea2018.3/Scala/lib/compiler-shared.jar:/Users/mohammedsiddiq/Library/Application Support/IntelliJIdea2018.3/Scala/lib/jps/nailgun.jar:/Users/mohammedsiddiq/Library/Application Support/IntelliJIdea2018.3/Scala/lib/jps/compiler-jps.jar:/Users/mohammedsiddiq/Library/Application Support/IntelliJIdea2018.3/Scala/lib/jps/sbt-interface.jar:/Users/mohammedsiddiq/Library/Application Support/IntelliJIdea2018.3/Scala/lib/jps/incremental-compiler.jar:/Users/mohammedsiddiq/Library/Application Support/IntelliJIdea2018.3/Scala/lib/scala-library.jar:/Users/mohammedsiddiq/Library/Application Support/IntelliJIdea2018.3/Scala/lib/scala-play-2-jps-plugin.jar:/Users/mohammedsiddiq/Library/Application Support/IntelliJIdea2018.3/Scala/launcher/sbt-launch.jar org.jetbrains.jps.cmdline.BuildMain 127.0.0.1 64041 f46cd3c6-70d5-4361-8954-d70bef7f00c1 /Users/mohammedsiddiq/Library/Caches/IntelliJIdea2018.3/compile-server
mohammedsiddiq   63351 114.1  1.0  7847728  87716   ??  S     8:53PM   0:01.25 /Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/bin/java -javaagent:/Applications/IntelliJ IDEA.app/Contents/lib/idea_rt.jar=57103:/Applications/IntelliJ IDEA.app/Contents/bin -Dfile.encoding=UTF-8 -classpath /Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/charsets.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/deploy.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/ext/cldrdata.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/ext/dnsns.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/ext/jaccess.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/ext/jfxrt.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/ext/localedata.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/ext/nashorn.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/ext/sunec.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/ext/sunjce_provider.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/ext/sunpkcs11.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/ext/zipfs.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/javaws.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/jce.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/jfr.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/jfxswt.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/jsse.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/management-agent.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/plugin.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/resources.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/rt.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/lib/ant-javafx.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/lib/dt.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/lib/javafx-mx.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/lib/jconsole.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/lib/packager.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/lib/sa-jdi.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/lib/tools.jar:/Users/mohammedsiddiq/Masters/Fall2019/OOLE/Hw3/target/scala-2.13/classes:/Users/mohammedsiddiq/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/com/typesafe/config/1.4.0/config-1.4.0.jar:/Users/mohammedsiddiq/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/org/slf4j/slf4j-simple/1.7.5/slf4j-simple-1.7.5.jar:/Users/mohammedsiddiq/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/org/slf4j/slf4j-api/1.7.5/slf4j-api-1.7.5.jar:/Users/mohammedsiddiq/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala-reflect/2.13.1/scala-reflect-2.13.1.jar:/Users/mohammedsiddiq/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala-library/2.13.1/scala-library-2.13.1.jar:/Users/mohammedsiddiq/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/org/scalactic/scalactic_2.13/3.1.0/scalactic_2.13-3.1.0.jar Drivers.Builder
mohammedsiddiq     291  23.2  0.4  8379652  36180   ??  R    Sun11AM   4:09.13 /Applications/PyCharm CE.app/Contents/MacOS/pycharm -psn_0_65552
mohammedsiddiq   55174  19.5 10.1 10917096 849060   ??  S    Wed09PM 128:24.94 /Applications/IntelliJ IDEA.app/Contents/MacOS/idea
root             35029   5.8 22.7  8758864 1902356   ??  S    Mon12PM 247:37.70 /Applications/VMware Fusion.app/Contents/Library/vmware-vmx -E en -s vmx.stdio.keep=TRUE -# product=64;name=VMware Fusion;version=11.0.3;buildnumber=12992109;licensename=VMware Fusion for Mac OS;licenseversion=11.0; -@ duplex=3;msgs=ui -D 4 /Users/mohammedsiddiq/Downloads/cloudera-quickstart-vm-5.13.0-0-vmware 2/cloudera-quickstart-vm-5.13.0-0-vmware.vmx
_windowserver      165   4.8  0.3  7250972  21760   ??  Ss   Sun11AM 161:29.10 /System/Library/PrivateFrameworks/SkyLight.framework/Resources/WindowServer -daemon
mohammedsiddiq   31748   2.5  0.9  9627980  77652   ??  S    Sun11AM 192:23.06 /Applications/Firefox.app/Contents/MacOS/firefox
.....
.....
.....
.....
```

In order to capture the response and project specific columns

```scala
resultProcessor.project(2)
```

```scala
%CPU
118.4
114.1
23.2
19.5
5.8
4.8
2.5
```

---
### 2. Builder framework with Phanthom types
The actual and exhaustive implementation of the framework is done for the following commands:

- cd 
- grep
- ls
- man
- ps
- ssh

The following describes the builder exposed fpr grep command (using phanthom type)

```scala

/**
  * Builder pattern for grep command with its options
  * @param c
  * @tparam Grep
  */
class PGrepCommand[Grep <: PGrepCommand.Grep](c: String = "grep") {

  import PGrepCommand.Grep._

  def pattern(p: String): PGrepCommand[Grep with Pattern] = new PGrepCommand(c.concat(" ").concat(p))

  def filePath(p: String): PGrepCommand[Grep with FilePath] = new PGrepCommand(c.concat(" ").concat(p))

  // -i Options
  def ignoreCase(): PGrepCommand[Grep with IgnoreCase] = new PGrepCommand[Grep with IgnoreCase](c.concat(" ").concat("-i"))

  //-v option
  def invertPattern(): PGrepCommand[Grep with InvertPattern] = new PGrepCommand[Grep with InvertPattern](c.concat(" ".concat("-v")))

  //-n option
  def includeLineNumber(): PGrepCommand[Grep with IncludeLineNumber] = new PGrepCommand[Grep with IncludeLineNumber](c.concat(" ").concat("-n"))

  //-E requalar exp
  def extendRegularExpression(): PGrepCommand[Grep with ExtendedRegularExpression] = new PGrepCommand[Grep with ExtendedRegularExpression](c.concat(" ").concat("-E"))


  def build(implicit ev: Grep <:< FinalCommand): CommandExecutor = {
    CommandExecutor(Seq(c))
  }
}

/**
  * Structural type for grep command
  */
object PGrepCommand {

  sealed trait Grep

  def apply(): PGrepCommand[Grep] = new PGrepCommand()

  object Grep {

    sealed trait Pattern extends Grep

    sealed trait FilePath extends Grep

    sealed trait IgnoreCase extends Grep

    sealed trait InvertPattern extends Grep

    sealed trait IncludeLineNumber extends Grep

    sealed trait ExtendedRegularExpression extends Grep

    type FinalCommand = Grep with Pattern

  }

}

```

Compiler will complain and enforce the type and above schema defined if violated.

For example the following usages will result in the compile time error
```scala

 PGrepCommand().build
//Invalid grep command with filename
 PGrepCommand().filePath("some path").build
 
 //Invalid usage with only options
 PGrepCommand().invertPattern().includeLineNumber().build
 
 //Missing pattern
 PGrepCommand().invertPattern().ignoreCase().includeLineNumber().filePath("").build
```

In all the above usages compiler complains 

```
[error]   PGrepCommand().invertPattern().includeLineNumber().build
[error]                                                      ^
[error] /Users/mohammedsiddiq/Masters/Fall2019/OOLE/Hw3/src/main/scala/Drivers/InvalidUsages.scala:29:80: Cannot prove that executors.PGrepCommand.Grep with executors.PGrepCommand.Grep.InvertPattern with executors.PGrepCommand.Grep.IgnoreCase with executors.PGrepCommand.Grep.IncludeLineNumber with executors.PGrepCommand.Grep.FilePath <:< executors.PGrepCommand.Grep.FinalCommand.
[error]   PGrepCommand().invertPattern().ignoreCase().includeLineNumber().filePath("").build
```

Similarly have constructed the Invalid usages in the Driver package `InvalidUsages.scala` , Uncomment the code and run `sbt clean compile` for  all invalid usages of the commands compiler will complain

```
[error] /Users/mohammedsiddiq/Masters/Fall2019/OOLE/Hw3/src/main/scala/Drivers/InvalidUsages.scala:14:14: value build is not a member of object executors.PLsCommand
[error]   PLsCommand.build.execute()
[error]              ^
[error] /Users/mohammedsiddiq/Masters/Fall2019/OOLE/Hw3/src/main/scala/Drivers/InvalidUsages.scala:17:16: Cannot prove that executors.PcdCommand.Cd <:< executors.PcdCommand.Cd.FinalCommand.
[error]   PcdCommand().build
[error]                ^
[error] /Users/mohammedsiddiq/Masters/Fall2019/OOLE/Hw3/src/main/scala/Drivers/InvalidUsages.scala:21:18: Cannot prove that executors.PGrepCommand.Grep <:< executors.PGrepCommand.Grep.FinalCommand.
[error]   PGrepCommand().build
[error]                  ^
[error] /Users/mohammedsiddiq/Masters/Fall2019/OOLE/Hw3/src/main/scala/Drivers/InvalidUsages.scala:25:40: Cannot prove that executors.PGrepCommand.Grep with executors.PGrepCommand.Grep.FilePath <:< executors.PGrepCommand.Grep.FinalCommand.
[error]   PGrepCommand().filePath("some path").build
[error]                                        ^
[error] /Users/mohammedsiddiq/Masters/Fall2019/OOLE/Hw3/src/main/scala/Drivers/InvalidUsages.scala:27:54: Cannot prove that executors.PGrepCommand.Grep with executors.PGrepCommand.Grep.InvertPattern with executors.PGrepCommand.Grep.IncludeLineNumber <:< executors.PGrepCommand.Grep.FinalCommand.
[error]   PGrepCommand().invertPattern().includeLineNumber().build
[error]                                                      ^
[error] /Users/mohammedsiddiq/Masters/Fall2019/OOLE/Hw3/src/main/scala/Drivers/InvalidUsages.scala:29:80: Cannot prove that executors.PGrepCommand.Grep with executors.PGrepCommand.Grep.InvertPattern with executors.PGrepCommand.Grep.IgnoreCase with executors.PGrepCommand.Grep.IncludeLineNumber with executors.PGrepCommand.Grep.FilePath <:< executors.PGrepCommand.Grep.FinalCommand.
[error]   PGrepCommand().invertPattern().ignoreCase().includeLineNumber().filePath("").build
[error]                                                                                ^
[error] /Users/mohammedsiddiq/Masters/Fall2019/OOLE/Hw3/src/main/scala/Drivers/InvalidUsages.scala:33:17: Cannot prove that executors.PManCommand.Man <:< executors.PManCommand.Man.FinalCommand.
[error]   PManCommand().build
[error]                 ^
[error] /Users/mohammedsiddiq/Masters/Fall2019/OOLE/Hw3/src/main/scala/Drivers/InvalidUsages.scala:37:30: Cannot prove that executors.PSshCommand.Ssh with executors.PSshCommand.Ssh.Password <:< executors.PSshCommand.Ssh.FinalCommand.
[error]   PSshCommand().Password("").build
[error]                              ^
[error] /Users/mohammedsiddiq/Masters/Fall2019/OOLE/Hw3/src/main/scala/Drivers/InvalidUsages.scala:41:30: Cannot prove that executors.PSshCommand.Ssh with executors.PSshCommand.Ssh.UserName <:< executors.PSshCommand.Ssh.FinalCommand.
[error]   PSshCommand().UserName("").build
[error]                              ^
[error] 9 errors found
[error] (Compile / compileIncremental) Compilation failed
[error] Total time: 6 s, completed Nov 29, 2019, 9:10:37 PM
```

### Piping multiple commands

The following is an example usage to pipe multiple commands using this framework
```
man top | grep -n -i options
```

To build and execute the above command 

```scala

PManCommand().forCommand(ConfigReader.manPage).build
.pipe(PGrepCommand().includeLineNumber()
.ignoreCase().pattern("options").build)
.execute()
```

Outputs the Grepped man page of `top` with line numbers having options, ignoring the case since that options is enabled 
in the builder.

```
32:     Options can be specified more than once.  If conflicting options are
260:     fields that are displayed depend on the options that are set.  The pid
326:     command.  To enable the -r option, use it after any -c mode options
```

The above pattern can be used to pipe as many commands as possible with enforced type checking hence the builder will
 let you only build valid individual commands.
 
 `drivers/BuilderSampleUsages.scala` demonstrates the usage of the framework with sample commands
  and their combinations
  `drivers/InvalidUsages.scala` houses code for invalid usage of the command framework. The code is commented, you can uncomment and run to see the compile errors
   
 
 ### Steps to run 
 
 - Clone this project : 
 
 ```
 Git clone <project-url>
 ```
 - For testing 
 ```
 sbt clean compile test
 ```
 - For running sample usages 
 ```
 sbt run
 ```
This will compile and run the test cases post successful compilation and test verification,
the sample usages are executed.

### Snapshot of the response:

Tests: 
```
[pool-23-thread-5-ScalaTest-running-VerifyCommandBuilders] INFO com.mohammedsiddiq.VerifyCommandBuilders - Building CD command
[pool-23-thread-5-ScalaTest-running-VerifyCommandBuilders] INFO com.mohammedsiddiq.VerifyCommandBuilders - Successfully built the commad : cd src/main/resources 
[pool-23-thread-5-ScalaTest-running-VerifyCommandBuilders] INFO com.mohammedsiddiq.VerifyCommandBuilders - Building LS command for path src/main/scala/executors
[pool-23-thread-5-ScalaTest-running-VerifyCommandBuilders] INFO com.mohammedsiddiq.VerifyCommandBuilders - successfully built ls -l -S -a 
[pool-23-thread-5-ScalaTest-running-VerifyCommandBuilders] INFO com.mohammedsiddiq.VerifyCommandBuilders - Grepping the file src/main/scala/executors/PGrepCommand.scala for logger
[pool-23-thread-5-ScalaTest-running-VerifyCommandBuilders] INFO executors.PGrepCommand$ - building grep
[pool-23-thread-5-ScalaTest-running-VerifyCommandBuilders] INFO com.mohammedsiddiq.VerifyCommandBuilders - Building LS command for path src/main/scala/executors
[pool-23-thread-5-ScalaTest-running-VerifyCommandBuilders] INFO executors.PGrepCommand$ - building grep
[pool-23-thread-5-ScalaTest-running-VerifyCommandBuilders] INFO com.mohammedsiddiq.VerifyCommandBuilders - successfully built ls -l -S -a | grep -i C.+d 
[pool-23-thread-5-ScalaTest-running-VerifyCommandBuilders] INFO com.mohammedsiddiq.VerifyCommandBuilders - Getting the manual page for the command top 
[pool-23-thread-5-ScalaTest-running-VerifyCommandBuilders] INFO com.mohammedsiddiq.VerifyCommandBuilders - successfully built man -h -f -W top 
[pool-23-thread-5-ScalaTest-running-VerifyCommandBuilders] INFO com.mohammedsiddiq.VerifyCommandBuilders - Adding Ssh command
[pool-23-thread-5-ScalaTest-running-VerifyCommandBuilders] INFO com.mohammedsiddiq.VerifyCommandBuilders - built command : ssh localhost ls :8080 test 
[info] VerifyCommandBuilders:
[info] Cd Command builder
[info] - should build the right command
[info] Ls
[info] - should build the command to list the files in the path specified
[info] Grep
[info] - should build the command to grep from the file content
[info] Ls piped with grep: ls |grep ...
[info] - should list the files in the path specified and apply grep
[info] Man
[info] - should build the command to List the man page of the document
[info] SSh command
[info] - should be built with its right options
[info] Run completed in 932 milliseconds.
[info] Total number of tests run: 6
[info] Suites: completed 1, aborted 0
[info] Tests: succeeded 6, failed 0, canceled 0, ignored 0, pending 0
[info] All tests passed.
```
 
 Sample Run:
 
 ```
[main] INFO Drivers.BuilderSampleUsages$ - Building the Commands...
[main] INFO Drivers.BuilderSampleUsages$ - LS src/main/scala/executors/PGrepCommand.scala -> 
 src/main/scala/executors/PGrepCommand.scala
[main] INFO Drivers.BuilderSampleUsages$ - Man page for top -> 
TOP(1)                    BSD General Commands Manual                   TOP(1)

NAME
     top -- display and update sorted information about processes

SYNOPSIS
     top [-a | -d | -e | -c mode]
         [-F | -f]
         [-h]
         [-i interval]
         [-l samples]
         [-ncols columns]
         [-o key | -O skey]
         [-R | -r]
         [-S]
         [-s delay-secs]
         [-n nprocs]
         [-stats keys]
         [-pid processid]
         [-user username]
         [-U username]
         [-u]

DESCRIPTION
     The top program periodically displays a sorted list of system processes.
     The default sorting key is pid, but other keys can be used instead.  Var-
     ious output options are available.

OPTIONS
     Command line option specifications are processed from left to right.
     Options can be specified more than once.  If conflicting options are
     specified, later specifications override earlier ones.  This makes it
     viable to create a shell alias for top with preferred defaults specified,
     then override those preferred defaults as desired on the command line.

   ...
   
   main] INFO executors.PGrepCommand$ - building grep
   [main] INFO Drivers.BuilderSampleUsages$ - Cding into src/main/scala/
   [main] INFO Drivers.BuilderSampleUsages$ - Piping cd and LS......
   [main] INFO Drivers.BuilderSampleUsages$ - Cding into src/main/scala/ and LS ->
    README.md
   build.sbt
   project
   src
   target
   [main] INFO Drivers.BuilderSampleUsages$ - piping cd | LS | Grep
   [main] INFO executors.PGrepCommand$ - building grep
   [main] INFO Drivers.BuilderSampleUsages$ - Cd into src/main/scala/ then LS then Grepping for C -> 
    CommandsWithGenericBuilder
   Constants
   [main] INFO Drivers.BuilderSampleUsages$ - Building PS with generic builder
   [main] INFO CommandsWithGenericBuilder.PsCommand - 
   
   [main] INFO CommandsWithGenericBuilder.PsCommand - USER               PID  %CPU %MEM      VSZ    RSS   TT  STAT STARTED      TIME COMMAND
   mohammedsiddiq   64203 108.0  1.2  6425228 102648   ??  R     9:39PM   0:01.57 /Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/bin/java -Xmx700m -Djava.awt.headless=true -Djava.endorsed.dirs="" -Djdt.compiler.useSingleThread=true -Dpreload.project.path=/Users/mohammedsiddiq/Masters/Fall2019/OOLE/Hw3 -Dpreload.config.path=/Users/mohammedsiddiq/Library/Preferences/IntelliJIdea2018.3/options -Dcompile.parallel=false -Drebuild.on.dependency.change=true -Djava.net.preferIPv4Stack=true -Dio.netty.initialSeedUniquifier=-2991813464524539102 -Dfile.encoding=UTF-8 -Duser.language=en -Duser.country=US -Didea.paths.selector=IntelliJIdea2018.3 -Didea.home.path=/Applications/IntelliJ IDEA.app/Contents -Didea.config.path=/Users/mohammedsiddiq/Library/Preferences/IntelliJIdea2018.3 -Didea.plugins.path=/Users/mohammedsiddiq/Library/Application Support/IntelliJIdea2018.3 -Djps.log.dir=/Users/mohammedsiddiq/Library/Logs/IntelliJIdea2018.3/build-log -Djps.fallback.jdk.home=/Applications/IntelliJ IDEA.app/Contents/jdk/Contents/Home/jre -Djps.fallback.jdk.version=1.8.0_152-release -Dio.netty.noUnsafe=true -Djava.io.tmpdir=/Users/mohammedsiddiq/Library/Caches/IntelliJIdea2018.3/compile-server/hw3_5560cef3/_temp_ -Djps.backward.ref.index.builder=true -Dkotlin.incremental.compilation=true -Dkotlin.daemon.enabled -Dkotlin.daemon.client.alive.path="/var/folders/sk/g608jk411qx6_0jtsfdjpks00000gn/T/kotlin-idea-6093298434353377083-is-running" -classpath /Applications/IntelliJ IDEA.app/Contents/lib/jps-launcher.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/lib/tools.jar:/Applications/IntelliJ IDEA.app/Contents/lib/optimizedFileManager.jar org.jetbrains.jps.cmdline.Launcher /Applications/IntelliJ IDEA.app/Contents/lib/plexus-component-annotations-1.6.jar:/Applications/IntelliJ IDEA.app/Contents/lib/aether-api-1.1.0.jar:/Applications/IntelliJ IDEA.app/Contents/lib/aether-transport-http-1.1.0.jar:/Applications/IntelliJ IDEA.app/Contents/lib/aether-util-1.1.0.jar:/Applications/IntelliJ IDEA.app/Contents/lib/lz4-1.3.0.jar:/Applications/IntelliJ IDEA.app/Contents/lib/httpcore-4.4.10.jar:/Applications/IntelliJ IDEA.app/Contents/lib/guava-25.1-jre.jar:/Applications/IntelliJ IDEA.app/Contents/lib/asm-all-7.0.jar:/Applications/IntelliJ IDEA.app/Contents/lib/util.jar:/Applications/IntelliJ IDEA.app/Contents/lib/platform-api.jar:/Applications/IntelliJ IDEA.app/Contents/lib/plexus-interpolation-1.21.jar:/Applications/IntelliJ IDEA.app/Contents/lib/aether-spi-1.1.0.jar:/Applications/IntelliJ IDEA.app/Contents/lib/protobuf-java-3.4.0.jar:/Applications/IntelliJ IDEA.app/Contents/lib/aether-connector-basic-1.1.0.jar:/Applications/IntelliJ IDEA.app/Contents/lib/jna.jar:/Applications/IntelliJ IDEA.app/Contents/lib/trove4j.jar:/Applications/IntelliJ IDEA.app/Contents/lib/aether-transport-file-1.1.0.jar:/Applications/IntelliJ IDEA.app/Contents/lib/jps-builders.jar:/Applications/IntelliJ IDEA.app/Contents/lib/nanoxml-2.2.3.jar:/Applications/IntelliJ IDEA.app/Contents/lib/netty-transport-4.1.30.Final.jar:/Applications/IntelliJ IDEA.app/Contents/lib/jdom.jar:/Applications/IntelliJ IDEA.app/Contents/lib/netty-codec-4.1.30.Final.jar:/Applications/IntelliJ IDEA.app/Contents/lib/commons-codec-1.10.jar:/Applications/IntelliJ IDEA.app/Contents/lib/forms-1.1-preview.jar:/Applications/IntelliJ IDEA.app/Contents/lib/annotations.jar:/Applications/IntelliJ IDEA.app/Contents/lib/maven-model-3.3.9.jar:/Applications/IntelliJ IDEA.app/Contents/lib/resources_en.jar:/Applications/IntelliJ IDEA.app/Contents/lib/netty-buffer-4.1.30.Final.jar:/Applications/IntelliJ IDEA.app/Contents/lib/javac2.jar:/Applications/IntelliJ IDEA.app/Contents/lib/maven-builder-support-3.3.9.jar:/Applications/IntelliJ IDEA.app/Contents/lib/aether-impl-1.1.0.jar:/Applications/IntelliJ IDEA.app/Contents/lib/jna-platform.jar:/Applications/IntelliJ IDEA.app/Contents/lib/oro-2.0.8.jar:/Applications/IntelliJ IDEA.app/Contents/lib/maven-aether-provider-3.3.9.jar:/Applications/IntelliJ IDEA.app/Contents/lib/jps-model.jar:/Applications/IntelliJ IDEA.app/Contents/lib/maven-model-builder-3.3.9.jar:/Applications/IntelliJ IDEA.app/Contents/lib/commons-lang3-3.4.jar:/Applications/IntelliJ IDEA.app/Contents/lib/plexus-utils-3.0.22.jar:/Applications/IntelliJ IDEA.app/Contents/lib/netty-common-4.1.30.Final.jar:/Applications/IntelliJ IDEA.app/Contents/lib/commons-logging-1.2.jar:/Applications/IntelliJ IDEA.app/Contents/lib/netty-resolver-4.1.30.Final.jar:/Applications/IntelliJ IDEA.app/Contents/lib/jps-builders-6.jar:/Applications/IntelliJ IDEA.app/Contents/lib/httpclient-4.5.6.jar:/Applications/IntelliJ IDEA.app/Contents/lib/maven-artifact-3.3.9.jar:/Applications/IntelliJ IDEA.app/Contents/lib/maven-repository-metadata-3.3.9.jar:/Applications/IntelliJ IDEA.app/Contents/lib/log4j.jar:/Applications/IntelliJ IDEA.app/Contents/lib/aether-dependency-resolver.jar:/Applications/IntelliJ IDEA.app/Contents/lib/slf4j-api-1.7.25.jar:/Applications/IntelliJ IDEA.app/Contents/lib/idea_rt.jar::/Applications/IntelliJ IDEA.app/Contents/lib/gson-2.8.5.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/android/lib/jarutils.jar:/Applications/IntelliJ IDEA.app/Contents/lib/guava-25.1-jre.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/android/lib/common-26.1.2.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/android/lib/manifest-merger-26.1.2.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/android/lib/sdk-common-26.1.2.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/android/lib/builder-model-3.1.2.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/android/lib/builder-test-api-3.1.2.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/android/lib/ddmlib-26.1.2.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/android/lib/repository-26.1.2.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/gradle/lib/gradle-api-4.10.jar:/Applications/IntelliJ IDEA.app/Contents/lib/gson-2.8.5.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/android/lib/jarutils.jar:/Applications/IntelliJ IDEA.app/Contents/lib/guava-25.1-jre.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/android/lib/common-26.1.2.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/android/lib/manifest-merger-26.1.2.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/android/lib/sdk-common-26.1.2.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/android/lib/builder-model-3.1.2.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/android/lib/builder-test-api-3.1.2.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/android/lib/ddmlib-26.1.2.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/android/lib/repository-26.1.2.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/gradle/lib/gradle-api-4.10.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/ant/lib/ant-jps-plugin.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/uiDesigner/lib/jps/ui-designer-jps-plugin.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/IntelliLang/lib/intellilang-jps-plugin.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/Groovy/lib/groovy-jps-plugin.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/Groovy/lib/groovy-rt-constants.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/eclipse/lib/eclipse-jps-plugin.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/eclipse/lib/common-eclipse-util.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/maven/lib/maven-jps-plugin.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/osmorc/lib/osmorc-jps-plugin.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/osmorc/lib/biz.aQute.bndlib-4.0.0.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/osmorc/lib/biz.aQute.repository-4.0.0.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/osmorc/lib/biz.aQute.resolve-4.0.0.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/osmorc/lib/bundlor-all.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/aspectj/lib/aspectj-jps-plugin.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/gradle/lib/gradle-jps-plugin.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/devkit/lib/devkit-jps-plugin.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/JavaEE/lib/javaee-jps-plugin.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/JavaEE/lib/jps/jpa-jps-plugin.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/webSphereIntegration/lib/jps/webSphere-jps-plugin.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/weblogicIntegration/lib/jps/weblogic-jps-plugin.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/android/lib/jps/android-jps-plugin.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/android/lib/android-common.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/android/lib/build-common.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/android/lib/android-rt.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/android/lib/sdklib.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/android/lib/common-26.1.2.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/android/lib/jarutils.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/android/lib/layoutlib-api.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/javaFX/lib/javaFX-jps-plugin.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/javaFX/lib/common-javaFX-plugin.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/Kotlin/lib/jps/kotlin-jps-plugin.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/Kotlin/lib/kotlin-stdlib.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/Kotlin/lib/kotlin-reflect.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/Kotlin/lib/kotlin-plugin.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/Kotlin/lib/android-extensions-ide.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/Kotlin/lib/android-extensions-compiler.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/flex/lib/flex-jps-plugin.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/flex/lib/flex-shared.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/dmServer/lib/dmServer-jps-plugin.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/GwtStudio/lib/gwt-jps-plugin.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/GoogleAppEngine/lib/google-app-engine-jps-plugin.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/GoogleAppEngine/lib/appEngine-runtime.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/Grails/lib/grails-jps-plugin.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/Grails/lib/grails-compiler-patch.jar:/Users/mohammedsiddiq/Library/Application Support/IntelliJIdea2018.3/Scala/lib/scala-library.jar:/Users/mohammedsiddiq/Library/Application Support/IntelliJIdea2018.3/Scala/lib/compiler-shared.jar:/Users/mohammedsiddiq/Library/Application Support/IntelliJIdea2018.3/Scala/lib/jps/nailgun.jar:/Users/mohammedsiddiq/Library/Application Support/IntelliJIdea2018.3/Scala/lib/jps/compiler-jps.jar:/Users/mohammedsiddiq/Library/Application Support/IntelliJIdea2018.3/Scala/lib/jps/sbt-interface.jar:/Users/mohammedsiddiq/Library/Application Support/IntelliJIdea2018.3/Scala/lib/jps/incremental-compiler.jar:/Users/mohammedsiddiq/Library/Application Support/IntelliJIdea2018.3/Scala/lib/scala-library.jar:/Users/mohammedsiddiq/Library/Application Support/IntelliJIdea2018.3/Scala/lib/scala-play-2-jps-plugin.jar:/Users/mohammedsiddiq/Library/Application Support/IntelliJIdea2018.3/Scala/launcher/sbt-launch.jar org.jetbrains.jps.cmdline.BuildMain 127.0.0.1 64041 a5f70e42-c34f-4c76-aa64-479796034467 /Users/mohammedsiddiq/Library/Caches/IntelliJIdea2018.3/compile-server
   mohammedsiddiq   64204  62.4  1.1  7848812  88084   ??  S     9:39PM   0:01.20 /Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/bin/java -javaagent:/Applications/IntelliJ IDEA.app/Contents/lib/idea_rt.jar=57289:/Applications/IntelliJ IDEA.app/Contents/bin -Dfile.encoding=UTF-8 -classpath /Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/charsets.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/deploy.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/ext/cldrdata.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/ext/dnsns.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/ext/jaccess.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/ext/jfxrt.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/ext/localedata.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/ext/nashorn.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/ext/sunec.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/ext/sunjce_provider.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/ext/sunpkcs11.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/ext/zipfs.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/javaws.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/jce.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/jfr.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/jfxswt.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/jsse.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/management-agent.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/plugin.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/resources.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/rt.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/lib/ant-javafx.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/lib/dt.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/lib/javafx-mx.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/lib/jconsole.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/lib/packager.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/lib/sa-jdi.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/lib/tools.jar:/Users/mohammedsiddiq/Masters/Fall2019/OOLE/Hw3/target/scala-2.13/classes:/Users/mohammedsiddiq/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/com/typesafe/config/1.4.0/config-1.4.0.jar:/Users/mohammedsiddiq/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/org/slf4j/slf4j-simple/1.7.5/slf4j-simple-1.7.5.jar:/Users/mohammedsiddiq/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/org/slf4j/slf4j-api/1.7.5/slf4j-api-1.7.5.jar:/Users/mohammedsiddiq/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala-reflect/2.13.1/scala-reflect-2.13.1.jar:/Users/mohammedsiddiq/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala-library/2.13.1/scala-library-2.13.1.jar:/Users/mohammedsiddiq/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/org/scalactic/scalactic_2.13/3.1.0/scalactic_2.13-3.1.0.jar Drivers.BuilderSampleUsages
   mohammedsiddiq   55174  48.2  9.3 10907672 782716   ??  S    Wed09PM 159:25.58 /Applications/IntelliJ IDEA.app/Contents/MacOS/idea
   root             35029   9.0 22.8  8758864 1914296   ??  S    Mon12PM 252:25.53 /Applications/VMware Fusion.app/Contents/Library/vmware-vmx -E en -s vmx.stdio.keep=TRUE -# product=64;name=VMware Fusion;version=11.0.3;buildnumber=12992109;licensename=VMware Fusion for Mac OS;licenseversion=11.0; -@ duplex=3;msgs=ui -D 4 /Users/mohammedsiddiq/Downloads/cloudera-quickstart-vm-5.13.0-0-vmware 2/cloudera-quickstart-vm-5.13.0-0-vmware.vmx
   root                79   7.9  0.4  4694272  30376   ??  Rs   Sun11AM  43:35.00 /Library/Application Support/Symantec/Silo/NFM/Daemon/SymDaemon.bundle/Contents/MacOS/SymDaemon
   _windowserver      165   4.8  0.2  7109292  13028   ??  Ss   Sun11AM 164:00.00 /System/Library/PrivateFrameworks/SkyLight.framework/Resources/WindowServer -daemon
   root               108   2.0  0.0  4296516   1316   ??  Ss   Sun11AM   0:03.24 /usr/libexec/taskgated
   _hidd              104   1.6  0.0  4335280   2856   ??  Ss   Sun11AM  32:23.52 /usr/libexec/hidd
   mohammedsiddiq    1287   1.4  0.0  6557368   1148   ??  S    Sun11AM  81:02.87 com.docker.hyperkit -A -u -F vms/0/hyperkit.pid -c 2 -m 2048M -s 0:0,hostbridge -s 31,lpc -s 1:0,virtio-vpnkit,path=vpnkit.eth.sock,uuid=f008f2c7-7c03-4035-af31-18fbf5c8ea5d -U edbb4e9c-1217-4507-82a4-174e5e2bd17f -s 2:0,ahci-hd,/Users/mohammedsiddiq/Library/Containers/com.docker.docker/Data/vms/0/Docker.raw -s 3,virtio-sock,guest_cid=3,path=vms/0,guest_forwards=2376;1525 -s 4,ahci-cd,/Applications/Docker.app/Contents/Resources/linuxkit/docker-desktop.iso -s 5,ahci-cd,vms/0/config.iso -s 6,ahci-cd,/Applications/Docker.app/Contents/Resources/linuxkit/docker.iso -s 7,virtio-rnd -l com1,autopty=vms/0/tty,asl -f bootrom,/Applications/Docker.app/Contents/Resources/uefi/UEFI.fd,,
   root               118   0.4  0.0  4348912   1576   ??  Ss   Sun11AM   0:17.75 /usr/libexec/syspolicyd
   root                78   0.4  0.0  4309156    144   ??  Ss   Sun11AM  25:42.14 /Library/LaunchAgents/ss_conn_service
   mohammedsiddiq    1015   0.3  0.1  4609540   6824   ??  S    Sun11AM   2:20.92 /Applications/Google Drive File Stream.app/Contents/Frameworks/Google Drive File Stream Helper.app/Contents/MacOS/Google Drive File Stream Helper --type=gpu-process --field-trial-handle=1718379636,7999991402377710657,4796235830765265802,131072 --disable-features=NetworkService --no-sandbox --log-file=/Users/mohammedsiddiq/Library/Application Support/Google/DriveFS/Logs/chrome_debug.log --lang=en --gpu-preferences=KAAAAAAAAAAgACAAAQAAAAAAAAAAAGAAAAAAAAAAAAAIAAAAAAAAADgBAAAmAAAAMAEAAAAAAAA4AQAAAAAAAEABAAAAAAAASAEAAAAAAABQAQAAAAAAAFgBAAAAAAAAYAEAAAAAAABoAQAAAAAAAHABAAAAAAAAeAEAAAAAAACAAQAAAAAAAIgBAAAAAAAAkAEAAAAAAACYAQAAAAAAAKABAAAAAAAAqAEAAAAAAACwAQAAAAAAALgBAAAAAAAAwAEAAAAAAADIAQAAAAAAANABAAAAAAAA2AEAAAAAAADgAQAAAAAAAOgBAAAAAAAA8AEAAAAAAAD4AQAAAAAAAAACAAAAAAAACAIAAAAAAAAQAgAAAAAAABgCAAAAAAAAIAIAAAAAAAAoAgAAAAAAADACAAAAAAAAOAIAAAAAAABAAgAAAAAAAEgCAAAAAAAAUAIAAAAAAABYAgAAAAAAABAAAAAAAAAAAAAAAAAAAAAQAAAAAAAAAAAAAAAGAAAAEAAAAAAAAAAAAAAABwAAABAAAAAAAAAAAAAAAAgAAAAQAAAAAAAAAAAAAAAKAAAAEAAAAAAAAAAAAAAACwAAABAAAAAAAAAAAAAAAA0AAAAQAAAAAAAAAAAAAAAOAAAAEAAAAAAAAAABAAAAAAAAABAAAAAAAAAAAQAAAAYAAAAQAAAAAAAAAAEAAAAHAAAAEAAAAAAAAAABAAAACAAAABAAAAAAAAAAAQAAAAoAAAAQAAAAAAAAAAEAAAALAAAAEAAAAAAAAAABAAAADQAAABAAAAAAAAAAAQAAAA4AAAAQAAAAAAAAAAQAAAAAAAAAEAAAAAAAAAAEAAAABgAAABAAAAAAAAAABAAAAAcAAAAQAAAAAAAAAAQAAAAIAAAAEAAAAAAAAAAEAAAACgAAABAAAAAAAAAABAAAAAsAAAAQAAAAAAAAAAQAAAANAAAAEAAAAAAAAAAEAAAADgAAABAAAAAAAAAABgAAAAAAAAAQAAAAAAAAAAYAAAAGAAAAEAAAAAAAAAAGAAAACAAAABAAAAAAAAAABgAAAAoAAAAQAAAAAAAAAAYAAAALAAAAEAAAAAAAAAAGAAAADQAAABAAAAAAAAAABgAAAA4AAAAQAAAAAAAAAAcAAAAAAAAAEAAAAAAAAAAHAAAABgAAABAAAAAAAAAABwAAAAgAAAAQAAAAAAAAAAcAAAAKAAAAEAAAAAAAAAAHAAAACwAAABAAAAAAAAAABwAAAA0AAAAQAAAAAAAAAAcAAAAOAAAA --log-file=/Users/mohammedsiddiq/Library/Application Support/Google/DriveFS/Logs/chrome_debug.log --service-request-channel-token=2612367623620796907
   mohammedsiddiq   57532   0.3  0.6  8395272  52004   ??  S    Thu06PM  24:51.58 /Applications/Firefox.app/Contents/MacOS/plugin-container.app/Contents/MacOS/plugin-container -childID 48 -isForBrowser -prefsLen 10769 -prefMapSize 221488 -sbStartup -sbAppPath /Applications/Firefox.app -sbLevel 3 -sbAllowAudio -sbAllowWindowServer -parentBuildID 20191030021342 -greomni /Applications/Firefox.app/Contents/Resources/omni.ja -appomni /Applications/Firefox.app/Contents/Resources/browser/omni.ja -appdir /Applications/Firefox.app/Contents/Resources/browser -profile /Users/mohammedsiddiq/Library/Application Support/Firefox/Profiles/8ljlv12s.default-1555184292518 31748 gecko-crash-server-pipe.31748 org.mozilla.machname.2012163486 tab
   root               214   0.3  0.1  5965232   9712   ??  Ss   Sun11AM   6:08.37 /System/Library/Frameworks/CoreServices.framework/Frameworks/Metadata.framework/Versions/A/Support/mds_stores
   root               110   0.3  0.0  4297284   1472   ??  Ss   Sun11AM   1:24.53 /usr/sbin/notifyd
   root                 1   0.3  0.1  4315984   5184   ??  Ss   Sun11AM  16:28.61 /sbin/launchd
   root                66   0.2  0.1  4412884   7688   ??  Ss   Sun11AM   3:15.74 /System/Library/Frameworks/CoreServices.framework/Frameworks/Metadata.framework/Support/mds
   ........
   
  
   main] INFO Drivers.BuilderSampleUsages$ - PS result: 
    CommandsWithGenericBuilder.PsResultProcessor@3108bc 
   [main] INFO CommandsWithGenericBuilder.PsResultProcessor - 
   %CPU
   108.0
   62.4
   48.2
   9.0
   7.9
   4.8
   2.0
   1.6
   1.4
   0.4
   0.4
   0.3
   0.3
   0.3
   
   [main] INFO Drivers.BuilderSampleUsages$ - Projecting 3rd column 
    CommandsWithGenericBuilder.PsResultProcessor@64729b1e
   [main] INFO CommandsWithGenericBuilder.PsResultProcessor - 
   PID
   64203
   64204
   55174
   35029
   79
   165
   108
   104
   1287
   118
   78
   1015
   57532
   214
   110
   1
   66
   77
   59071
   31768
   74
   61805
   59
   57958
   964
   62712
   89
   64056
   64055
   64053

[main] INFO Drivers.BuilderSampleUsages$ - Piping Man page with grep....
[main] INFO executors.PGrepCommand$ - building grep
[main] INFO Drivers.BuilderSampleUsages$ - Grepping top page for options pattern found 28:     ious output options are available.
32:     Options can be specified more than once.  If conflicting options are
260:     fields that are displayed depend on the options that are set.  The pid
326:     command.  To enable the -r option, use it after any -c mode options.

   

```