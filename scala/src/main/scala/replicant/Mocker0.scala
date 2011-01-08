// Copyright 2011 Kiel Hodges
package replicant

object Mocker0 {

  def apply[Result](mock: Any, methodName: String) (implicit fallback: ResponseFallback[Result]): Mocker0[Result] = 
      new Mocker0(mock, methodName, fallback)
    
}

class Mocker0[Result] private[replicant] (mock: Any, methodName: String, fallback: ResponseFallback[Result]) {
  
  def expect(response: => Result) { responder(call) = response _ }
  
  def apply() = {
    called += call
    responseFor(call)()
  }
  
  import org.scalatest.Assertions.assert
  
  def assertCalled { 
    assert(called.contains(call), "Expected " + call + ", but " + historyDescription)
  }

  def assertCalledOnce { 
    assert(called.size == 1, 
           "Expected " + mock + "." + methodName + " to be called once, but " + historyDescription)
  }
  
  def assertNotCalled { 
    assert(called.isEmpty, 
           "Expected no calls to " + mock + "." + methodName + ", but " + historyDescription)
  }
  
  private def historyDescription = "received" + (if (called.isEmpty) " no calls" else ":\n  " + called.mkString("\n  "))

  private val call = Call(mock, methodName)
  
  private def responseFor(call: Call) = responder(call).fold(fallback(_), identity)
  
  private val responder = new MappedResponder[Result]()
  
  private val called = scala.collection.mutable.ListBuffer[Call]()
  
}
  
