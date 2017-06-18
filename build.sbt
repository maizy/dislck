name := "dislck"
organization := "ru.maizy"

val scalaV = "2.12.2"
val dislckVersion = "0.0.1-alpha"

val scalacOpts = Seq(
  "-target:jvm-1.8",
  "-encoding", "UTF-8",
  "-deprecation",
  "-unchecked",
  "-feature",
  "-explaintypes",
  "-Xfatal-warnings",
  "-Xlint:_",
  "-Ywarn-dead-code",
  "-Ywarn-inaccessible",
  "-Ywarn-infer-any",
  "-Ywarn-nullary-override",
  "-Ywarn-nullary-unit",
  "-Ywarn-numeric-widen",
  "-Ywarn-unused",
  "-Ywarn-value-discard",
  "-Ywarn-unused-import"
)

scalaVersion := scalaV

lazy val commonSettings = Seq(
  organization := "ru.maizy",
  version := dislckVersion,
  scalaVersion := scalaV,
  maintainer := "Nikita Kovaliov <nikita@maizy.ru>",
  scalacOptions ++= scalacOpts,
  scalacOptions in (Compile, console) := scalacOpts
)

lazy val commonDependencies = Seq(
  libraryDependencies ++= Seq(
    "org.scalatest" %% "scalatest" % "3.0.1" % "test"
  )
)

lazy val httpClientDependencies = Seq(
  libraryDependencies ++= Seq(
    "net.databinder.dispatch" %% "dispatch-core" % "0.13.0"
  )
)

lazy val cliDependencies = Seq(
  libraryDependencies ++= Seq(
    "com.github.scopt" %% "scopt" % "3.6.0"
  )
)

lazy val jsonDependencies = Seq(
  libraryDependencies ++= Seq(
    "com.github.fomkin" %% "pushka-json" % "0.8.0"
  ),
  addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)
)

lazy val slackClient = project
  .in(file("slack-client"))
  .settings(commonSettings, commonDependencies, httpClientDependencies, jsonDependencies)

lazy val macosService = project
  .in(file("macos-service"))
  .settings(commonSettings, commonDependencies)

lazy val dislckApp = project
  .in(file("dislck-app"))
  .settings(commonSettings, commonDependencies, cliDependencies)
  .dependsOn(slackClient)
  .dependsOn(macosService)