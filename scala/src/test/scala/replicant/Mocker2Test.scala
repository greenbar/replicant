// Copyright 2011 Kiela" Hodgesb"
package replicant

import org.scalatest._
import org.scalatest.matchers._
import org.scalatest.junit.JUnitRunner
import org.junit._
import org.junit.runner._
import testing._

@RunWith(classOf[JUnitRunner])
class Mocker2Test extends junit.JUnit3Suite with ShouldMatchers {
 
  import replicant._
  
  private case class A(value: Int) 
  private val a1 = A(1)
  private val a2 = A(2)
  private val a3 = A(3)
  
  private case class Mock(name: String) 
  private val mock = Mock("aMock")
  
  @Test def testReturningValues {
    val mocker = new Mocker2[(Int, String), Int, A](mock, "aMethod", NoResponse)
    
    mocker.expect(1, "abc")(1) { a1 }
    mocker.expect(1, "abc")(2) { a2 }
    mocker.expect(2, "xyz")(1) { a3 }
    
    mocker(1, "abc")(1) should equal(a1)
    mocker(1, "abc")(2) should equal(a2)
    mocker(2, "xyz")(1) should equal(a3)
  } 
  
  @Test def testThrowingExceptions {
    val mocker = new Mocker2[(Int, String), Int, A](mock, "aMethod", NoResponse)
    val exception = new TestException("testing")
    
    mocker.expect(1, "abc")(2) { throw exception }
    intercept[TestException] { mocker(1, "abc")(2) } should be theSameInstanceAs(exception)
  } 
  
  @Test def testWithFallbackValue {
    val mocker = new Mocker2[Int, String, A](mock, "aMethod", new FallbackValue(a3))
    mocker.expect(1)("a") {a1}
    mocker.expect(2)("b") {a2}
    
    mocker(1)("a") should equal(a1)
    mocker(2)("b") should equal(a2)
    mocker(1)("b") should equal(a3)
    mocker(2)("a") should equal(a3)
    mocker(3)("a") should equal(a3)
  } 
  
  @Test def testWithNoResponse {
    val mocker = new Mocker2[Int, String, A](mock, "aMethod", NoResponse)
    mocker.expect(1)("a") {a1}
    mocker.expect(2)("b") {a2}
    
    mocker(1)("a") should equal(a1)
    mocker(2)("b") should equal(a2)
    intercept[UnknownResponseException] { 
      mocker(1)("b") should equal(a3)
    } should equal(new UnknownResponseException("No response expected for " + Call(mock, "aMethod")(1)("b") ))
    intercept[UnknownResponseException] { 
      mocker(2)("a") should equal(a3)
    } should equal(new UnknownResponseException("No response expected for " + Call(mock, "aMethod")(2)("a") ))
    intercept[UnknownResponseException] { 
      mocker(3)("a") should equal(a3)
    } should equal(new UnknownResponseException("No response expected for " + Call(mock, "aMethod")(3)("a") ))
  } 

  @Test def testRecordingCalls {
    val mocker = new Mocker2[(Int, String), Int, A](mock, "aMethod", NoResponse)
    mocker.expect(1, "abc")(10) { a1 }
    mocker.expect(2, "xyz")(20) { a2 }
    
    mocker.assertNotCalled
    intercept[TestFailedException] { 
      mocker.assertCalled(1, "abc")(10)
    }.message.get should equal("Expected " + Call(mock, "aMethod")(1, "abc")(10) + ", but received no calls")
    intercept[TestFailedException] { 
      mocker.assertCalledOnce
    }.message.get should equal("Expected " + mock + ".aMethod to be called once, but received no calls")
    
    mocker(1, "abc")(10)
    mocker.assertCalled(1, "abc")(10)
    mocker.assertCalledOnce
    intercept[TestFailedException] { 
      mocker.assertNotCalled
    }.message.get should equal("Expected no calls to " + mock + ".aMethod, but received:\n" +
    		"  " + Call(mock, "aMethod")(1, "abc")(10))
    intercept[TestFailedException] { 
      mocker.assertCalled(1, "abc")(20)
    }.message.get should equal("Expected " + Call(mock, "aMethod")(1, "abc")(20) + ", but received:\n" +
        "  " + Call(mock, "aMethod")(1, "abc")(10))
    intercept[TestFailedException] { 
      mocker.assertCalled(2, "xyz")(20)
    }.message.get should equal("Expected " + Call(mock, "aMethod")(2, "xyz")(20) + ", but received:\n" +
        "  " + Call(mock, "aMethod")(1, "abc")(10))
    intercept[TestFailedException] { 
      mocker.assertCalled(2, "xyz")(10)
    }.message.get should equal("Expected " + Call(mock, "aMethod")(2, "xyz")(10) + ", but received:\n" +
        "  " + Call(mock, "aMethod")(1, "abc")(10))
    
    mocker(1, "abc")(10)
    intercept[TestFailedException] { 
      mocker.assertNotCalled
    }.message.get should equal("Expected no calls to " + mock + ".aMethod, but received:\n" +
        "  " + Call(mock, "aMethod")(1, "abc")(10) + "\n" +
        "  " + Call(mock, "aMethod")(1, "abc")(10))
    intercept[TestFailedException] { 
      mocker.assertCalledOnce
    }.message.get should equal("Expected " + mock + ".aMethod to be called once, but received:\n" +
        "  " + Call(mock, "aMethod")(1, "abc")(10) + "\n" +
        "  " + Call(mock, "aMethod")(1, "abc")(10))
    
    mocker(2, "xyz")(20)
    mocker.assertCalled(1, "abc")(10)
    mocker.assertCalled(2, "xyz")(20)
    intercept[TestFailedException] { 
      mocker.assertNotCalled
    }.message.get should equal("Expected no calls to " + mock + ".aMethod, but received:\n" +
        "  " + Call(mock, "aMethod")(1, "abc")(10) + "\n" +
        "  " + Call(mock, "aMethod")(1, "abc")(10) + "\n" +
        "  " + Call(mock, "aMethod")(2, "xyz")(20))
    intercept[TestFailedException] { 
      mocker.assertCalledOnce
    }.message.get should equal("Expected " + mock + ".aMethod to be called once, but received:\n" +
        "  " + Call(mock, "aMethod")(1, "abc")(10) + "\n" +
        "  " + Call(mock, "aMethod")(1, "abc")(10) + "\n" +
        "  " + Call(mock, "aMethod")(2, "xyz")(20))
  } 
  
  @Test def testMockerForUnitFunction {
    val mocker = new Mocker2[Int, String, Unit](mock, "aMethod", new FallbackValue(()))
    val exception = new TestException("testing")
    
    mocker.expect(1)("b") { throw exception }
    
    mocker(1)("a")
    mocker(2)("b")
    intercept[TestException] { mocker(1)("b") } should be theSameInstanceAs(exception)
  } 
  
}