// Copyright 2011 Kiel Hodges
package replicant

trait Responder[ArgTuple, Result] {
  
  def update(call: Call, response: () => Result): Unit
  def apply(call: Call): Either[UnknownResponseException, () => Result]
  
}
