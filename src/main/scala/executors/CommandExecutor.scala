package executors

import sys.process._

/**
  * Command executor used by all the commands to execute the bult command
  *
  * @param c
  */
case class CommandExecutor(c: Seq[String]) {

  def pipe(ce: CommandExecutor): CommandExecutor = CommandExecutor(Seq(c(0) + " | " + ce.c(0)))

  def execute(): Option[String] = {
    val s = Seq("/bin/sh", "-c") ++: c
    val result = s.!!.trim
    //    print(result)
    Option(result)
  }
}
