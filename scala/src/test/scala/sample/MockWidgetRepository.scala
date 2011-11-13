// Copyright 2011 Kiel Hodges
package sample

import replicant._

class MockWidgetRepository extends MockGenericRepository[Widget] { controller =>

  override val mock: WidgetRepository = new BaseSubject with WidgetRepository {
    def findByPartNumber(partNumber: String): Widget = controller.findByPartNumber(partNumber)
  }
    
  val findByPartNumber = method("findByPartNumber", mock.findByPartNumber _)

}
