// Copyright 2011 Kiel Hodges
package replicant

import support._

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

  override def toString = "Mocker0(" + call + ", " + callHandler + ')'

  private val equalityKey = (call, callHandler)

  override def equals(other: Any) = other match {
    case that: Mocker0[_] => this.equalityKey == that.equalityKey
    case _ => false
  }
  
  override def hashCode = equalityKey.hashCode 

}
