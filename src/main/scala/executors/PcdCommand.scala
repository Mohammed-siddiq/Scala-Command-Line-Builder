package executors

import sys.process._

class PcdCommand[Cd <: PcdCommand.Cd](c: String = "cd") {

  import PcdCommand.Cd._

  def path(p: String): PcdCommand[Cd with Destination] = new PcdCommand(c.concat(" ").concat(p))


  def build(implicit ev: Cd <:< FinalCommand): CommandExecutor = {

    //    lazy val res = c !!
    //    println(c !!)
    //    CommandExecutor(c)


  CommandExecutor(Seq(c))
  }

}


object PcdCommand {

  sealed trait Cd

  def apply(): PcdCommand[Cd] = new PcdCommand()

  object Cd {

    sealed trait Destination extends Cd

    type FinalCommand = Destination

  }

}