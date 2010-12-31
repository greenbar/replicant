// Copyright 2009 Kiel Hodges
package replicant;

import java.util.*;

import junit.framework.*;
import replicant.testing.testobjects.TestValue.*;
import static replicant.collections.CollectionFunctions.*;

public class CallTest extends TestCase {

  private static final A A = new A(7);

  public void testToString() throws Exception {
    Object mock = "aMock";
    Call call = new Call(mock, "someMethod", list(1, "a", A));
    
    assertEquals("aMock.someMethod(1, a, " + A + ')', call.toString());
  }
  
  public void testArgumentTypeFlexibiliy() throws Exception {
    List<A> arguments = list(A);
    new Call("aMock", "someMethod", arguments);
  }
  
  public void testEquality() throws Exception {
    Call call = new Call("aMock", "someMethod", list(1, "a", A));
    
    assertEquals(false, call.equals(null));
    assertEquals(false, call.equals("not a Call"));
    assertEquals(false, call.equals(new Call("another", "someMethod", list(1, "a", A))));
    assertEquals(false, call.equals(new Call("aMock",   "another",    list(1, "a", A))));
    assertEquals(false, call.equals(new Call("aMock",   "someMethod", list(1, "another", A))));
    assertEquals(true,  call.equals(call));
    assertEquals(true,  call.equals(new Call("aMock",   "someMethod", list(1, "a", A))));
  }
  
}
