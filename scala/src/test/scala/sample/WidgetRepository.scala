// Copyright 2011 Kiel Hodges
package sample

trait WidgetRepository extends GenericRepository[Widget] {
  def findByPartNumber(partNumber: String): Widget
}
