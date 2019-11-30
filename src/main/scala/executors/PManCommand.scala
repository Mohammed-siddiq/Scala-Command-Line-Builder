package executors

import sys.process._

/**
  * Man command builder using phantom types
  *
  * @param c The string representing the command
  * @tparam Man The phantom type
  */

class PManCommand[Man <: PManCommand.Man](c: String = "man") {

  import PManCommand.Man._


  def forCommand(page: String): PManCommand[Man with ForCommand] = new PManCommand(c.concat(" ").concat(page))

  // -h options : print help message for the command
  def withHelpMessage(): PManCommand[Man with HelpOption] = new PManCommand(c.concat(" ").concat("-h"))

  //-f option : to get whatIS information
  def withWhatIsInfo(): PManCommand[Man with WhatIsOption] = new PManCommand(c.concat(" ").concat("-f"))

  //-W option: TO print the fileNames
  def withFileNames(): PManCommand[Man with FileNames] = new PManCommand(c.concat(" ").concat("-W"))


  def build(implicit ev: Man <:< FinalCommand): CommandExecutor = {

//    println(c !!)
    CommandExecutor(Seq(c))
  }
}


/**
  * Implementing structural type to build and evaluate Man
  */
object PManCommand {

  sealed trait Man


  def apply(): PManCommand[Man] = new PManCommand[Man]()

  object Man {

    sealed trait ForCommand extends Man

    sealed trait HelpOption extends Man

    sealed trait WhatIsOption extends Man

    sealed trait FileNames extends Man

    type FinalCommand = Man with ForCommand

  }

}
