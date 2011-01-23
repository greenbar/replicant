// Copyright 2011 Kiel Hodges
package replicant

object Mocker {

  def apply[Args, Result](mock: Any, methodName: String)
    (implicit fallback: ResponseFallback[Result]): Mocker[Args, Result] = 
      new Mocker(mock, methodName, fallback)
    
}

class Mocker[Args, Result] private[replicant] (mock: Any, methodName: String, fallback: ResponseFallback[Result])
extends BaseMocker(Call(mock, methodName), fallback) {

  def expect(args: Args)(response: => Result) { expect(call(args), response) }
  
  def apply(args: Args): Result = apply(call(args))

  def assertCalled(args: Args) { assertCalled(call(args)) }

}
