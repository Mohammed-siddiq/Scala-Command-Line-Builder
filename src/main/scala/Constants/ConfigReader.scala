package Constants

import com.typesafe.config.ConfigFactory

/**
  * Config reader and provider
  */
object ConfigReader {


  val configReader = ConfigFactory.load("application.conf")

  val lsPath = configReader.getString("LS_PATH")

  val manPage = configReader.getString("MAN_PAGE")

  val grepFile = configReader.getString("GREP_FILE_PATH")

  val grepFilePattern = configReader.getString("GREP_PATTERN")

  val cdPath = configReader.getString("CD_PATH")


}
