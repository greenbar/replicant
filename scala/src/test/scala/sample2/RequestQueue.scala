// Copyright 2011 Kiel Hodges
package sample2

trait RequestQueue {
  def nextRequest: Option[Request]
}

