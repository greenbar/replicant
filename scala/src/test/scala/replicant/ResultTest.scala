// Copyright 2011 Kiel Hodges
package replicant

import org.scalatest._
import org.scalatest.matchers._
import org.scalatest.junit.JUnitRunner
import org.junit._
import org.junit.runner._
import testing._

@RunWith(classOf[JUnitRunner])
class ResultTest extends junit.JUnit3Suite with ShouldMatchers { outer =>
 
  import replicant._
  
  private case class Mock(name: String) 
  
  private val resultValue = A(1)
  private val exception = new Exception("testing")
  private val testFailedException = new TestFailedException(1)
  
  val call1 = Call(Mock("aMock"), "aMethod")
  val call2 = Call(Mock("aMock"), "anotherMethod")
  
  @Test def testWithArgs {
    val callHandler = new TestCallHandler[A]
    val result: Result[A] = Result(call1, callHandler)

    val resultWithArgs: Result[A] = result.withArgs((1, "a")) 
    
    resultWithArgs should equal(Result(call1(1, "a"), callHandler))
  } 

  @Test def testSettingResponse {
    val callHandler = new TestCallHandler[A] {
      override def expect(call: Call, response: => A) {
        call should equal(call1)
        this.response = response _
      }
      var response: () => A = _
    }
    val result: Result[A] = Result(call1, callHandler)
      
      result.response = { resultValue }
      
      callHandler.response() should equal(resultValue)
  } 
  
  @Test def testResponse {
    val callHandler = new TestCallHandler[A] {
      var response: () => A = _
      override def apply(call: Call): A = {
        call should equal(call1)
        response()
      }
    }
    val result: Result[A] = Result(call1, callHandler)
      
    callHandler.response = () => resultValue
    result.response should equal(resultValue)
    
    callHandler.response = () => throw exception
    intercept[Exception] { result.response } should be theSameInstanceAs(exception)
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
    val result: Result[A] = Result(call1, callHandler)
    
    result.assertNotCalled()
    callHandler.called should equal(true)
    
    callHandler.shouldFail = true
    intercept[TestFailedException] { result.assertNotCalled() } should be theSameInstanceAs(testFailedException)
  } 
  
  @Test def testAssertCalled {
    val callHandler = new TestCallHandler[A] {
      override def assertCalled(call: Call) {
        called = true
        call should equal(call1)
        if (shouldFail) throw testFailedException
      }
      var called = false
      var shouldFail = false
    }
    val result: Result[A] = Result(call1, callHandler)
    
    result.assertCalled()
    callHandler.called should equal(true)
    
    callHandler.shouldFail = true
    intercept[TestFailedException] { result.assertCalled() } should be theSameInstanceAs(testFailedException)
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
    val result: Result[A] = Result(call1, callHandler)
    
    result.assertCalledOnce()
    callHandler.called should equal(true)
    
    callHandler.shouldFail = true
    intercept[TestFailedException] { result.assertCalledOnce() } should be theSameInstanceAs(testFailedException)
  } 
  
  @Test def testAssertAllResponsesUsed {
    val callHandler = new TestCallHandler[A] {
      override def assertExpectationsMet {
        called = true
        if (shouldFail) throw testFailedException
      }
      var called = false
      var shouldFail = false
    }
    val result: Result[A] = Result(call1, callHandler)
    
    result.assertAllResponsesUsed()
    callHandler.called should equal(true)
    
    callHandler.shouldFail = true
    intercept[TestFailedException] { result.assertAllResponsesUsed() } should be theSameInstanceAs(testFailedException)
  } 

  @Test def testResultEquality {
    val callHandler1 = new TestCallHandler[A]
    val callHandler2 = new TestCallHandler[A]
    testEqualityOf(   Result(call1, callHandler1) ).
      shouldEqual(    Result(call1, callHandler1) ).
      shouldNotEqual( Result(call2, callHandler1) ).
      shouldNotEqual( Result(call1, callHandler2) ).
      shouldNotEqual( "not a Result" )
  } 

}
