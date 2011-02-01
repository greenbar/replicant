// Copyright 2011 Kiel Hodges
package object testing extends org.scalatest.Assertions {

  class TestException(message: String) extends Exception(message)
  
  trait NotImplemented {
    protected def notImplemented = fail("not implemented")
  }
  
  import scala.reflect.Manifest
  import scala.reflect.ClassManifest

  private class TypeMatcher[Type](manifest: Manifest[Type]) {
    def unapply(value: AnyRef) = 
      if (ClassManifest.singleType(value) <:< manifest) 
        Some(value.asInstanceOf[Type]) 
      else 
        None
  }
  
  def assertThrows[Type <: Throwable](code: => Unit)(implicit exceptionType: Manifest[Type]): Type = {
    val expectedException = new TypeMatcher(exceptionType)
    try {
      code
      fail("Expected " + exceptionType + ", but nothing was thrown.")
    } catch {
      case expectedException(e) => e 
      case e: Exception         => fail("Expected " + exceptionType + ", but caught " + e)
    }
  }

  class EqualityTester(subject: AnyRef) {
    
    def shouldEqual(other: AnyRef): EqualityTester = {
      assertObjectsEqual(other, subject)
      assertObjectsEqual(subject, other)
      assertHashesEqual(other, subject)
      this
    }
    
    def shouldNotEqual(other: AnyRef): EqualityTester = {
      assertObjectsNotEqual(other, subject)
      assertObjectsNotEqual(subject, other)
      this
    }
    
    private def assertObjectsEqual(object1: AnyRef, object2: AnyRef) {
      if (object1 != object2) fail("Expected " + object1 + " == " + object2)
    }
    
    private def assertHashesEqual(object1: AnyRef, object2: AnyRef) {
      val hash1 = object1.hashCode
      val hash2 = object2.hashCode 
      if (hash1 != hash2) 
        fail("Expected " + object1 + ".hashCode == " + object2 + ".hashCode, but " + hash1 + " != " + hash2)
    }
  
    private def assertObjectsNotEqual(object1: AnyRef, object2: AnyRef) {
      if (object1 == object2) fail("Expected " + object1 + " != " + object2)
    }
    
  }
  
  def testEqualityOf(subject: AnyRef) = new EqualityTester(subject)

}