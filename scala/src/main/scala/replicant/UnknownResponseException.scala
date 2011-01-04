// Copyright 2011 Kiel Hodges
package replicant

class UnknownResponseException(message: String) extends Exception(message) {

  override def equals(other: Any) = other match {
    case that: UnknownResponseException => this.getMessage == that.getMessage
    case _ => false
  }
  
  override def hashCode = getMessage.hashCode

}