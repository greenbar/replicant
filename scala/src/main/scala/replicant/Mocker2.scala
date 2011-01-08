// Copyright 2011 Kiel Hodges
package replicant

object Mocker2 {

  def apply[Args, Result](mock: Any, methodName: String)
    (implicit fallback: ResponseFallback[Result]): Mocker2[Args, Args, Result] = 
      new Mocker2(mock, methodName, fallback)
    
}

class Mocker2[Args1, Args2, Result] private[replicant] (
    mock: Any, 
    methodName: String, 
    fallback: ResponseFallback[Result]
) {
  
  def expect(args1: Args1)(args2: Args2)(response: => Result) { responder(callWith(args1)(args2)) = response _ }
  
  def apply(args1: Args1)(args2: Args2) = {
    val call = callWith(args1)(args2)
    called += call
    responseFor(call)()
  }
  
  import org.scalatest.Assertions.assert
  
  def assertCalled(args1: Args1)(args2: Args2) { 
    val call = callWith(args1)(args2)
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

  private val callWith = Call(mock, methodName)
  
  private def responseFor(call: Call) = responder(call).fold(fallback(_), identity)
  
  private val responder = new MappedResponder[Result]()
  
  private val called = scala.collection.mutable.ListBuffer[Call]()
  
}
  
