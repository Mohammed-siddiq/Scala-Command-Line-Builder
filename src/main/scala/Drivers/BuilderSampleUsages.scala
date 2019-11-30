package Drivers

import CommandsWithGenericBuilder.{PsCommand, a, u, x}
import Constants.ConfigReader
import executors.{PGrepCommand, PLsCommand, PManCommand, PcdCommand}
import org.slf4j.LoggerFactory
import sys.process._

/**
  * Driver that demonstrates the usage of the command framework with sample commands and their combinations using piping
  */
object BuilderSampleUsages extends App {

  val logger = LoggerFactory.getLogger(BuilderSampleUsages.getClass)


  logger.info("Building the Commands...")

  val command = "ps" #| "grep zsh"


  val result = PLsCommand().path(ConfigReader.grepFile).build.execute()

  logger.info("LS {} -> \n {}", ConfigReader.grepFile, result.get)


  val manPage = PManCommand().forCommand(ConfigReader.manPage).build.execute()

  logger.info("Man page for {} -> \n{} ", ConfigReader.manPage, manPage.get)


  val greppedResult = PGrepCommand().pattern(ConfigReader.grepFilePattern).filePath(ConfigReader.grepFile).build.execute()


  val cdResult = PcdCommand().path(ConfigReader.cdPath).build.execute()

  logger.info("Cding into {}", ConfigReader.cdPath)

  logger.info("Piping cd and LS......")

  val cdLs = PcdCommand().path(ConfigReader.cdPath).build.pipe(PLsCommand().build).execute()

  logger.info("Cding into {} and LS ->\n {}", ConfigReader.cdPath, cdLs.get)


  logger.info("piping cd | LS | Grep")

  val pipeResult = PcdCommand().path(ConfigReader.cdPath).build.pipe(PLsCommand().path(ConfigReader.cdPath).build).pipe(PGrepCommand().pattern("C").build).execute()

  logger.info("Cd into {} then LS then Grepping for {} -> \n {}", ConfigReader.cdPath, "C", pipeResult.get)


  logger.info("Building PS with generic builder")

  val resultProcessor = new PsCommand().addOption(a()).addOption(u()).addOption(x()).build

  logger.info("PS result: \n {} ", resultProcessor)

  logger.info("Projecting 3rd column \n {}", resultProcessor.project(2))

  logger.info("Projecting 2nd column \n {} ", resultProcessor.project(1))


  logger.info("Piping Man page with grep....")

  val greppedPattern = PManCommand().forCommand(ConfigReader.manPage).build.pipe(PGrepCommand().includeLineNumber().ignoreCase().pattern("options").build).execute()


  logger.info("Grepping {} page for {} pattern found {}", ConfigReader.manPage, "options", greppedPattern.get)


  ////
  //  val res = command !!
  ////
  //  println(res)
  //
  //  val executor = new SshCommand().addOption().addOption().addOption().addOption().build
  //
  //  executor.execute()

  //  new ManCommand().addOption(Page("ls")).addOption(Header()).build

  //  val resultProcessor = new PsCommand().addOption(a()).addOption(u()).addOption(x()).build
  //
  //  println(resultProcessor.project(2))
  //
  //
  //  val s = "Some random string"


  //   PManCommand().forCommand("ls").build

  //  PSshCommand().Host("localhost").build
  //
  //  new DockerCommand().addOption(image()).build
  //
  //
  //  val a = Map("x"->2, "y" -> 3)
  //
  //  a.filter()
  //
  //  PLsCommand().build
  //
  //
  //  Option


  //  def fun1[A,B,C] (f1: A=>B, f2: B=>C):A=>C = {
  //    a:A => f2(f1(a))
  //  }
  //
  //
  //  val r = fun1[String,Int,Int](s=>s.length,x=>x*10)
  //
  //  print(r("qwerty"))


  //  val as = Seq("ps", "grep zsh")

  //  PManCommand().forCommand("ls").build.pipe(PGrepCommand().pattern("options").build).execute()

  //  val result = PcdCommand().path("executors").build.execute()

  // val result =  PLsCommand().build.execute()
  //  val result = PcdCommand().path("qq").build.execute()


  //  val res = PGrepCommand().pattern(ConfigReader.grepFilePattern).filePath(ConfigReader.grepFile).build.execute()
  //
  //  println(res)

}
