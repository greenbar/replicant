import sbt._

class ReplicantProject(info: ProjectInfo) extends DefaultProject(info) {

  val scalaToolsSnapshots = ScalaToolsSnapshots
  val scalatest = "org.scalatest" %% "scalatest" % "latest.release" withSources()
  val junit     = "junit"         %  "junit" % "4.8.1"   % "test"   withSources()

}