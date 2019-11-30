name := "Hw3"

version := "0.1"

scalaVersion := "2.13.1"

scalacOptions += "-language:postfixOps"

libraryDependencies ++= Seq("com.typesafe" % "config" % "1.4.0",
  "org.scalatest" %% "scalatest" % "3.1.0" % "test", "org.scalactic" %% "scalactic" % "3.1.0", "org.slf4j" % "slf4j-api" % "1.7.5",
  "org.slf4j" % "slf4j-simple" % "1.7.5")