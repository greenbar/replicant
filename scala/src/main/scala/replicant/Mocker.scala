// Copyright 2011 Kiel Hodges
package replicant

import support._

object Mocker {
  def apply[Args, Result: ResponseFallback](mock: Any, methodName: String): Mocker[Args, Result] = { 
    val call = Call(mock, methodName)
    new Mocker(call, CallHandler(call, implicitly[ResponseFallback[Result]]))
  }
}

class Mocker[Args, Result] private[replicant] (call: Call, callHandler: CallHandler[Result]) {

  def apply(args: Args): Result = callHandler(call(args))
  def expect(args: Args)(response: => Result) { callHandler(call(args)) = response }
  def assertCalled(args: Args)                { callHandler.assertCalled(call(args))     }
  def assertCalledOnce                        { callHandler.assertCalledOnce             } 
  def assertNotCalled                         { callHandler.assertNotCalled              } 
  def assertExpectationsMet                   { callHandler.assertExpectationsMet        } 

  override def toString = "Mocker(" + call + ", " + callHandler + ')'

  private val equalityKey = (call, callHandler)

  override def equals(other: Any) = other match {
    case that: Mocker[_, _] => this.equalityKey == that.equalityKey
    case _ => false
  }
  
  override def hashCode = equalityKey.hashCode 

}
