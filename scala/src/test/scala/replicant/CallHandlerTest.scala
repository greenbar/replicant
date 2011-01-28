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
  
  case class A(value: Int) 
  private val result1 = A(1)
  private val result2 = A(2)
  private val result3 = A(3)
  
  private case class Mock(name: String) 
  private val mock = Mock("aMock")
  private val call = Call(mock, "aMethod")
  private val call1 = call(1, "a")
  private val call2 = call(2, "b")
  private val call3 = call(3, "c")
  private val call4 = call(4, "d")

  @Test def testResponsesWithNoFallback {
    val mocker = CallHandler[A](call, NoResponse)
    val exception = new TestException("testing")
    
    mocker.expect(call1, result1)
    mocker.expect(call2, result2)
    mocker.expect(call3, throw exception)
    
    mocker(call1) should equal(result1)
    mocker(call2) should equal(result2)
    intercept[TestException] { 
      mocker(call3) 
    } should be theSameInstanceAs(exception)
    intercept[UnknownResponseException] { 
      mocker(call4) 
    } should equal(new UnknownResponseException("No response expected for " + call4))
  }

  @Test def testResponsesWithUnitFunction {
    val mocker = CallHandler[Unit](call, UnitFallback)
    val exception = new TestException("testing")
    
    mocker.expect(call1, ()) 
    mocker.expect(call2, 7 should equal(7))
    mocker.expect(call3, throw exception)
    
    mocker(call1)
    mocker(call2)
    intercept[TestException] { 
      mocker(call3) 
    } should be theSameInstanceAs(exception)
    mocker(call4)
  } 

  @Test def testRecordingCalls {
    val mocker = CallHandler[A](call, NoResponse)
    mocker.expect(call1, result1)
    mocker.expect(call2, result2)
    
    mocker.assertNotCalled
    intercept[TestFailedException] { 
      mocker.assertCalled(call2)
    }.message.get should equal("Expected " + call2 + ", but received no calls")
    intercept[TestFailedException] { 
      mocker.assertCalledOnce
    }.message.get should equal("Expected " + call + " to be called once, but received no calls")
    
    mocker(call1)
    mocker.assertCalled(call1)
    mocker.assertCalledOnce
    intercept[TestFailedException] { 
      mocker.assertNotCalled
    }.message.get should equal("Expected no calls to " + call + ", but received:" + calls(call1))
    intercept[TestFailedException] { 
      mocker.assertCalled(call2)
    }.message.get should equal("Expected " + call2 + ", but received:" + calls(call1))
    
    mocker(call1)
    intercept[TestFailedException] { 
      mocker.assertNotCalled
    }.message.get should equal("Expected no calls to " + call + ", but received:" + calls(call1, call1))
    intercept[TestFailedException] { 
      mocker.assertCalledOnce
    }.message.get should equal("Expected " + call + " to be called once, but received:" + calls(call1, call1))
    
    mocker(call2)
    mocker.assertCalled(call1)
    mocker.assertCalled(call2)
    intercept[TestFailedException] { 
      mocker.assertNotCalled
    }.message.get should equal("Expected no calls to " + call + ", but received:" + calls(call1, call1, call2))
    intercept[TestFailedException] { 
      mocker.assertCalledOnce
    }.message.get should equal("Expected " + call + " to be called once, but received:" + calls(call1, call1, call2))
  }

  private def calls(calls: Call*): String = calls.map("\n  " + _).mkString

}