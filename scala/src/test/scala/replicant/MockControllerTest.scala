// Copyright 2011 Kiel Hodges
package replicant

import org.scalatest._
import org.scalatest.matchers._
import org.scalatest.junit.JUnitRunner
import org.junit._
import org.junit.runner._
import testing._

@RunWith(classOf[JUnitRunner])
class MockControllerTest extends junit.JUnit3Suite with ShouldMatchers {

  trait Foo {
    def noArgMethod: A
    def oneArgMethod(arg: A): B
    def twoArgMethod(arg1: A, arg2: B): C
  }

  class FooMockController extends MockController[Foo] { controller =>

    val mock: Foo = new Foo {
      def noArgMethod                    = controller.noArgMethod()
      def oneArgMethod(arg: A)           = controller.oneArgMethod(arg)
      def twoArgMethod(arg1: A, arg2: B) = controller.twoArgMethod(arg1, arg2)
    }

    val noArgMethod  = method("noArgMethod",  mock.noArgMethod _)
    val oneArgMethod = method("oneArgMethod", mock.oneArgMethod _)
    val twoArgMethod = method("twoArgMethod", mock.twoArgMethod _)
  }

  @Test def testControllerWithNoArgMethod {
    val mockController = new FooMockController
    
    val noArgMethod: Mocker0[A] = mockController.noArgMethod

    mockController.noArgMethod should equal(Mocker0[A](mockController.mock, "noArgMethod"))
    
    mockController.noArgMethod.expect { A(7) }
    mockController.mock.noArgMethod should equal(A(7))
  } 
  
  @Test def testControllerWithOneArgMethod {
    val mockController = new FooMockController
    
    val oneArgMethod: Mocker[A, B] = mockController.oneArgMethod
    
    mockController.oneArgMethod should equal(Mocker[A, B](mockController.mock, "oneArgMethod"))

    mockController.oneArgMethod.expect(A(3)) { B(7) }
    mockController.mock.oneArgMethod(A(3)) should equal(B(7))
  } 
  
  @Test def testControllerWithTwoArgMethod {
    val mockController = new FooMockController
    
    val twoArgMethod: Mocker[(A, B), C] = mockController.twoArgMethod
    
    mockController.twoArgMethod should equal(Mocker[(A, B), C](mockController.mock, "twoArgMethod"))

    mockController.twoArgMethod.expect(A(3), B(7)) { C(11) }
    mockController.mock.twoArgMethod(A(3), B(7)) should equal(C(11))
  } 
  
}