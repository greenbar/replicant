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
    baseCall: Call, 
    responder: Responder[Result], 
    fallback: ResponseFallback[Result]
) extends CallHandler[Result] {
  
  def expect(call: Call, response: => Result) { 
    responder(call) = response _ 
  }

  def apply(call: Call): Result = {
    called += call
    responseFor(call)()
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
  
  private def responseFor(call: Call) = responder(call).fold(fallback(_), identity)

  private val called = scala.collection.mutable.ListBuffer[Call]()
  
}
