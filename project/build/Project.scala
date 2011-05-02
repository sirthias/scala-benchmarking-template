import sbt._
import Process._

class Project(info: ProjectInfo) extends DefaultProject(info) {
  
  // -------------------------------------------------------------------------------------------------------------------
  // All repositories *must* go here! See ModuleConfigurations below.
  // -------------------------------------------------------------------------------------------------------------------
  object Repositories {
    lazy val SonatypeSnapshotsRepo = MavenRepository("Sonatype Snapshots", "http://oss.sonatype.org/content/repositories/snapshots")
  }
  
  // -------------------------------------------------------------------------------------------------------------------
  // ModuleConfigurations
  // Every dependency that cannot be resolved from the built-in repositories (Maven Central and Scala Tools Releases)
  // must be resolved from a ModuleConfiguration. This will result in a significant acceleration of the update action.
  // Therefore, if repositories are defined, this must happen as def, not as val.
  // -------------------------------------------------------------------------------------------------------------------
  import Repositories._
  val instrumenterModuleConfig = ModuleConfiguration("com.google.code.java-allocation-instrumenter", DefaultMavenRepository)
  val caliperModuleConfig      = ModuleConfiguration("com.google.code.caliper", SonatypeSnapshotsRepo)
  val gsonModuleConfig         = ModuleConfiguration("com.google.code.gson", SonatypeSnapshotsRepo)

  // -------------------------------------------------------------------------------------------------------------------
  // Dependencies
  // -------------------------------------------------------------------------------------------------------------------
  val instrumenter = "com.google.code.java-allocation-instrumenter" % "java-allocation-instrumenter" % "2.0"
  val caliper      = "com.google.code.caliper" % "caliper" % "1.0-SNAPSHOT"

  // required tweak, if not present caliper will not see the right classpath and die with a ConfigurationException
  override def fork = forkRun("-cp" :: (runClasspath +++ buildLibraryJar).absString :: Nil)
}
