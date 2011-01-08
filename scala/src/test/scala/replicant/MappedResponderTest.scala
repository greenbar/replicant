// Copyright 2011 Kiel Hodges
package replicant

import org.scalatest._
import org.scalatest.matchers._
import org.scalatest.junit.JUnitRunner
import org.junit._
import org.junit.runner._
import testing._


@RunWith(classOf[JUnitRunner])
class MappedResponderTest extends junit.JUnit3Suite with ShouldMatchers {
 
  case class A(value: Int) 
   
  case class Mock(name: String) 
  val mock = Mock("aMock")
  
  @Test def testMappedResponder {
    val responder = new MappedResponder[A]
    val call1 = Call(mock, "methodA")(1, "abc")
    val call2 = Call(mock, "methodA")(2, "xyz")
    val call3 = Call(mock, "methodA")(2, "abc")
    
    responder(call1) = () => A(1)
    responder(call2) = () => A(2)

    responder(call1).right.get.apply() should equal(A(1))
    responder(call2).right.get.apply() should equal(A(2))
    responder(call3).left.get          should equal(new UnknownResponseException("No response expected for " + call3))
  } 
  
}