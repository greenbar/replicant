// Copyright 2011 Kiel Hodges
package replicant.support

class MappedResponder[Result] extends Responder[Result] {
  
  private val responses = scala.collection.mutable.LinkedHashMap[Call, ValueResponse[Result]]()
  private var called    = Set[Call]()

  def update(call: Call, response: () => Result) { responses(call) = ValueResponse(response) }
  
  def apply(call: Call): Response[Result] = {
    called += call
    responses.get(call).getOrElse(UnknownResponse("No response expected for " + call))
  }
  
  def assertExpectationsMet { 
    import org.scalatest.Assertions.assert
    val missingCalls = responses.keySet -- called
    assert(missingCalls.isEmpty, "Expected but did not receive " + Call.describe(missingCalls))
  }
  
  override def equals(other: Any) = other match {
    case that: MappedResponder[_] => true 
    case _ => false
  }

  override def hashCode = 37

}
  
