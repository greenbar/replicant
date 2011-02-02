// Copyright 2011 Kiel Hodges
package replicant

import org.scalatest._
import org.scalatest.matchers._
import org.scalatest.junit.JUnitRunner
import org.junit._
import org.junit.runner._
import testing._

@RunWith(classOf[JUnitRunner])
class ArgListValueTest extends junit.JUnit3Suite with ShouldMatchers {

  @Test def testToStringWithMultipleArgs {
    val argList: ArgListValue = ArgListValue(1, "abc")
    
    argList.toString should equal("(1, abc)")
  } 
  
  @Test def testToStringWithSingleArg {
    val argList: ArgListValue = ArgListValue(1)
  
    argList.toString should equal("(1)")
  } 
  
  @Test def testToStringWithNoArgs {
    val argList: ArgListValue = ArgListValue()
  
    argList.toString should equal("()")
  } 
  
  @Test def testArgListValueEquality {
    testEqualityOf(   ArgListValue(1, "abc") ).
      shouldEqual(    ArgListValue(1, "abc") ).
      shouldNotEqual( ArgListValue(2, "abc") ).
      shouldNotEqual( ArgListValue(1, "xyz") ).
      shouldNotEqual( ArgListValue(1)        ).
      shouldNotEqual( ArgListValue()         ).
      shouldNotEqual( "not an ArgListValue"                          )
  }
 
}