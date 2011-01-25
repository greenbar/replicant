// Copyright 2011 Kiel Hodges
package replicant

private[replicant] abstract class BaseMocker[Result] (val call: Call, fallback: ResponseFallback[Result]) {
  
  import org.scalatest.Assertions.assert
  
  def assertCalledOnce { 
    assert(called.size == 1, "Expected " + call + " to be called once, but " + historyDescription)
  }
  
  def assertNotCalled { 
    assert(called.isEmpty, "Expected no calls to " + call + ", but " + historyDescription)
  }

  protected def expect(call: Call, response: => Result) { responder(call) = response _ }

  protected def apply(call: Call): Result = {
    called += call
    responseFor(call)()
  }

  protected def assertCalled(call: Call) { 
    assert(called.contains(call), "Expected " + call + ", but " + historyDescription)
  }

  private def historyDescription = "received" + (if (called.isEmpty) " no calls" else ":\n  " + called.mkString("\n  "))

  private def responseFor(call: Call) = responder(call).fold(fallback(_), identity)

  private val responder = new MappedResponder[Result]()

  private val called = scala.collection.mutable.ListBuffer[Call]()

}
