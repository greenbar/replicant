// Copyright 2011 Kiel Hodges
package replicant

object Mocker {

  def apply[Args, Result](mock: Any, methodName: String)
  (implicit fallback: ResponseFallback[Result]): Mocker[Args, Result] = { 
    val call = Call(mock, methodName)
    new Mocker(call, new CallHandler(call, fallback))
  }
}

class Mocker[Args, Result] private[replicant] (call: Call, callHandler: CallHandler[Result]) {

  def apply(args: Args): Result = callHandler(call(args))
  def expect(args: Args)(response: => Result) { callHandler.expect(call(args), response) }
  def assertCalled(args: Args)                { callHandler.assertCalled(call(args))     }
  def assertCalledOnce                        { callHandler.assertCalledOnce             } 
  def assertNotCalled                         { callHandler.assertNotCalled              } 

}