// Copyright 2011 Kiel Hodges
package sample2

import replicant._
import experiment1._

class MockWidgetRepository extends MockGenericRepository[Widget] with WidgetRepository { mock =>
  trait Stub extends super.Stub {
    val findByPartNumber: ArgList[String, Result[Widget]] = Replicant.with1ArgList(mock, "findByPartNumber")
  }
  override val method = new Stub() {}
  def findByPartNumber(partNumber: String): Widget = method.findByPartNumber(partNumber).response
}
