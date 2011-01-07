// Copyright 2011 Kiel Hodges
package replicant

import org.scalatest._
import org.scalatest.matchers._
import org.scalatest.junit.JUnitRunner
import org.junit._
import org.junit.runner._
import testing._

@RunWith(classOf[JUnitRunner])
class ArgListTest extends junit.JUnit3Suite with ShouldMatchers {

  case class A(value: Int)
  case class B(value: Int)
  
  @Test def testToStringWithMultipleArgs {
    val argList: ArgList = ArgList(1, "abc")
    
    argList.toString should equal("(1, abc)")
  } 
  
  @Test def testToStringWithSingleArg {
    val argList: ArgList = ArgList(1)
  
    argList.toString should equal("(1)")
  } 
  
  @Test def testToStringWithNoArgs {
    val argList: ArgList = ArgList()
  
    argList.toString should equal("()")
  } 
  
  @Test def testArgListEquality {
    testEqualityOf(   ArgList(1, "abc") ).
      shouldEqual(    ArgList(1, "abc") ).
      shouldNotEqual( ArgList(2, "abc") ).
      shouldNotEqual( ArgList(1, "xyz") ).
      shouldNotEqual( ArgList(1)        ).
      shouldNotEqual( ArgList()         ).
      shouldNotEqual( "not an ArgList"                          )
  }
 
}