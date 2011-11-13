// Copyright 2011 Kiel Hodges
package sample2

import replicant._
import experiment1._

class MockGenericRepository[Subject: ResponseFallback] extends XMockController[GenericRepository[Subject]] { self =>

  class TestDouble extends GenericRepository[Subject] {
    def store(subject: Subject)     { self.store(subject).response }
    def findById(id: Long): Subject = self.findById(id).response
  }

  val testDouble: GenericRepository[Subject] = new TestDouble
  
  val store    = method("store", testDouble.store _)
  val findById = method("findById", testDouble.findById _)
  
}
