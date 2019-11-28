package CommandsWithGenericBuilder

import builder.{CommandBuilder, CommandExecutor, CommandOption, CommandType}

import sys.process._


sealed trait Man extends CommandType

sealed trait ManOption extends CommandOption


case class Page(command: String) extends ManOption

case class Header() extends ManOption

//case class UserName(uname: String) extends ManOption
//
//case class password(pwd: String) extends ManOption
//
//case class Port(p: String) extends ManOption


class ManCommand(s: String = "man") extends CommandBuilder[Man]{
  override def command(a: => Man): CommandBuilder[Man] = ???

  override def addOption(o: CommandOption): CommandBuilder[Man] = new ManCommand(this.updateOption(o))


  override def pipe: CommandBuilder[Man] = ???

  def updateOption(option: CommandOption): String = {

    option match {
      case page: Page => this.s.concat(" ").concat(page.command)
      case header: Header => this.s.concat(" -h")
      //      case username: UserName => this.s.concat(" -l ").concat(username.uname)
    }
  }

  override def build: CommandExecutor[Man] = ???

}
