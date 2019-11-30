package executors

import sys.process._

/**
  * SSH command builder using phantom types
  *
  * @param c The string representing the command
  * @tparam Man The phantom type
  */

class PSshCommand[Ssh <: PSshCommand.Ssh](c: String = "ssh") {

  import PSshCommand.Ssh._


  def UserName(uName: String): PSshCommand[Ssh with UserName] = new PSshCommand(c.concat(" ").concat(uName))

  // -h options : print help message for the command
  def Host(endpoint: String): PSshCommand[Ssh with Host] = new PSshCommand(c.concat(" ").concat(endpoint).concat(" ls "))

  //-f option : to get whatIS information
  def Password(pwd: String): PSshCommand[Ssh with Password] = new PSshCommand(c.concat(" ").concat("-f"))

  //-W option: TO print the fileNames
  def Port(p: Int): PSshCommand[Ssh with Port] = new PSshCommand(c.concat(":").concat(p.toString))


  def build(implicit ev: Ssh <:< FinalCommand): CommandExecutor = {

//    println("Executing command : " , c)
//    println(c !!)
    CommandExecutor(Seq(c))
  }
}


/**
  * Implementing structural type to build and evaluate Man
  */
object PSshCommand {

  sealed trait Ssh


  def apply(): PSshCommand[Ssh] = new PSshCommand[Ssh]()

  object Ssh {

    sealed trait UserName extends Ssh

    sealed trait Host extends Ssh

    sealed trait Password extends Ssh

    sealed trait Port extends Ssh

    type FinalCommand = Ssh with Host

  }

}