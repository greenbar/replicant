// Copyright 2011 Kiel Hodges
package replicant

import org.scalatest._
import org.scalatest.matchers._
import org.scalatest.junit.JUnitRunner
import org.junit._
import org.junit.runner._
import testing._

@RunWith(classOf[JUnitRunner])
class MockMinderTest extends junit.JUnit3Suite with ShouldMatchers {

  trait Foo {
    def noArgMethod: A
    def oneArgMethod(arg: A): B
    def twoArgMethod(arg1: A, arg2: B): C
  }

  class FooMinder extends MockMinder[Foo] { minder =>

    val mock: Foo = new Foo {
      def noArgMethod                    = minder.noArgMethod()
      def oneArgMethod(arg: A)           = minder.oneArgMethod(arg)
      def twoArgMethod(arg1: A, arg2: B) = minder.twoArgMethod(arg1, arg2)
    }

    val noArgMethod  = method("noArgMethod",  mock.noArgMethod _)
    val oneArgMethod = method("oneArgMethod", mock.oneArgMethod _)
    val twoArgMethod = method("twoArgMethod", mock.twoArgMethod _)
  }

  @Test def testControllerWithNoArgMethod {
    val minder = new FooMinder
    
    val noArgMethod: Mocker0[A] = minder.noArgMethod

    minder.noArgMethod should equal(Mocker0[A](minder.mock, "noArgMethod"))
    
    minder.noArgMethod.expect { A(7) }
    minder.mock.noArgMethod should equal(A(7))
  } 
  
  @Test def testControllerWithOneArgMethod {
    val minder = new FooMinder
    
    val oneArgMethod: Mocker[A, B] = minder.oneArgMethod
    
    minder.oneArgMethod should equal(Mocker[A, B](minder.mock, "oneArgMethod"))

    minder.oneArgMethod.expect(A(3)) { B(7) }
    minder.mock.oneArgMethod(A(3)) should equal(B(7))
  } 
  
  @Test def testControllerWithTwoArgMethod {
    val minder = new FooMinder
    
    val twoArgMethod: Mocker[(A, B), C] = minder.twoArgMethod
    
    minder.twoArgMethod should equal(Mocker[(A, B), C](minder.mock, "twoArgMethod"))

    minder.twoArgMethod.expect(A(3), B(7)) { C(11) }
    minder.mock.twoArgMethod(A(3), B(7)) should equal(C(11))
  } 
  
}