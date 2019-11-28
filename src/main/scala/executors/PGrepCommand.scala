package executors

class PGrepCommand[Grep <: PGrepCommand.Grep](c: String = "grep") {

  import PGrepCommand.Grep._

  def pattern(p: String): PGrepCommand[Grep with Pattern] = new PGrepCommand(c.concat(" ").concat(p))

  def filePath(p: String): PGrepCommand[Grep with FilePath] = new PGrepCommand(c.concat(" ").concat(p))

  def ignoreCase(): PGrepCommand[Grep with IgnoreCase] = new PGrepCommand[Grep with IgnoreCase](c.concat(" ").concat("-i"))

  def invertPattern(): PGrepCommand[Grep with InvertPattern] = new PGrepCommand[Grep with InvertPattern](c.concat(" ".concat("-v")))

  def includeLineNumber(): PGrepCommand[Grep with IncludeLineNumber] = new PGrepCommand[Grep with IncludeLineNumber](c.concat(" ").concat("-n"))

  def extendRegularExpression(): PGrepCommand[Grep with ExtendedRegularExpression] = new PGrepCommand[Grep with ExtendedRegularExpression](c.concat(" ").concat("-E"))


  def build(implicit ev: Grep <:< FinalCommand): CommandExecutor = {

    CommandExecutor(Seq(c))
  }
}


object PGrepCommand {

  sealed trait Grep

  def apply(): PGrepCommand[Grep] = new PGrepCommand()

  object Grep {

    sealed trait Pattern extends Grep

    sealed trait FilePath extends Grep

    sealed trait IgnoreCase extends Grep

    sealed trait InvertPattern extends Grep

    sealed trait IncludeLineNumber extends Grep

    sealed trait ExtendedRegularExpression extends Grep

    type FinalCommand = Grep with Pattern

  }

}
