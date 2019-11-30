package CommandsWithGenericBuilder

import builder.{CommandBuilder, CommandExecutor, CommandOption, CommandType}
import sys.process._

sealed trait Docker extends CommandType

sealed trait DockerOption extends CommandOption


case class image(image: Option[String] = None) extends DockerOption

case class volume() extends DockerOption

class DockerCommand(s: String = "docker") extends CommandBuilder[Docker]  {
  override def command(a: => Docker): CommandBuilder[Docker] = ???

  override def addOption(o: CommandOption): CommandBuilder[Docker] = new DockerCommand(this.updateOption(o))


  override def pipe: CommandBuilder[Docker] = ???

  def updateOption(option: CommandOption): String = {

    option match {
      case i: image => this.s.concat(" ").concat("image ls ").concat(i.image.getOrElse(""))
      case v: volume => this.s.concat(" volume ls")
      //      case username: UserName => this.s.concat(" -l ").concat(username.uname)
    }
  }

  override def build: CommandExecutor[Docker] = ???
}

