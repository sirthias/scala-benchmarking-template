package org.example

import sbt._
import Keys._

object BenchPlugin extends Plugin
{
  
  // add the new run command "my-run" and classpath task "get-classpath"
  //  to the project
  override lazy val settings = Seq(
      commands ++= Seq(myRun),
      cpTask,
      libraryDependencies ++= Seq(
	"com.example" % "scala-benchmarking-template_2.8.1" % "1.0.0-SNAPSHOT"
    ),
      resolvers += "sonatypeSnapshots" at "http://oss.sonatype.org/content/repositories/snapshots",
      fork := true
  )
  
  // define the classpath task key
  val cpath = TaskKey[String]("get-classpath",
			      "Returns the runtime classpath")
    
  // define the classpath task
  val cpTask = cpath <<= fullClasspath.in(Runtime) map { (cp: Classpath) =>
    cp.files.mkString(":")
						      }
  
  // define the new run command
  def myRun = Command.command("my-run") { state => {
    // get the results of the classpath task
    val result: Option[Result[String]] = Project.evaluateTask(cpath, state)
    
    // match based on the results of the task
    result match {
      case None => { 
	println("key isn't defined")
	state.fail
      }
      case Some(Inc(inc)) => { 
	println("error: " + inc)
	// return a failure
	state.fail
      }
      case Some(Value(v)) => { 
	
	// extract the string from the task results
	val classpath: String = v
	
	// don't know how to set a setting in a command, so just build
	//  the command that sets it:
	// javaOptions in run ++= Seq("-cp", classpath)
	val cmd: String = "set javaOptions in run ++= Seq(\"-cp\", \"" +
	classpath +
	"\")"
	
	// return a new state that has the setting command and the run cmd
	//   prepended to the list of remaining commands
	state.copy(
	  remainingCommands = Seq (cmd, "run") ++ state.remainingCommands
	)
      }
    }
  }
 }
}
