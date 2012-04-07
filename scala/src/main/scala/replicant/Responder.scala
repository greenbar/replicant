// Copyright 2011 Kiel Hodges
package replicant

import support._

trait Responder[Result] {
  
  def update(call: Call, response: () => Result): Unit
  def apply(call: Call): Response[Result]
  def assertExpectationsMet: Unit
  
}
