import sbt._
import Keys._

object Build extends Build {
  lazy val project = Project("root", file(".")).settings(
    name := "scala-benchmarking-template",
    organization := "com.example",
    version := "1.0.0-SNAPSHOT",
    scalaVersion := "2.11.7",
    libraryDependencies ++= Seq(
      "com.google.caliper" % "caliper" % "1.0-beta-2",
      "com.sun.jersey" % "jersey-bundle" % "1.9.1"
    ),
    resolvers += "sonatypeSnapshots" at "http://oss.sonatype.org/content/repositories/snapshots",
    fork in run := true
  )
}
