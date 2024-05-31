ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.4.2"

lazy val root = (project in file("."))
  .settings(
    name := "scala-functional-programming-cooking-recipe-management",
    libraryDependencies ++= Seq(
      "org.typelevel"     %% "cats-effect" % "3.5.4",
      "org.apache.commons" % "commons-csv" % "1.11.0",
      "co.fs2"            %% "fs2-core"    % "3.10.2",
      "org.scalatest"     %% "scalatest"   % "3.2.18" % Test
    )
  )
