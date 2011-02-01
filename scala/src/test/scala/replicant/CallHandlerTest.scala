// Copyright 2011 Kiel Hodges
package replicant

import org.scalatest._
import org.scalatest.matchers._
import org.scalatest.junit.JUnitRunner
import org.junit._
import org.junit.runner._
import testing._

@RunWith(classOf[JUnitRunner])
class CallHandlerTest extends junit.JUnit3Suite with ShouldMatchers {
 
  import replicant._
  
  class TestResponder[Result] extends Responder[Result] with testing.NotImplemented {
    def update(call: Call, response: () => Result): Unit                  = notImplemented
    def apply(call: Call): Either[UnknownResponseException, () => Result] = notImplemented
    def assertExpectationsMet: Unit                                       = notImplemented
  }
  
  private val result1 = A(1)
  private val result2 = A(2)
  private val result3 = A(3)
  
  private case class Mock(name: String) 
  private val mock = Mock("aMock")
  private val baseCall = Call(mock, "aMethod")
  private val call1 = baseCall(1, "a")
  private val call2 = baseCall(2, "b")
  private val call3 = baseCall(3, "c")
  private val call4 = baseCall(4, "d")

  @Test def testExpect {
    val responder = new TestResponder[A] {
      var response: () => A = _
      override def update(call: Call, response: () => A) {
        call should equal(call1)
        this.response = response
      }
    }
    val handler = new StandardCallHandler[A](baseCall, responder, NoResponse)
    
    handler.expect(call1, result1)
    
    responder.response() should equal(result1)
  }
  
  @Test def testApplyWithNoFallbackWhenResponderReturnsResponse {
    val responder = new TestResponder[A] {
      override def apply(call: Call): Either[UnknownResponseException, () => A] = {
        call should equal(call1)
        Right(() => result1)
      }
    }
    val handler = new StandardCallHandler[A](baseCall, responder, NoResponse)
    
    handler(call1) should equal(result1)
  }
  
  @Test def testApplyWithNoFallbackWhenResponderReturnsUnknownResponseException {
    val exception = new UnknownResponseException("testing")
    val responder = new TestResponder[A] {
      override def apply(call: Call): Either[UnknownResponseException, () => A] = {
        call should equal(call1)
        Left(exception)
      }
    }
    val handler = new StandardCallHandler[A](baseCall, responder, NoResponse)

    intercept[UnknownResponseException] { 
      handler(call1) 
    } should be theSameInstanceAs(exception)
  }
  
  @Test def testApplyWithUnitFallbackWhenResponderReturnsUnknownResponseException {
    val exception = new UnknownResponseException("testing")
    val responder = new TestResponder[Unit] {
      override def apply(call: Call): Either[UnknownResponseException, () => Unit] = {
        call should equal(call1)
        Left(exception)
      }
    }
    val handler = new StandardCallHandler[Unit](baseCall, responder, UnitFallback)
    
    handler(call1) should equal(())
  }
  
  @Test def testAssertExpectationsMade {
    val exception = new TestFailedException(1)
    val responder = new TestResponder[Unit] {
      override def assertExpectationsMet {
        throw exception
      }
    }
    val handler = new StandardCallHandler[Unit](baseCall, responder, UnitFallback)
    
    intercept[TestFailedException] { 
      handler.assertExpectationsMet
    } should be theSameInstanceAs(exception)
  }
  
  @Test def testRecordingCalls {
    val handler = CallHandler[A](baseCall, NoResponse)
    handler.expect(call1, result1)
    handler.expect(call2, result2)
    
    handler.assertNotCalled
    intercept[TestFailedException] { 
      handler.assertCalled(call2)
    }.message.get should equal("Expected " + call2 + ", but received no calls")
    intercept[TestFailedException] { 
      handler.assertCalledOnce
    }.message.get should equal("Expected " + baseCall + " to be called once, but received no calls")
    
    handler(call1)
    handler.assertCalled(call1)
    handler.assertCalledOnce
    intercept[TestFailedException] { 
      handler.assertNotCalled
    }.message.get should equal("Expected no calls to " + baseCall + ", but received " + calls(call1))
    intercept[TestFailedException] { 
      handler.assertCalled(call2)
    }.message.get should equal("Expected " + call2 + ", but received " + calls(call1))
    
    handler(call1)
    intercept[TestFailedException] { 
      handler.assertNotCalled
    }.message.get should equal("Expected no calls to " + baseCall + ", but received " + calls(call1, call1))
    intercept[TestFailedException] { 
      handler.assertCalledOnce
    }.message.get should equal("Expected " + baseCall + " to be called once, but received " + calls(call1, call1))

    handler(call2)
    handler.assertCalled(call1)
    handler.assertCalled(call2)
    intercept[TestFailedException] { 
      handler.assertNotCalled
    }.message.get should equal("Expected no calls to " + baseCall + ", but received " + calls(call1, call1, call2))
    intercept[TestFailedException] { 
      handler.assertCalledOnce
    }.message.get should equal("Expected " + baseCall + " to be called once, but received " + calls(call1, call1, call2))
  }

  private def calls(calls: Call*): String = Call.describe(calls)

}