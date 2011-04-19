import sbt._
import Process._

class Project(info: ProjectInfo) extends DefaultProject(info) {
  
  // -------------------------------------------------------------------------------------------------------------------
  // All repositories *must* go here! See ModuleConfigurations below.
  // -------------------------------------------------------------------------------------------------------------------
  object Repositories {
    // e.g. val akkaRepo = MavenRepository("Akka Repository", "http://akka.io/repository")
  }
  
  // -------------------------------------------------------------------------------------------------------------------
  // ModuleConfigurations
  // Every dependency that cannot be resolved from the built-in repositories (Maven Central and Scala Tools Releases)
  // must be resolved from a ModuleConfiguration. This will result in a significant acceleration of the update action.
  // Therefore, if repositories are defined, this must happen as def, not as val.
  // -------------------------------------------------------------------------------------------------------------------
  import Repositories._
  // e.g. val sprayModuleConfig = ModuleConfiguration("cc.spray", ScalaToolsSnapshots)

  // -------------------------------------------------------------------------------------------------------------------
  // Dependencies
  // -------------------------------------------------------------------------------------------------------------------
  // e.g. val spray               = "cc.spray" %% "spray" % "0.6.0-SNAPSHOT" % "compile" withSources()
  
  // required tweak, if not present caliper will not see the right classpath and die with a ConfigurationException
  override def fork = forkRun("-cp" :: (runClasspath +++ buildLibraryJar).absString :: Nil)
}
