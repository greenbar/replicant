// Copyright 2011 Kiel Hodges
package replicant

object CallHandler {
  def apply[Result](call: Call, fallback: ResponseFallback[Result]) = new CallHandler(call, fallback)
}

private[replicant] class CallHandler[Result](val call: Call, fallback: ResponseFallback[Result]) {
  
  import org.scalatest.Assertions.assert
  
  def assertCalledOnce { 
    assert(called.size == 1, "Expected " + call + " to be called once, but " + historyDescription)
  }
  
  def assertNotCalled { 
    assert(called.isEmpty, "Expected no calls to " + call + ", but " + historyDescription)
  }

  def expect(call: Call, response: => Result) { responder(call) = response _ }

  def apply(call: Call): Result = {
    called += call
    responseFor(call)()
  }

  def assertCalled(call: Call) { 
    assert(called.contains(call), "Expected " + call + ", but " + historyDescription)
  }

  private def historyDescription = "received" + (if (called.isEmpty) " no calls" else ":\n  " + called.mkString("\n  "))

  private def responseFor(call: Call) = responder(call).fold(fallback(_), identity)

  private val responder = new MappedResponder[Result]()

  private val called = scala.collection.mutable.ListBuffer[Call]()

}
