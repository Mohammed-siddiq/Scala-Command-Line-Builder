package builder


/**
  * Generic builder framework to build any command
  *
  * @tparam C Type representing the implemented command
  */
trait CommandBuilder[C <: CommandType] {
  def command(a: => C): CommandBuilder[C]

  def addOption(o: CommandOption): CommandBuilder[C]

  def build: CommandExecutor[C]

  def pipe: CommandBuilder[C]


}

trait CommandType

trait CommandOption

trait CommandExecutor[C <: CommandType] {

  def project(c: Int): CommandExecutor[C]

  def execute()
}