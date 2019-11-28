package CommandsWithGenericBuilder

import sys.process._
import builder.{CommandBuilder, CommandExecutor, CommandOption, CommandType}


sealed trait Ssh extends CommandType

sealed trait SshOption extends CommandOption


case class Host(host: String) extends SshOption

case class UserName(uname: String) extends SshOption

case class password(pwd: String) extends SshOption

case class Port(p: String) extends SshOption


class SshCommand(s: String = "ssh") extends CommandBuilder[Ssh] {
  override def command(a: => Ssh): CommandBuilder[Ssh] = ???

  override def addOption(o: CommandOption): CommandBuilder[Ssh] = new SshCommand(this.updateOption(o))


  override def pipe: CommandBuilder[Ssh] = ???

  def updateOption(option: CommandOption): String = {

    option match {
      case host: Host => this.s.concat(" ").concat(host.host)
      case username: UserName => this.s.concat(" -l ").concat(username.uname)
    }
  }

  override def build: CommandExecutor[Ssh] = {
    val response = this.s !!

    println(response)

    new SshResultProcessor(Some(response))
  }
}


class SshResultProcessor(result: Option[String] = None) extends CommandExecutor[Ssh] {
  override def project(c: Int): CommandExecutor[Ssh] = {
    result match {
      case Some(x) => {
        val res = x.split("\n").map(l => l.trim().replaceAll(" +", " ")).map(l => l.split(" ")).map(a => a(c))

        new SshResultProcessor(Some(res.toString))
      }
      case None => new SshResultProcessor

    }


  }

  override def execute(): Unit = ???
}