import sbt._
import Keys._

object MyBuild extends Build {
  lazy val project = Project("root", file(".")) settings(
    organization := "com.example",
    name := "scala-benchmarking-template",
    version := "1.0.0-SNAPSHOT",
    scalaVersion := "2.9.1",
    libraryDependencies ++= Seq(
        "com.google.code.java-allocation-instrumenter" % "java-allocation-instrumenter" % "2.0",
        "com.google.code.gson" % "gson" % "1.7.1"
    ),
    resolvers += "sonatypeSnapshots" at "http://oss.sonatype.org/content/repositories/snapshots",
    // enable forking in run
    fork in run := true,

    // custom kludge to get caliper to see the right classpath

    // define the onLoad hook
    onLoad in Global <<= (onLoad in Global) ?? identity[State],
    {
      // attribute key to prevent circular onLoad hook
      val key = AttributeKey[Boolean]("loaded")
      val f = (s: State) => {
        val loaded: Boolean = s get key getOrElse false
        if (!loaded) {
          var cpString: String = ""
          // get the runtime classpath
          Project.evaluateTask(fullClasspath.in(Runtime), s) match {
	    // make a colon-delimited string of the classpath
            case Some(Value(cp)) => cpString = cp.files.mkString(":")
	    // probably should handle an error here, but not sure you can
	    //  ever get here with a working sbt
            case _ => Nil
            }
          val extracted: Extracted = Project.extract(s)
          // return a state with loaded = true and javaOptions set correctly
          extracted.append(Seq(javaOptions in run ++= Seq("-cp", cpString)),
			   s.put(key, true))
        } else {
          // return the state, unmodified
          s
        }
      }
      onLoad in Global ~= (f compose _)
    })
}
