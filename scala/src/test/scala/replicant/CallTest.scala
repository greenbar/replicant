// Copyright 2011 Kiel Hodges
package replicant

import org.scalatest._
import org.scalatest.matchers._
import org.scalatest.junit.JUnitRunner
import org.junit._
import org.junit.runner._
import testing._

@RunWith(classOf[JUnitRunner])
class CallTest extends junit.JUnit3Suite with ShouldMatchers {

  case class Mock(name: String) 
  val mock1 = Mock("mock1")
  val mock2 = Mock("mock2")
  
  @Test def testCallWithNoArgList {
    val call = Call(mock1, "methodA")
    
    call.mock       should equal(mock1)
    call.methodName should equal("methodA")
    call.argLists   should equal(Nil)
    call.toString should equal(mock1 + ".methodA")
  } 
  
  @Test def testCallWithOneArgList {
    val call = Call(mock1, "methodA")(1, "abc")
    
    call.mock       should equal(mock1)
    call.methodName should equal("methodA")
    call.argLists   should equal(List(ArgList(1, "abc")))
    call.toString   should equal(mock1 + ".methodA" + ArgList((1, "abc")))
  } 
  
  @Test def testCallWithSeveralArgLists {
    val call = Call(mock1, "methodA")(1, "abc")(A(1), B(2))(7)
    
    call.mock       should equal(mock1)
    call.methodName should equal("methodA")
    call.argLists   should equal(List(ArgList(1, "abc"), ArgList(A(1), B(2)), ArgList(7)))
    call.toString   should equal(mock1 + ".methodA" + ArgList((1, "abc")) + ArgList(A(1), B(2)) + ArgList(7))
  } 
  
  @Test def testCallEquality {
    testEqualityOf(   Call(mock1, "methodA") ).
      shouldEqual(    Call(mock1, "methodA") ).
      shouldNotEqual( Call(mock2, "methodA") ).
      shouldNotEqual( Call(mock1, "methodB") ).
      shouldNotEqual( Call(mock1, "methodA")(1, "abc") ).
      shouldNotEqual( "not a Call" )
    testEqualityOf(   Call(mock1, "methodA")(1, "abc")(2) ).
      shouldEqual(    Call(mock1, "methodA")(1, "abc")(2) ).
      shouldNotEqual( Call(mock2, "methodA")(1, "abc")(2) ).
      shouldNotEqual( Call(mock1, "methodB")(1, "abc")(2) ).
      shouldNotEqual( Call(mock1, "methodA")(2, "abc")(2) ).
      shouldNotEqual( Call(mock1, "methodA")(1, "xyz")(2) ).
      shouldNotEqual( Call(mock1, "methodB")(1, "abc")(1) ).
      shouldNotEqual( Call(mock1, "methodA")(1)           ).
      shouldNotEqual( Call(mock1, "methodA")              ).
      shouldNotEqual( "not a Call"                        )
  }

  @Test def testDescribe {
    val call1 = Call(mock1, "methodA")(1, "a")
    val call2 = Call(mock1, "methodA")(2, "b")
    val call3 = Call(mock1, "methodA")(3, "c")
    val emptyAlternative = "emptyAlternative"
      
    Call.describe(Traversable()) should equal("no calls")
    Call.describe(Traversable(call1, call2, call3)) should 
      equal("\n  " + call1 + "\n  " + call2 + "\n  " + call3)
  } 

}