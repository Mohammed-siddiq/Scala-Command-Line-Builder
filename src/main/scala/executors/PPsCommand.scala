package executors

import sys.process._

class PPsCommand[Ps <: PPsCommand.Ps](c: String = "man"){

  import PManCommand.Man._


  def forCommand(page: String): PManCommand[Ps with ForCommand] = new PManCommand(c.concat(" ").concat(page))

  // -h options : print help message for the command
  def withHelpMessage(): PManCommand[Ps with HelpOption] = new PManCommand(c.concat(" ").concat("-h"))

  //-f option : to get whatIS information
  def withWhatIsInfo(): PManCommand[Ps with WhatIsOption] = new PManCommand(c.concat(" ").concat("-f"))

  //-W option: TO print the fileNames
  def withFileNames(): PManCommand[Ps with FileNames] = new PManCommand(c.concat(" ").concat("-W"))


  def build(implicit ev: Ps <:< FinalCommand): CommandExecutor = {

    println(c !!)
    CommandExecutor(Seq(c))
  }
}


/**
  * Implementing structural type to build and evaluate Man
  */
object PPsCommand {

  sealed trait Ps


  def apply(): PPsCommand[Ps.ForCommand] = new PPsCommand[Ps.ForCommand]()

  object Ps {

    sealed trait ForCommand extends Ps

    sealed trait HelpOption extends Ps

    sealed trait WhatIsOption extends Ps

    sealed trait FileNames extends Ps

    type FinalCommand = Ps with ForCommand

  }

}