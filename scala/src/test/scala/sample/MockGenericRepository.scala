// Copyright 2011 Kiel Hodges
package sample

import replicant._

class MockGenericRepository[Subject: ResponseFallback] extends MockController[GenericRepository[Subject]] { self =>
  
  protected class BaseSubject {
    def store(subject: Subject) = self.store(subject)
    def findById(id: Long)  = self.findById(id)
  }
  
  val mock: GenericRepository[Subject] = new BaseSubject with GenericRepository[Subject]

  val store    = method("store",    mock.store _)
  val findById = method("findById", mock.findById _)

}
