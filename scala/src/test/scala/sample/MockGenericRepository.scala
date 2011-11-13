// Copyright 2011 Kiel Hodges
package sample

import replicant._

class MockGenericRepository[Subject: ResponseFallback] extends MockController[GenericRepository[Subject]] { controller =>
  
  protected class BaseSubject {
    def store(subject: Subject) = controller.store(subject)
    def findById(id: Long)  = controller.findById(id)
  }
  
  val mock: GenericRepository[Subject] = new BaseSubject with GenericRepository[Subject]

  val store    = method("store",    mock.store _)
  val findById = method("findById", mock.findById _)

}
