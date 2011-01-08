// Copyright 2011 Kiel Hodges
package replicant

import org.scalatest._
import org.scalatest.matchers._
import org.scalatest.junit.JUnitRunner
import org.junit._
import org.junit.runner._
import testing._

@RunWith(classOf[JUnitRunner])
class MockerTest extends junit.JUnit3Suite with ShouldMatchers {
 
  import replicant._
  
  private case class A(value: Int) 
  private val a1 = A(1)
  private val a2 = A(2)
  private val a3 = A(3)
  
  private case class Mock(name: String) 
  private val mock = Mock("aMock")
  
  @Test def testReturningValues {
    val mocker = new Mocker[(Int, String), A](mock, "aMethod", NoResponse)
    
    mocker.expect(1, "abc") { a1 }
    mocker.expect(2, "xyz") { a2 }
    
    mocker(1, "abc") should equal(a1)
    mocker(2, "xyz") should equal(a2)
  } 
  
  @Test def testThrowingExceptions {
    val mocker = new Mocker[(Int, String), A](mock, "aMethod", NoResponse)
    val exception = new TestException("testing")
    
    mocker.expect(1, "abc") { throw exception }
    intercept[TestException] { mocker(1, "abc") } should be theSameInstanceAs(exception)
  } 
  
  @Test def testWithFallbackValue {
    val mocker = new Mocker[Int, A](mock, "aMethod", new FallbackValue(a3))
    mocker.expect(1) {a1}
    mocker.expect(2) {a2}
    
    mocker(1) should equal(a1)
    mocker(2) should equal(a2)
    mocker(3) should equal(a3)
  } 
  
  @Test def testWithNoResponse {
    val mocker = new Mocker[Int, A](mock, "aMethod", NoResponse)
    mocker.expect(1) {a1}
    mocker.expect(2) {a2}
    
    mocker(1) should equal(a1)
    mocker(2) should equal(a2)
    intercept[UnknownResponseException] { 
      mocker(3) 
    } should equal(new UnknownResponseException("No response expected for " + Call(mock, "aMethod")(3) ))
  } 

  @Test def testRecordingCalls {
    val mocker = new Mocker[(Int, String), A](mock, "aMethod", NoResponse)
    mocker.expect(1, "abc") { a1 }
    mocker.expect(2, "xyz") { a2 }
    
    mocker.assertNotCalled
    intercept[TestFailedException] { 
      mocker.assertCalled(2, "xyz")
    }.message.get should equal("Expected " + Call(mock, "aMethod")(2, "xyz") + ", but received no calls")
    intercept[TestFailedException] { 
      mocker.assertCalledOnce
    }.message.get should equal("Expected " + mock + ".aMethod to be called once, but received no calls")
    
    mocker(1, "abc")
    mocker.assertCalled(1, "abc")
    mocker.assertCalledOnce
    intercept[TestFailedException] { 
      mocker.assertNotCalled
    }.message.get should equal("Expected no calls to " + mock + ".aMethod, but received:\n" +
    		"  " + Call(mock, "aMethod")(1, "abc"))
    intercept[TestFailedException] { 
      mocker.assertCalled(2, "xyz")
    }.message.get should equal("Expected " + Call(mock, "aMethod")((2, "xyz")) + ", but received:\n" +
        "  " + Call(mock, "aMethod")(1, "abc"))
    
    mocker(1, "abc")
    intercept[TestFailedException] { 
      mocker.assertNotCalled
    }.message.get should equal("Expected no calls to " + mock + ".aMethod, but received:\n" +
        "  " + Call(mock, "aMethod")(1, "abc") + "\n" +
        "  " + Call(mock, "aMethod")(1, "abc"))
    intercept[TestFailedException] { 
      mocker.assertCalledOnce
    }.message.get should equal("Expected " + mock + ".aMethod to be called once, but received:\n" +
        "  " + Call(mock, "aMethod")(1, "abc") + "\n" +
        "  " + Call(mock, "aMethod")(1, "abc"))
    
    mocker(2, "xyz")
    mocker.assertCalled(1, "abc")
    mocker.assertCalled(2, "xyz")
    intercept[TestFailedException] { 
      mocker.assertNotCalled
    }.message.get should equal("Expected no calls to " + mock + ".aMethod, but received:\n" +
        "  " + Call(mock, "aMethod")(1, "abc") + "\n" +
        "  " + Call(mock, "aMethod")(1, "abc") + "\n" +
        "  " + Call(mock, "aMethod")(2, "xyz"))
    intercept[TestFailedException] { 
      mocker.assertCalledOnce
    }.message.get should equal("Expected " + mock + ".aMethod to be called once, but received:\n" +
        "  " + Call(mock, "aMethod")(1, "abc") + "\n" +
        "  " + Call(mock, "aMethod")(1, "abc") + "\n" +
        "  " + Call(mock, "aMethod")(2, "xyz"))
  } 
  
  @Test def testMockerForUnitFunction {
    val mocker = new Mocker[Int, Unit](mock, "aMethod", new FallbackValue(()))
    val exception = new TestException("testing")
    
    mocker.expect(2) { throw exception }
    
    mocker(1)
    intercept[TestException] { mocker(2) } should be theSameInstanceAs(exception)
  } 
  
  @Test def testMockerForNoArgFunction {
    val mocker = new Mocker[Unit, A](mock, "aMethod", NoResponse)
    val exception = new TestException("testing")
    
    mocker.expect() { a1 }
    
    mocker() should equal(a1)
  }  
   
}