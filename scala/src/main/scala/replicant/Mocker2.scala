// Copyright 2011 Kiel Hodges
package replicant

object Mocker2 {

  def apply[Args1, Args2, Result](mock: Any, methodName: String)
  (implicit fallback: ResponseFallback[Result]): Mocker2[Args1, Args2, Result] = {
    val call = Call(mock, methodName)
    new Mocker2(call, new CallHandler(call, fallback))
  }
    
}

class Mocker2[Args1, Args2, Result] private[replicant] (call: Call, callHandler: CallHandler[Result]) {

  def apply(args1: Args1)(args2: Args2): Result = callHandler(call(args1)(args2))
  def expect(args1: Args1)(args2: Args2)(response: => Result) { callHandler.expect(call(args1)(args2), response) }
  def assertCalled(args1: Args1)(args2: Args2)                { callHandler.assertCalled(call(args1)(args2))     }
  def assertCalledOnce                                        { callHandler.assertCalledOnce                     } 
  def assertNotCalled                                         { callHandler.assertNotCalled                      } 

}
