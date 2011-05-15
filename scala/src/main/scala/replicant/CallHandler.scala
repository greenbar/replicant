// Copyright 2011 Kiel Hodges
package replicant

private[replicant] trait CallHandler[Result] {
  def expect(call: Call, response: => Result): Unit
  def apply(call: Call): Result
  def assertExpectationsMet: Unit
  def assertNotCalled: Unit
  def assertCalled(call: Call): Unit
  def assertCalledOnce: Unit
}

object CallHandler {
  def apply[Result](call: Call, fallback: ResponseFallback[Result]): CallHandler[Result] = 
    new StandardCallHandler(call, new MappedResponder[Result](), fallback)
}

private[replicant] class StandardCallHandler[Result](
    private val baseCall: Call, 
    private val responder: Responder[Result], 
    private val fallback: ResponseFallback[Result]
) extends CallHandler[Result] {
  
  def expect(call: Call, response: => Result) { 
    responder(call) = response _ 
  }

  def apply(call: Call): Result = {
    called += call
    responder.responseFor(call).value(fallback)
  }

  def assertExpectationsMet { responder.assertExpectationsMet }

  import org.scalatest.Assertions.assert
  
  def assertNotCalled { 
    assert(called.isEmpty, "Expected no calls to " + baseCall + ", but received " + describe(called))
  }

  def assertCalled(call: Call) { 
    assert(called.contains(call), "Expected " + call + ", but received " + describe(called))
  }

  def assertCalledOnce { 
    assert(called.size == 1, "Expected " + baseCall + " to be called once, but received " + describe(called))
  }
  
  private def describe(calls: Iterable[Call]): String = Call.describe(calls) 
  
  private val called = scala.collection.mutable.ListBuffer[Call]()
  
  override def equals(other: Any) = other match {
    case that: StandardCallHandler[_] => this.baseCall == that.baseCall && 
                                         this.responder == that.responder && 
                                         this.fallback == that.fallback
    case _ => false
  }
  
  override def hashCode = 41 * (41 * (41 + baseCall.hashCode) + responder.hashCode) + fallback.hashCode 

  override def toString = "StandardCallHandler(" + baseCall + ", " + responder + ", " + fallback + ')'
  
}
