// Copyright 2011 Kiel Hodges
package replicant

trait Responder[ArgTuple, Result] {
  
  def update(call: Call[ArgTuple], response: () => Result): Unit
  def apply(call: Call[ArgTuple]): Either[UnknownResponseException, () => Result]
  
}
