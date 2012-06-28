logLevel := Level.Warn

resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

addSbtPlugin("play" % "sbt-plugin" % "2.0.2")

addSbtPlugin("com.github.mpeltonen" % "sbt-idea" % "1.0.0")

// Don't forget adding your JDBC driver
libraryDependencies += "com.h2database" % "h2" % "[1.3,)"

addSbtPlugin("com.github.seratch" %% "scalikejdbc-mapper-generator" % "1.3.2")

