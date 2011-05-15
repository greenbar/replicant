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

  @Test def testValueResponseWithNoFallback {
    val response: Response[A] = new ValueResponse(() => result1)
    
    response.value(NoResponse) should be(result1)
  } 
  
  @Test def testValueResponseWithUnitFallback {
    var x = ""
    val response: Response[Unit] = new ValueResponse(() => x = "got it")
  
    response.value(UnitFallback)
    x should be("got it")
  } 
  
  val errorDescription = "errorDescription"

  @Test def testUnknownResponseWithNoFallback {
    val response: Response[A] = new UnknownResponse(errorDescription)
  
    intercept[UnknownResponseException] { 
      response.value(NoResponse)
    } should equal(new UnknownResponseException(errorDescription))

  } 
  
  @Test def testUnknownResponseWithUnitFallback {
    val response: Response[Unit] = new UnknownResponse(errorDescription)
    
    response.value(UnitFallback) should be(())
  } 
  
}