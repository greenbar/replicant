// Copyright 2011 Kiel Hodges
package replicant

object Mocker0 {

  def apply[Result](mock: Any, methodName: String) (implicit fallback: ResponseFallback[Result]): Mocker0[Result] = 
      new Mocker0(mock, methodName, fallback)
    
}

class Mocker0[Result] private[replicant] (mock: Any, methodName: String, fallback: ResponseFallback[Result]) 
extends BaseMocker(Call(mock, methodName), fallback) {
  
  def expect(response: => Result) { expect(call, response) }
  
  def apply(): Result = apply(call)
  
  def assertCalled { assertCalled(call) }

}
