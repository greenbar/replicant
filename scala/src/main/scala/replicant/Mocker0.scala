// Copyright 2011 Kiel Hodges
package replicant

object Mocker0 {
  def apply[Result: ResponseFallback](mock: Any, methodName: String): Mocker0[Result] = { 
    val call = Call(mock, methodName)
    new Mocker0(call, CallHandler(call, implicitly[ResponseFallback[Result]]))
  }
}

class Mocker0[Result] private[replicant] (call: Call, callHandler: CallHandler[Result]) { 
  
  def apply(): Result = callHandler(call)
  def expect(response: => Result) { callHandler(call) = response       }
  def assertCalled                { callHandler.assertCalled(call)     }
  def assertCalledOnce            { callHandler.assertCalledOnce       } 
  def assertNotCalled             { callHandler.assertNotCalled        } 
  def assertExpectationsMet       { callHandler.assertExpectationsMet  }

}
