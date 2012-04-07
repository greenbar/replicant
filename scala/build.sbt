name := "replicant"

version := "0.1.0"

organization := "kiel"

scalaVersion := "2.9.1"


EclipseKeys.withSource := true

retrieveManaged := true

libraryDependencies += "org.scalatest" % "scalatest_2.9.0" % "1.4.1"          withSources()

libraryDependencies += "junit"         % "junit" % "4.8.1"           % "test" withSources()

