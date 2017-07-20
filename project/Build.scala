import sbt._
import Keys._

object Build extends Build {

  lazy val project = Project("root", file(".")).settings(

    // basics
    name := "scala-benchmarking-template",
    organization := "com.example",
    version := "1.0.0-SNAPSHOT",
    scalaVersion := "2.9.2",

    // dependencies
    libraryDependencies ++= Seq(
        "com.google.caliper" % "caliper" % "0.5-rc1"
    ),
    resolvers += "sonatypeSnapshots" at "http://oss.sonatype.org/content/repositories/snapshots",

    // enable forking in run
    fork in run := true,

    // we need to add the runtime classpath as a "-cp" argument to the `javaOptions in run`, otherwise caliper
    // will not see the right classpath and die with a ConfigurationException
    javaOptions in run ++= Seq("-cp",
      Attributed.data((fullClasspath in Runtime).value).mkString(":"))
  )
}
