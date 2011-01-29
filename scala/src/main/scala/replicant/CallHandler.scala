// Copyright 2011 Kiel Hodges
package replicant

object CallHandler {
  def apply[Result](call: Call, fallback: ResponseFallback[Result]) = new CallHandler(call, fallback)
}

private[replicant] class CallHandler[Result](val call: Call, fallback: ResponseFallback[Result]) {
  
  import org.scalatest.Assertions.assert
  
  def assertCalledOnce { 
    assert(called.size == 1, "Expected " + call + " to be called once, but received " + describe(called))
  }
  
  def assertNotCalled { 
    assert(called.isEmpty, "Expected no calls to " + call + ", but received " + describe(called))
  }

  def expect(call: Call, response: => Result) { 
    responder(call) = response _ 
  }

  def apply(call: Call): Result = {
    called += call
    responseFor(call)()
  }

  def assertCalled(call: Call) { 
    assert(called.contains(call), "Expected " + call + ", but received " + describe(called))
  }

  private def describe(calls: Iterable[Call]): String = Call.describe(calls) 
  
  private def responseFor(call: Call) = responder(call).fold(fallback(_), identity)

  private val responder = new MappedResponder[Result]()

  private val called = scala.collection.mutable.ListBuffer[Call]()
  
}
