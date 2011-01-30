// Copyright 2011 Kiel Hodges
package replicant

import org.scalatest._
import org.scalatest.matchers._
import org.scalatest.junit.JUnitRunner
import org.junit._
import org.junit.runner._
import testing._

@RunWith(classOf[JUnitRunner])
class Mocker0Test extends junit.JUnit3Suite with ShouldMatchers { outer =>
 
  import replicant._
  
  private case class A(value: Int) 
  private case class Mock(name: String) 

  private val call = Call(Mock("aMock"), "aMethod")
  private val result1 = A(1)
  private val exception = new Exception("testing")
  private val testFailedException = new TestFailedException(1)

  class TestCallHandler[Result] extends CallHandler[Result] {
    def expect(call: Call, response: => Result): Unit = notImplemented
    def apply(call: Call): Result                     = notImplemented
    def assertExpectationsMet: Unit                   = notImplemented
    def assertNotCalled: Unit                         = notImplemented
    def assertCalled(call: Call): Unit                = notImplemented
    def assertCalledOnce: Unit                        = notImplemented
    private def notImplemented = fail("not implemented")
  }


  @Test def testExpect {
    val callHandler = new TestCallHandler[A] {
      override def expect(call: Call, response: => A) {
        call should equal(outer.call)
        this.response = response _
      }
      var response: () => A = _
    }
    val mocker = new Mocker0[A](call, callHandler)

    mocker.expect { result1 }
    callHandler.response() should equal(result1)

    mocker.expect { throw exception }
    intercept[Exception] { callHandler.response() } should be theSameInstanceAs(exception)
  } 
  
  @Test def testApply {
    val callHandler = new TestCallHandler[A] {
      override def apply(call: Call): A = {
        call should equal(outer.call)
        response()
      }
      var response: () => A = _
    }
    val mocker = new Mocker0[A](call, callHandler)
    
    callHandler.response = () => result1
    mocker() should equal(result1)
    
    callHandler.response = () => throw exception
    intercept[Exception] { mocker() } should be theSameInstanceAs(exception)
  } 

  @Test def testAssertNotCalled {
    val callHandler = new TestCallHandler[A] {
      override def assertNotCalled {
        called = true
        if (shouldFail) throw testFailedException
      }
      var called = false
      var shouldFail = false
    }
    val mocker = new Mocker0[A](call, callHandler)
    
    mocker.assertNotCalled
    callHandler.called should equal(true)
    
    callHandler.shouldFail = true
    intercept[TestFailedException] { mocker.assertNotCalled } should be theSameInstanceAs(testFailedException)
  } 
  
  @Test def testAssertCalled {
    val callHandler = new TestCallHandler[A] {
      override def assertCalled(call: Call) {
        called = true
        call should equal(outer.call)
        if (shouldFail) throw testFailedException
      }
      var called = false
      var shouldFail = false
    }
    val mocker = new Mocker0[A](call, callHandler)
    
    mocker.assertCalled
    callHandler.called should equal(true)
    
    callHandler.shouldFail = true
    intercept[TestFailedException] { mocker.assertCalled } should be theSameInstanceAs(testFailedException)
  } 
  
  @Test def testAssertCalledOnce {
    val callHandler = new TestCallHandler[A] {
      override def assertCalledOnce {
        called = true
        if (shouldFail) throw testFailedException
      }
      var called = false
      var shouldFail = false
    }
    val mocker = new Mocker0[A](call, callHandler)
    
    mocker.assertCalledOnce
    callHandler.called should equal(true)
    
    callHandler.shouldFail = true
    intercept[TestFailedException] { mocker.assertCalledOnce } should be theSameInstanceAs(testFailedException)
  } 
  
}