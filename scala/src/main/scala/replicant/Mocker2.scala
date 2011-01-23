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
) extends BaseMocker(Call(mock, methodName), fallback) {

  def expect(args1: Args1)(args2: Args2)(response: => Result) { expect(call(args1)(args2), response) }

  def apply(args1: Args1)(args2: Args2): Result = apply(call(args1)(args2))

  def assertCalled(args1: Args1)(args2: Args2) { assertCalled(call(args1)(args2)) }

}
