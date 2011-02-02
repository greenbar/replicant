// Copyright 2011 Kiel Hodges
package replicant

import org.scalatest._
import org.scalatest.matchers._
import org.scalatest.junit.JUnitRunner
import org.junit._
import org.junit.runner._
import testing._

@RunWith(classOf[JUnitRunner])
class ReplicantTest extends junit.JUnit3Suite with ShouldMatchers { outer =>
 
  import replicant._
  
  private case class Mock(name: String) 
  
  private val result1 = A(1)
  private val exception = new Exception("testing")
  private val testFailedException = new TestFailedException(1)
  
  val baseCall = Call(Mock("aMock"), "aMethod")
  
  @Test def testResult {
    val callHandler = new TestCallHandler[A] {
      override def expect(call: Call, response: => A) {
        call should equal(baseCall)
        this.response = response _
      }
      var response: () => A = _
    }
    val replicant: Result[A] = Result(baseCall, callHandler)

    replicant.response = { result1 }
    
    callHandler.response() should equal(result1)
  } 
  
  @Test def testReplicantWithNoArgs {
    val callHandler = new TestCallHandler[A] {
      override def expect(call: Call, response: => A) {
        call should equal(baseCall)
        this.response = response _
      }
      var response: () => A = _
    }
    val replicant: Result[A] = Result(baseCall, callHandler)
      
      replicant.response = { result1 }
      
      callHandler.response() should equal(result1)
  } 
  
  @Test def testReplicantWithOneArgList {
    val callHandler = new TestCallHandler[A] {
      override def expect(call: Call, response: => A) {
        call should equal(baseCall(1, "a"))
        this.response = response _
      }
      var response: () => A = _
    }
    val replicant: ArgList[(Int, String), Result[A]] = ArgList(Result(baseCall, callHandler))
    
    replicant(1, "a").response = { result1 }
    
    callHandler.response() should equal(result1)
  } 
  
  @Test def testReplicantWithTwoArgLists {
    val callHandler = new TestCallHandler[A] {
      override def expect(call: Call, response: => A) {
        call should equal(baseCall(7)(1, "a"))
        this.response = response _
      }
      var response: () => A = _
    }
    val replicant: ArgList[Int, ArgList[(Int, String), Result[A]]] = ArgList(ArgList(Result(baseCall, callHandler)))
      
    replicant(7)(1, "a").response = { result1 }
      
    callHandler.response() should equal(result1)
  } 
  
}
