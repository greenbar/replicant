// Copyright 2011 Kiel Hodges
package replicant

trait Responder[Result] {
  
  def update(call: Call, response: () => Result): Unit
  def responseFor(call: Call): Response[Result]
  def assertExpectationsMet: Unit
  
}
