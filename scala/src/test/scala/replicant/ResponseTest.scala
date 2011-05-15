// Copyright 2011 Kiel Hodges
package replicant

import org.scalatest._
import org.scalatest.matchers._
import org.scalatest.junit.JUnitRunner
import org.junit._
import org.junit.runner._
import testing._

@RunWith(classOf[JUnitRunner])
class ResponseTest extends junit.JUnit3Suite with ShouldMatchers {

  private val result1 = A(1)
  private val result2 = A(2)
  private val error1 = "error1"
  private val error2 = "error2"

  @Test def testValueResponseWithNoFallback {
    val response: Response[A] = ValueResponse(() => result1)
    
    response.value(NoResponse) should be(result1)
  } 
  
  @Test def testValueResponseThatThrows {
    val response: Response[A] = ValueResponse(() => throw TestException)
    
    intercept[TestException] { 
      response.value(NoResponse)
    } should be(TestException)
  } 
  
  @Test def testValueResponseWithUnitFallback {
    var x = ""
    val response: Response[Unit] = ValueResponse(() => x = "got it")
  
    response.value(UnitFallback)
    x should be("got it")
  } 

  @Test def testUnknownResponseWithNoFallback {
    val response: Response[A] = UnknownResponse(error1)
  
    intercept[UnknownResponseException] { 
      response.value(NoResponse)
    } should equal(new UnknownResponseException(error1))
  } 
  
  @Test def testUnknownResponseWithUnitFallback {
    val response: Response[Unit] = UnknownResponse(error1)
    
    response.value(UnitFallback) should be(())
  } 

  @Test def testResponseEquality {
    val f1 = () => result1
    val f2 = () => result2

    testEqualityOf(   ValueResponse(f1)  ).
      shouldEqual(    ValueResponse(f1)  ).
      shouldNotEqual( ValueResponse(f2)  ).
      shouldNotEqual( UnknownResponse(error1) )

    testEqualityOf(   UnknownResponse(error1) ).
      shouldEqual(    UnknownResponse(error1) ).
      shouldNotEqual( UnknownResponse(error2) ).
      shouldNotEqual( ValueResponse(f1)  )
  }
  

}