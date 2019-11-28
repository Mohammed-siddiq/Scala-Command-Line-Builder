package executors

import scala.concurrent.Future
import sys.process._

class PLsCommand[Ls <: PLsCommand.Ls](c: String = "ls") {

  import PLsCommand.Ls._

  def fileName(page: String): PLsCommand[Ls with FileName] = new PLsCommand(c.concat(" ").concat(page))

  // -a      Include directory entries whose names begin with a dot (.).
  def listAll(): PLsCommand[Ls with AllOption] = new PLsCommand(c.concat(" ").concat("-a"))

  //-l (The lowercase letter ``ell''.)  List in long format.
  def inLongFormat(): PLsCommand[Ls with LongFormatOption] = new PLsCommand(c.concat(" ").concat("-l"))

  //-S      Sort files by size
  def sortFilesBySize(): PLsCommand[Ls with SortOption] = new PLsCommand(c.concat(" ").concat("-S"))


  def build(implicit ev: Ls <:< FinalCommand): CommandExecutor = {

    lazy val result = c !!

    print(result)

    CommandExecutor(Seq(c))



  }
}


/**
  * Implementing structural type to build and evaluate Man
  */
object PLsCommand {

  sealed trait Ls


  def apply(): PLsCommand[Ls] = new PLsCommand[Ls]()

  object Ls {

    sealed trait FileName extends Ls

    sealed trait AllOption extends Ls

    sealed trait LongFormatOption extends Ls

    sealed trait SortOption extends Ls

    type FinalCommand = Ls

  }

}