// Copyright 2011 Kiel Hodges
package replicant

import org.scalatest._
import org.scalatest.matchers._
import org.scalatest.junit.JUnitRunner
import org.junit._
import org.junit.runner._
import testing._

@RunWith(classOf[JUnitRunner])
class UnknownResponseExceptionTest extends junit.JUnit3Suite with ShouldMatchers {
 
  private case class A(value: Int) 
  
  @Test def testUnknownResponseException {
    val exception = new UnknownResponseException("message");
    
    exception.getMessage should equal("message")
    exception should equal(    new UnknownResponseException("message"))
    exception should not equal(new UnknownResponseException("other message"))
    exception should not equal(new RuntimeException("message"))
    exception.hashCode() should equal(new UnknownResponseException("message").hashCode())
  } 
  
}
