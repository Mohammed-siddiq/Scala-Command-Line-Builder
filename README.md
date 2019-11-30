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
   _hidd              104   1.6  0.0  4335280   2856   ??  Ss   Sun11AM  32:23.52 /usr/libexec/hidd
   mohammedsiddiq    1287   1.4  0.0  6557368   1148   ??  S    Sun11AM  81:02.87 com.docker.hyperkit -A -u -F vms/0/hyperkit.pid -c 2 -m 2048M -s 0:0,hostbridge -s 31,lpc -s 1:0,virtio-vpnkit,path=vpnkit.eth.sock,uuid=f008f2c7-7c03-4035-af31-18fbf5c8ea5d -U edbb4e9c-1217-4507-82a4-174e5e2bd17f -s 2:0,ahci-hd,/Users/mohammedsiddiq/Library/Containers/com.docker.docker/Data/vms/0/Docker.raw -s 3,virtio-sock,guest_cid=3,path=vms/0,guest_forwards=2376;1525 -s 4,ahci-cd,/Applications/Docker.app/Contents/Resources/linuxkit/docker-desktop.iso -s 5,ahci-cd,vms/0/config.iso -s 6,ahci-cd,/Applications/Docker.app/Contents/Resources/linuxkit/docker.iso -s 7,virtio-rnd -l com1,autopty=vms/0/tty,asl -f bootrom,/Applications/Docker.app/Contents/Resources/uefi/UEFI.fd,,
   root               118   0.4  0.0  4348912   1576   ??  Ss   Sun11AM   0:17.75 /usr/libexec/syspolicyd
   root                78   0.4  0.0  4309156    144   ??  Ss   Sun11AM  25:42.14 /Library/LaunchAgents/ss_conn_service
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

