// Copyright 2011 Kiel Hodges
package replicant

object Mocker {

  def apply[Args, Result](mock: Any, methodName: String)
    (implicit fallback: ResponseFallback[Result]): Mocker[Args, Result] = 
      new Mocker(mock, methodName, fallback)
    
}

class Mocker[Args, Result](mock: Any, methodName: String, fallback: ResponseFallback[Result]) {
  
  def expect(args: Args)(response: => Result) { responder(callWith(args)) = response _ }
  
  def apply(args: Args) = {
    val call = callWith(args)
    called += call
    responseFor(call)()
  }
  
  import org.scalatest.Assertions.assert
  
  def assertCalled(args: Args) { 
    val call = callWith(args)
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

  private def callWith(args: Args) = Call(mock, methodName, args)
  
  private def responseFor(call: Call[Args]) = responder(call).fold(fallback(_), identity)
  
  private val responder = new MappedResponder[Args, Result]()
  
  private val called = scala.collection.mutable.ListBuffer[Call[Args]]()
  
}
  
