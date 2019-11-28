package executors

import sys.process._

case class CommandExecutor(c: Seq[String]) {

  def pipe(ce: CommandExecutor): CommandExecutor = CommandExecutor(Seq(c(0) + "|" + ce.c(0)))

  def execute(): String = {
    val s = Seq("/bin/sh", "-c") ++: c
    val result = s.!!
    print(result)
    result
  }
}
