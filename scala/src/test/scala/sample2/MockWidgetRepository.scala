// Copyright 2011 Kiel Hodges
package sample2

import replicant._
import experiment1._

class MockWidgetRepository extends MockGenericRepository[Widget] { self =>
  
  override val testDouble: WidgetRepository = new TestDouble with WidgetRepository {
    def findByPartNumber(partNumber: String): Widget = self.findByPartNumber(partNumber).response
  }

  val findByPartNumber = method("findByPartNumber", testDouble.findByPartNumber _)
  
}
