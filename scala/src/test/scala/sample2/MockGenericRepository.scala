// Copyright 2011 Kiel Hodges
package sample2

import replicant._
import experiment1._

class MockGenericRepository[Subject](implicit fallback: ResponseFallback[Subject]) 
extends GenericRepository[Subject] { mock =>
  trait Stub {
    val store:    ArgList[Subject, Result[Unit]] = Replicant.with1ArgList(mock, "store")
    val findById: ArgList[Long, Result[Subject]] = Replicant.with1ArgList(mock, "findById")
  }
  val method = new Stub {}
  def store(subject: Subject) = method.store(subject).response
  def findById(id: Long)  = method.findById(id).response
}
