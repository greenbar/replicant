// Copyright 2009 Kiel Hodges
package replicant.testing.testobjects;


public class TestValue {

  private final int value;

  public TestValue(int value) {
    this.value = value;
  }
  
  public int value() {
    return value;
  }
  
  public boolean equals(Object object) {
    if (object instanceof TestValue) {
      TestValue that = (TestValue) object;
      return this.value == that.value && this.getClass().equals(that.getClass());
    }
    return false;
  }
  
  public int hashCode() {
    return value;
  }
  
  public String toString() {
    String className = getClass().getName();
    int indexOfLastDollar = className.lastIndexOf('$');
    String shortClassName = className.substring(indexOfLastDollar + 1);
    return shortClassName + '(' + value + ')';
  }
  
  public static class SupertypeOfA extends TestValue { 
    public SupertypeOfA(int value)  {
      super(value); 
    } 
  }
  
  public static class X extends TestValue { 
    public X(int value)  {
      super(value); 
    } 
  }
  
  public static class A extends SupertypeOfA { 
    public A(int value) { 
      super(value); 
    } 
  }
  
  public static class SupertypeOfB extends TestValue { 
    public SupertypeOfB(int value)  {
      super(value); 
    } 
  }
  
  public static class B extends SupertypeOfB { 
    public B(int value) { 
      super(value); 
    } 
  }
  
  public static class C extends TestValue { 
    public C(int value)  {
      super(value); 
    } 
  }
  
  public static class D extends TestValue { 
    public D(int value)  {
      super(value); 
    } 
  }
  
  public static class E extends TestValue { 
    public E(int value)  {
      super(value); 
    } 
  }
  
}
