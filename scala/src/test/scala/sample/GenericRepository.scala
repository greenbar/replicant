// Copyright 2011 Kiel Hodges
package sample

trait GenericRepository[Subject] {
  def store(subject: Subject): Unit
  def findById(id: Long): Subject
}
