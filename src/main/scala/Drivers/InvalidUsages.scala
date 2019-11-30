//package Drivers
//
//import Constants.ConfigReader
//import executors._
//
//
///**
//  * Uncomment this file will show how the command builder framework throw compile errors
//  * Note: Uncommenting will and running sbt compile will throw the errors that violate the command pattern usage
//  */
//object InvalidUsages {
//
//  //Invalid LS build
//  PLsCommand.build.execute()
//
//  //Invalid Cd according to implemented framwork since CDing without a path
//  PcdCommand().build
//
//  //Invalid grep command without pattern
//
//  PGrepCommand().build
//
//  //Invalid grep command with filename
//
//  PGrepCommand().filePath("some path").build
//
//  PGrepCommand().invertPattern().includeLineNumber().build
//
//  PGrepCommand().invertPattern().ignoreCase().includeLineNumber().filePath("").build
//
//  //Invalid Man command without the page name
//
//  PManCommand().build
//
//  //Invalid SSH command with only password
//
//  PSshCommand().Password("").build
//
//  //Invalid Ssh command with only username
//
//  PSshCommand().UserName("").build
//
//  //Invalid ssh command with all options and no hosts
//
//  PSshCommand().UserName("").Password("").Host("").Port(11).build
//
//
//
//
//}
