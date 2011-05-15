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
  
  @Test def testCallApply {
    val call1 = Call(mock1, "methodA")
    val call2 = call1(1, "abc")
    val call3 = call2(A(1), B(2))(7)

    call1 should equal(Call(mock1, "methodA"))
    call2 should equal(Call(mock1, "methodA")(1, "abc"))
    call3 should equal(Call(mock1, "methodA")(1, "abc")(A(1), B(2))(7)) 
  } 
  
  @Test def testCallToString {
    Call(mock1, "methodA").toString                          should equal(mock1 + ".methodA")
    Call(mock1, "methodA")(1, "abc").toString                should equal(mock1 + ".methodA" + 
                                                                          ArgListValue((1, "abc")))
    Call(mock1, "methodA")(1, "abc")(A(1), B(2))(7).toString should equal(mock1 + ".methodA" + 
                                                                          ArgListValue((1, "abc")) + 
                                                                          ArgListValue(A(1), B(2)) + 
                                                                          ArgListValue(7))
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