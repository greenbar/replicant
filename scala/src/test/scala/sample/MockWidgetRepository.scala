// Copyright 2011 Kiel Hodges
package sample

import replicant._

class MockWidgetRepository extends MockGenericRepository[Widget] with WidgetRepository { mock =>
  trait Stub extends super.Stub {
    val findByPartNumber = Mocker[String, Widget](mock, "findByPartNumber")
  }
  override val method = new Stub() {}
  def findByPartNumber(partNumber: String): Widget = null
}
