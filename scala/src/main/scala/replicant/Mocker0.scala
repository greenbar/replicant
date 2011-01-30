// Copyright 2011 Kiel Hodges
package replicant

object Mocker0 {

  def apply[Result](mock: Any, methodName: String) (implicit fallback: ResponseFallback[Result]): Mocker0[Result] = { 
    val call = Call(mock, methodName)
    new Mocker0(call, CallHandler(call, fallback))
  }
    
}

class Mocker0[Result] private[replicant] (call: Call, callHandler: CallHandler[Result]) { 
  
  def apply(): Result = callHandler(call)
  def expect(response: => Result) { callHandler.expect(call, response) }
  def assertCalled                { callHandler.assertCalled(call)     }
  def assertCalledOnce            { callHandler.assertCalledOnce       } 
  def assertNotCalled             { callHandler.assertNotCalled        } 
  def assertExpectationsMet       { callHandler.assertExpectationsMet  }

}
