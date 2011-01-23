// Copyright 2011 Kiel Hodges
package replicant

import org.scalatest._
import org.scalatest.matchers._
import org.scalatest.junit.JUnitRunner
import org.junit._
import org.junit.runner._
import testing._

@RunWith(classOf[JUnitRunner])
class Mocker0Test extends junit.JUnit3Suite with ShouldMatchers {
 
  import replicant._
  
  private case class A(value: Int) 
  private val a1 = A(1)
  private val a2 = A(2)
  private val a3 = A(3)
  
  private case class Mock(name: String) 
  private val mock = Mock("aMock")
  
  @Test def testReturningValues {
    val mocker = new Mocker0[A](mock, "aMethod", NoResponse)
    
    mocker.expect { a1 }
    
    mocker() should equal(a1)
  } 
  
  @Test def testThrowingExceptions {
    val mocker = new Mocker0[A](mock, "aMethod", NoResponse)
    val exception = new TestException("testing")
    
    mocker.expect { throw exception }
    intercept[TestException] { mocker() } should be theSameInstanceAs(exception)
  } 
  
  @Test def testRecordingCalls {
    val mocker = new Mocker0[A](mock, "aMethod", NoResponse)
    mocker.expect { a1 }
    
    mocker.assertNotCalled
    intercept[TestFailedException] { 
      mocker.assertCalled
    }.message.get should equal("Expected " + Call(mock, "aMethod") + ", but received no calls")
    intercept[TestFailedException] { 
      mocker.assertCalledOnce
    }.message.get should equal("Expected " + mock + ".aMethod to be called once, but received no calls")
    
    mocker()
    mocker.assertCalled
    mocker.assertCalledOnce
    intercept[TestFailedException] { 
      mocker.assertNotCalled
    }.message.get should equal("Expected no calls to " + mock + ".aMethod, but received:\n" +
    		"  " + Call(mock, "aMethod"))
    
    mocker()
    intercept[TestFailedException] { 
      mocker.assertNotCalled
    }.message.get should equal("Expected no calls to " + mock + ".aMethod, but received:\n" +
        "  " + Call(mock, "aMethod") + "\n" +
        "  " + Call(mock, "aMethod"))
    intercept[TestFailedException] { 
      mocker.assertCalledOnce
    }.message.get should equal("Expected " + mock + ".aMethod to be called once, but received:\n" +
        "  " + Call(mock, "aMethod") + "\n" +
        "  " + Call(mock, "aMethod"))
  } 
  
  @Test def testWithNoResponse {
    val mocker = new Mocker0[A](mock, "aMethod", NoResponse)
    
    intercept[UnknownResponseException] { 
      mocker() 
    } should equal(new UnknownResponseException("No response expected for " + Call(mock, "aMethod") ))
  } 

  @Test def testMocker0ForUnitFunction {
    val exception = new TestException("testing")
    
    val mocker = new Mocker0[Unit](mock, "aMethod", UnitFallback)
    mocker()

    mocker.expect { 7 should equal(7) }
    mocker()
    
    mocker.expect { throw exception }
    intercept[TestException] { mocker() } should be theSameInstanceAs(exception)
  } 
  
}