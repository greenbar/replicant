// Copyright 2011 Kiel Hodges
package sample

import replicant._

class MockGenericRepository[Subject: ResponseFallback] extends GenericRepository[Subject] { 
  mock =>
  trait Stub {
    val store    = Mocker[Subject, Unit](mock, "store")
    val findById = Mocker[Long, Subject](mock, "findById")
  }
  val method = new Stub {}
  def store(subject: Subject) = method.store(subject)
  def findById(id: Long)  = method.findById(id)
}
