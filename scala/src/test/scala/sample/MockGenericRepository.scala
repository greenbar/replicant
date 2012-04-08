// Copyright 2011 Kiel Hodges
package sample

import replicant._

class MockGenericRepository[Subject: ResponseFallback] extends MockMinder[GenericRepository[Subject]] { minder =>
  
  protected class BaseSubject {
    def store(subject: Subject) = minder.store(subject)
    def findById(id: Long)  = minder.findById(id)
  }
  
  val mock: GenericRepository[Subject] = new BaseSubject with GenericRepository[Subject]

  val store    = method("store",    mock.store _)
  val findById = method("findById", mock.findById _)

}
