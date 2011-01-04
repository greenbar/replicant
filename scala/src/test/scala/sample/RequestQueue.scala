// Copyright 2011 Kiel Hodges
package sample

trait RequestQueue {
  def nextRequest: Option[Request]
}

