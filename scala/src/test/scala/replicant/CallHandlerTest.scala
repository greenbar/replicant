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
  private val a1 = A(1)
  private val a2 = A(2)
  private val a3 = A(3)
  
  private case class Mock(name: String) 
  private val mock = Mock("aMock")
  private val call = Call(mock, "aMethod")
  private val call1 = call(1, "a")
  private val call2 = call(2, "b")
  private val call3 = call(3, "c")

  @Test def testResponses {
    val mocker = CallHandler[A](call, NoResponse)
    val exception = new TestException("testing")
    
    mocker.expect(call1, a1)
    mocker.expect(call2, a2)
    mocker.expect(call3, throw exception)
    
    mocker(call1) should equal(a1)
    mocker(call2) should equal(a2)
    intercept[TestException] { mocker(call3) } should be theSameInstanceAs(exception)
  } 
  
  @Test def testRecordingCalls {
    val mocker = CallHandler[A](call, NoResponse)
    mocker.expect(call1, a1)
    mocker.expect(call2, a2)
    
    mocker.assertNotCalled
    intercept[TestFailedException] { 
      mocker.assertCalled(call2)
    }.message.get should equal("Expected " + call2 + ", but received no calls")
    intercept[TestFailedException] { 
      mocker.assertCalledOnce
    }.message.get should equal("Expected " + mock + ".aMethod to be called once, but received no calls")
    
    mocker(call1)
    mocker.assertCalled(call1)
    mocker.assertCalledOnce
    intercept[TestFailedException] { 
      mocker.assertNotCalled
    }.message.get should equal("Expected no calls to " + mock + ".aMethod, but received:\n" +
    		"  " + call1)
    intercept[TestFailedException] { 
      mocker.assertCalled(call2)
    }.message.get should equal("Expected " + call2 + ", but received:\n" +
        "  " + call1)
    
    mocker(call1)
    intercept[TestFailedException] { 
      mocker.assertNotCalled
    }.message.get should equal("Expected no calls to " + mock + ".aMethod, but received:\n" +
        "  " + call1 + "\n" +
        "  " + call1)
    intercept[TestFailedException] { 
      mocker.assertCalledOnce
    }.message.get should equal("Expected " + mock + ".aMethod to be called once, but received:\n" +
        "  " + call1 + "\n" +
        "  " + call1)
    
    mocker(call2)
    mocker.assertCalled(call1)
    mocker.assertCalled(call2)
    intercept[TestFailedException] { 
      mocker.assertNotCalled
    }.message.get should equal("Expected no calls to " + mock + ".aMethod, but received:\n" +
        "  " + call1 + "\n" +
        "  " + call1 + "\n" +
        "  " + call2)
    intercept[TestFailedException] { 
      mocker.assertCalledOnce
    }.message.get should equal("Expected " + mock + ".aMethod to be called once, but received:\n" +
        "  " + call1 + "\n" +
        "  " + call1 + "\n" +
        "  " + call2)
  } 

  @Test def testWithNoResponse {
    val mocker = CallHandler[A](call, NoResponse)
    mocker.expect(call1, a1)
    mocker.expect(call2, a2)
    
    mocker(call1) should equal(a1)
    mocker(call2) should equal(a2)
    intercept[UnknownResponseException] { 
      mocker(call3) 
    } should equal(new UnknownResponseException("No response expected for " + call3))
  }

  @Test def testMockerForUnitFunction {
    val mocker = CallHandler[Unit](call, UnitFallback)
    val exception = new TestException("testing")
    
    mocker.expect(call2, 7 should equal(7))
    mocker.expect(call3, throw exception)
    
    mocker(call1)
    mocker(call2)
    intercept[TestException] { mocker(call3) } should be theSameInstanceAs(exception)
  } 
  
}