val scala3Version = "3.2.0"

lazy val root = project
  .in(file("."))
  .settings(
    name := "daytime-gnome-theme",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,

    libraryDependencies += "org.scalameta" %% "munit" % "0.7.29" % Test,
    libraryDependencies += "com.typesafe.play" %% "play-json" % "2.10.0-RC6",
    libraryDependencies += "com.softwaremill.sttp.client3" %% "core" % "3.8.0",
    assembly / mainClass := Some("Main"),
    assembly / assemblyJarName := "daytime-gnome-theme.jar"
)

assemblyMergeStrategy in assembly := {
 case PathList("META-INF", _*) => MergeStrategy.discard
 case _                        => MergeStrategy.first
}