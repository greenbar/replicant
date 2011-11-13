// Copyright 2011 Kiel Hodges
package replicant

import org.scalatest._
import org.scalatest.matchers._
import org.scalatest.junit.JUnitRunner
import org.junit._
import org.junit.runner._
import testing._

@RunWith(classOf[JUnitRunner])
class ResponseFallbackTest extends junit.JUnit3Suite with ShouldMatchers {
 
  @Test def testImplicitResponseFallbacks {

    implicitly[ResponseFallback[Unit]].apply(message) should equal(())

    verifyNoResponseFallback[Boolean]
    verifyNoResponseFallback[Char   ]
    verifyNoResponseFallback[Byte   ]
    verifyNoResponseFallback[Short  ]
    verifyNoResponseFallback[Int    ]
    verifyNoResponseFallback[Long   ]
    verifyNoResponseFallback[Float  ]
    verifyNoResponseFallback[Double ]
    verifyNoResponseFallback[AnyRef ]
    verifyNoResponseFallback[A      ]
  } 
  
  private val message = "message"
    
  private def verifyNoResponseFallback[Result: ResponseFallback] {
    intercept[UnknownResponseException] { 
      implicitly[ResponseFallback[Result]].apply(message)
    } should equal(new UnknownResponseException(message))
  } 
  
}
