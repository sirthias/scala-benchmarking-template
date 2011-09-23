// set the name of the project
name := "scala-benchmarking-template"

organization := "com.example"

version := "1.0.0-SNAPSHOT"

// set the Scala version used for the project
scalaVersion := "2.8.1"

// add compile dependencies on a some dispatch modules
libraryDependencies ++= {
    Seq(
        "com.google.code.java-allocation-instrumenter" % "java-allocation-instrumenter" % "2.0",
        "com.google.code.caliper" % "caliper" % "1.0-SNAPSHOT",
        "com.google.code.gson" % "gson" % "1.7.1"
    )
}

// add a sequence of maven-style repositories
resolvers ++= Seq(
  "sonatypeSnapshots" at "http://oss.sonatype.org/content/repositories/snapshots"
)

// enable forking in run
fork in run := true

// keep everything in lib_managed
retrieveManaged := true

// make this an sbt plugin
sbtPlugin := true
