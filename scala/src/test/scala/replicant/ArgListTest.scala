// Copyright 2011 Kiel Hodges
package replicant

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
  
  class TestReplicant extends Replicant[TestReplicant] {
    private[replicant] def withArgs[NewArgs](args: NewArgs) = {
      args should equal((1, "a"))
      withArgs
    }
    var withArgs: TestReplicant = _
  }
  
  @Test def testWithArgs {
    val next = new TestReplicant
    next.withArgs = new TestReplicant
    val argList: ArgList[(Int, String), TestReplicant] = ArgList(next)
    
    val argListWithArgs: ArgList[(Int, String), TestReplicant] = argList.withArgs((1, "a")) 
    
    argListWithArgs should equal(ArgList(next.withArgs))
  } 

  @Test def testArgListApply {
    val next = new TestReplicant
    next.withArgs = new TestReplicant
    val argList: ArgList[(Int, String), TestReplicant] = ArgList(next)
    
    argList(1, "a") should equal(next.withArgs)
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
