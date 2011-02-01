// Copyright 2011 Kiel Hodges
package replicant

import org.scalatest._
import org.scalatest.matchers._
import org.scalatest.junit.JUnitRunner
import org.junit._
import org.junit.runner._
import testing._

@RunWith(classOf[JUnitRunner])
class PackageTest extends junit.JUnit3Suite with ShouldMatchers {
 
  @Test def testImplicitResponseFallbacks {
    verifyUnitResponseFallback
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
  
  private def verifyUnitResponseFallback(implicit responseFallback: ResponseFallback[Unit]) {
    val exception = new UnknownResponseException("testing")
    responseFallback(exception)() should equal(())
  } 
  
  private def verifyNoResponseFallback[T](implicit responseFallback: ResponseFallback[T]) {
    val exception = new UnknownResponseException("testing")
    intercept[UnknownResponseException] { 
      responseFallback(exception)
    } should be theSameInstanceAs(exception)
  } 
  
}
