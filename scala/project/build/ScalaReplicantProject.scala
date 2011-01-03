import sbt._

class ReplicantProject(info: ProjectInfo) extends DefaultProject(info) {

  val scalaToolsSnapshots = ScalaToolsSnapshots
  val scalatest = "org.scalatest" % "scalatest" % "1.2"          withSources()
  val junit     = "junit"         % "junit" % "4.8.1"   % "test" withSources()

}