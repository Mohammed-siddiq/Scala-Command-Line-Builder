package executors

import CommandsWithGenericBuilder._

import sys.process._

object Builder extends App {

    val command = "ps" #| "grep zsh"
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

  PManCommand().forCommand("ls").build.pipe(PGrepCommand().pattern("options").build).execute()

}

