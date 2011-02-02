// Copyright 2011 Kiel Hodges
package sample2

trait GenericRepository[Subject] {
  def store(subject: Subject): Unit
  def findById(id: Long): Subject
}
