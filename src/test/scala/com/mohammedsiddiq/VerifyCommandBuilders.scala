package com.mohammedsiddiq

import Constants.ConfigReader
import executors._
import org.scalatest.FlatSpec
import org.slf4j.LoggerFactory

class VerifyCommandBuilders extends FlatSpec {


  val logger = LoggerFactory.getLogger(this.getClass)


  "Cd Command builder " should "build the right command" in {

    logger.info("Building CD command")

    val path = "src/main/resources"

    val cmd = "cd " + path
    val result = PcdCommand().path(path).build

    logger.info("Successfully built the commad : {} ", result.c(0))

    assert(result.c(0) == cmd)

  }

  "Ls" should "build the command to list the files in the path specified" in {

    logger.info("Building LS command for path {}", ConfigReader.lsPath)

    val res = PLsCommand().inLongFormat().sortFilesBySize().listAll().build

    val cmd = "ls -l -S -a"
    logger.info("successfully built {} ", res.c(0))
    assert(res.c(0) == cmd)


  }


  "Grep" should "build the command to grep from the file content" in {


    logger.info("Grepping the file {} for {}", ConfigReader.grepFile, ConfigReader.grepFilePattern)

    val res = PGrepCommand().ignoreCase().includeLineNumber().invertPattern().pattern(ConfigReader.grepFilePattern).filePath(ConfigReader.grepFile).build

    val cmd = "grep -i -n -v " + ConfigReader.grepFilePattern + " " + ConfigReader.grepFile
    assert(res.c(0) == cmd)


  }


  "Ls piped with grep: ls |grep ..." should "list the files in the path specified and apply grep" in {

    logger.info("Building LS command for path {}", ConfigReader.lsPath)

    val res = PLsCommand().inLongFormat().sortFilesBySize().listAll().build.pipe(PGrepCommand().ignoreCase().pattern("C.+d").build)

    val cmd = "ls -l -S -a | grep -i C.+d"
    logger.info("successfully built {} ", res.c(0))
    assert(res.c(0) == cmd)


  }

  "Man" should "build the command to List the man page of the document" in {

    logger.info("Getting the manual page for the command {} ", ConfigReader.manPage)
    val res = PManCommand().withHelpMessage().withWhatIsInfo().withFileNames().forCommand(ConfigReader.manPage).build

    logger.info("successfully built {} ", res.c(0))

    val cmd = "man -h -f -W " + ConfigReader.manPage

    assert(res.c(0).equals(cmd))

  }

  "SSh command" should "be built with its right options" in {

    logger.info("Adding Ssh command")

    val rcmd = PSshCommand().Host("localhost").Port(8080).UserName("test").build

    logger.info("built command : {} ", rcmd.c(0))

    assert(rcmd.c(0).length!=0)
    assert(rcmd.c(0).equals("ssh localhost ls :8080 test"))
  }


}
