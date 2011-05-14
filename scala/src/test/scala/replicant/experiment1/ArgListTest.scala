// Copyright 2011 Kiel Hodges
package replicant.experiment1

import org.scalatest._
import org.scalatest.matchers._
import org.scalatest.junit.JUnitRunner
import org.junit._
import org.junit.runner._
import testing._

@RunWith(classOf[JUnitRunner])
class ArgListTest extends junit.JUnit3Suite with ShouldMatchers { outer =>
 
  import replicant._
  
  private case class Mock(name: String) 
  
  private val resultValue = A(1)
  private val exception = new Exception("testing")
  private val testFailedException = new TestFailedException(1)
  
  val baseCall = Call(Mock("aMock"), "aMethod")
  
  class TestReplicant extends Replicant[TestReplicant] with NotImplemented {
    def assertCalledOnce()       { notImplemented }
    def assertNotCalled()        { notImplemented }
    def assertAllResponsesUsed() { notImplemented }
    private[replicant] def withArgs[NewArgs](args: NewArgs): TestReplicant = notImplemented
  }
  
  @Test def testWithArgs {
    val next = new TestReplicant {
      override private[replicant] def withArgs[NewArgs](args: NewArgs): TestReplicant = {
        args should equal((1, "a"))
        withArgs
      }
      var withArgs: TestReplicant = _
    }
    next.withArgs = new TestReplicant
    val argList: ArgList[(Int, String), TestReplicant] = ArgList(next)
    
    val argListWithArgs: ArgList[(Int, String), TestReplicant] = argList.withArgs((1, "a")) 
    
    argListWithArgs should equal(ArgList(next.withArgs))
  } 

  @Test def testArgListApply {
    val next = new TestReplicant {
      override private[replicant] def withArgs[NewArgs](args: NewArgs): TestReplicant = {
        args should equal((1, "a"))
        withArgs
      }
      var withArgs: TestReplicant = _
    }
    next.withArgs = new TestReplicant
    val argList: ArgList[(Int, String), TestReplicant] = ArgList(next)
    
    argList(1, "a") should equal(next.withArgs)
  }

  @Test def testAssertNotCalled {
    val next = new TestReplicant {
      override def assertNotCalled() {
        called = true
        if (shouldFail) throw testFailedException
      }
      var called = false
      var shouldFail = false
    }
    val argList: ArgList[(Int, String), TestReplicant] = ArgList(next)
    
    argList.assertNotCalled()
    next.called should equal(true)
    
    next.shouldFail = true
    intercept[TestFailedException] { argList.assertNotCalled() } should be theSameInstanceAs(testFailedException)
  } 
  
  @Test def testAssertCalledOnce {
    val next = new TestReplicant {
      override def assertCalledOnce() {
        called = true
        if (shouldFail) throw testFailedException
      }
      var called = false
      var shouldFail = false
    }
    val argList: ArgList[(Int, String), TestReplicant] = ArgList(next)
    
    argList.assertCalledOnce()
    next.called should equal(true)
    
    next.shouldFail = true
    intercept[TestFailedException] { argList.assertCalledOnce() } should be theSameInstanceAs(testFailedException)
  } 
  
  @Test def testAssertAllResponsesUsed {
    val next = new TestReplicant {
      override def assertAllResponsesUsed() {
        called = true
        if (shouldFail) throw testFailedException
      }
      var called = false
      var shouldFail = false
    }
    val argList: ArgList[(Int, String), TestReplicant] = ArgList(next)
    
    argList.assertAllResponsesUsed()
    next.called should equal(true)
    
    next.shouldFail = true
    intercept[TestFailedException] { argList.assertAllResponsesUsed() } should be theSameInstanceAs(testFailedException)
  } 

  @Test def testArgListEquality {
    val next1 = new TestReplicant
    val next2 = new TestReplicant
    testEqualityOf(   ArgList(next1) ).
      shouldEqual(    ArgList(next1) ).
      shouldNotEqual( ArgList(next2) ).
      shouldNotEqual( "not an ArgList" )
  } 
  
}
