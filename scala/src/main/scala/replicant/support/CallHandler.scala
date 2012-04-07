// Copyright 2011 Kiel Hodges
package replicant.support

import replicant._

private[replicant] trait CallHandler[Result] {
  def update(call: Call, response: => Result): Unit
  def apply(call: Call): Result
  def assertExpectationsMet: Unit
  def assertNotCalled: Unit
  def assertCalled(call: Call): Unit
  def assertCalledOnce: Unit
}

private[replicant] object CallHandler {
  def apply[Result](call: Call, fallback: ResponseFallback[Result]): CallHandler[Result] = 
    new StandardCallHandler(call, new MappedResponder[Result](), fallback)
}

private[support] class StandardCallHandler[Result](
    baseCall: Call, 
    responder: Responder[Result], 
    fallback: ResponseFallback[Result]
) extends CallHandler[Result] {
  
  def update(call: Call, response: => Result) { responder(call) = response _ }

  def apply(call: Call): Result = {
    called += call
    responder(call).value(fallback)
  }

  def assertExpectationsMet { responder.assertExpectationsMet }

  import org.scalatest.Assertions.assert
  import Call.describe 
  
  def assertNotCalled { 
    assert(called.isEmpty, "Expected no calls to " + baseCall + ", but received " + describe(called))
  }

  def assertCalled(call: Call) { 
    assert(called.contains(call), "Expected " + call + ", but received " + describe(called))
  }

  def assertCalledOnce { 
    assert(called.size == 1, "Expected " + baseCall + " to be called once, but received " + describe(called))
  }
  
  private val called = scala.collection.mutable.ListBuffer[Call]()


  private val equalityKey = (baseCall, responder, fallback)

  override def equals(other: Any) = other match {
    case that: StandardCallHandler[_] => this.equalityKey == that.equalityKey
    case _ => false
  }
  
  override def hashCode = equalityKey.hashCode 

  override def toString = "StandardCallHandler(" + baseCall + ", " + responder + ", " + fallback + ')'
  
}
