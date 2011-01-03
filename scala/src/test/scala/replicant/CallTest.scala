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
  
  @Test def testToStringWithMultipleArgs {
    val call: Call[(Int, String)] = Call(mock1, "methodA", (1, "abc"))
    
    call.toString should equal(mock1 + ".methodA(1, abc)")
  } 
  
  @Test def testToStringWithSingleArg {
    val call: Call[Int] = Call(mock1, "methodA", (1))
  
    call.toString should equal(mock1 + ".methodA(1)")
  } 
  
  @Test def testToStringWithNoArgs {
    val call: Call[Unit] = Call(mock1, "methodA", ())
  
    call.toString should equal(mock1 + ".methodA()")
  } 
  
  @Test def testCallEquality {
    testEqualityOf(   Call(mock1, "methodA", (1, "abc")) ).
      shouldEqual(    Call(mock1, "methodA", (1, "abc")) ).
      shouldNotEqual( Call(mock2, "methodA", (1, "abc")) ).
      shouldNotEqual( Call(mock1, "methodB", (1, "abc")) ).
      shouldNotEqual( Call(mock1, "methodA", (2, "abc")) ).
      shouldNotEqual( Call(mock1, "methodA", (1, "xyz")) ).
      shouldNotEqual( Call(mock1, "methodA", (1))        ).
      shouldNotEqual( "not a Call"                          )
  }
}