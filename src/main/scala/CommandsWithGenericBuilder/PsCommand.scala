package CommandsWithGenericBuilder

import builder.{CommandBuilder, CommandExecutor, CommandOption, CommandType}
import sys.process._

sealed trait PS extends CommandType

sealed trait PSOption extends CommandOption



case class a() extends PSOption
case class u() extends PSOption
case class x() extends PSOption

//case class UserName(uname: String) extends ManOption
//
//case class password(pwd: String) extends ManOption
//
//case class Port(p: String) extends ManOption


class PsCommand(s: String = "ps",  result:String = null) extends CommandBuilder[PS]{

  override def command(a: => PS): CommandBuilder[PS] = ???

  override def addOption(o: CommandOption): CommandBuilder[PS] = new PsCommand(this.updateOption(o))


  override def pipe: CommandBuilder[PS] = ???

  def updateOption(option: CommandOption): String = {

    option match {
      case a: a => this.s.concat(" ").concat("a")
      case u: u => this.s.concat("u")
      case x: x => this.s.concat("x")
      //      case username: UserName => this.s.concat(" -l ").concat(username.uname)
    }
  }

  override def build: CommandExecutor[PS] = {
    val response = this.s !!

    println(response)

    new PsResultProcessor(Some(response))
  }
}

class PsResultProcessor(result: Option[String] = None) extends CommandExecutor[PS] {
  override def project(c: Int): CommandExecutor[PS] = {
    result match {
      case Some(x) => {
        val res = x.split("\n").map(l => l.trim().replaceAll(" +", " ")).map(l => l.split(" ")).map(a => a(c))
        println(res.foldLeft("")((i,j) => i.concat("\n").concat(j)))
        new PsResultProcessor(Some(res.foldLeft("")((i,j) => i.concat("\n").concat(j))))
      }
      case None => new PsResultProcessor()

    }


  }

  override def execute(): Unit = ???
}


