import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

  val appName         = "play-with-bootstrap"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    "com.github.seratch" %% "scalikejdbc"             % "1.3.2",
    "com.github.seratch" %% "scalikejdbc-play-plugin" % "1.3.2"
  )

 val main = PlayProject(appName, appVersion, appDependencies, mainLang = SCALA).settings(
    externalResolvers ~= (_.filter(_.name != "Scala-Tools Maven2 Repository"))
  )

}
