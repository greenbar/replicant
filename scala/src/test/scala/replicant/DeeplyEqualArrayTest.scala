// Copyright 2011 Kiel Hodges
package replicant

import org.scalatest._
import org.scalatest.matchers._
import org.scalatest.junit.JUnitRunner
import org.junit._
import org.junit.runner._
import testing._

@RunWith(classOf[JUnitRunner])
class DeeplyEqualArrayTest extends junit.JUnit3Suite with ShouldMatchers {
 
  import replicant._
  
  private case class A(value: Int) 
  private val a1 = A(1)
  private val a2 = A(2)
  private val a3 = A(3)
  
  private case class Mock(name: String) 
  private val mock = Mock("aMock")
  
  @Test def testDeeplyEqualArray {
    val array1: DeeplyEqualArray[Int] = Array(1, 2, 3)
    val array2: DeeplyEqualArray[Int] = Array(2, 2, 3)
    
    array1 should equal(array1)
    array1 should not equal(array2)
    
    array2.self(0) = 1
    array1 should equal(array2)
    array1.hashCode should equal(array2.hashCode)
    
    array1.toString should equal("Array(1, 2, 3)")
  } 

  @Test def testReturningValues {
    val mocker = new Mocker[DeeplyEqualArray[Int], A](mock, "aMethod", NoResponse)
    val array1 = Array(1, 10, 1)
    val array2 = Array(2, 20, 2)
    val array3 = Array(3, 30, 3)
    mocker.expect(array1) { a1 }
    mocker.expect(array2) { a2 }
    
    mocker(array1) should equal(a1)
    mocker(array2) should equal(a2)
    
    intercept[UnknownResponseException] { 
      mocker(array3)
    } should equal(new UnknownResponseException("No response expected for " + Call(mock, "aMethod") + "(Array(3, 30, 3))" ))
  } 
  
  @Test def testRecordingCalls {
    val mocker = new Mocker[DeeplyEqualArray[Int], A](mock, "aMethod", NoResponse)
    val array1 = Array(1, 10, 1)
    val array2 = Array(2, 20, 2)
    val array3 = Array(3, 30, 3)
    mocker.expect(array1) { a1 }
    mocker.expect(array2) { a2 }
    
    mocker(array1)
    
    mocker.assertCalled(array1)
    intercept[TestFailedException] { 
      mocker.assertCalled(array2)
    }.message.get should equal("Expected " + Call(mock, "aMethod") + "(Array(2, 20, 2))" + ", but received:\n" +
        "  " + Call(mock, "aMethod") + "(Array(1, 10, 1))")
  } 
  
}