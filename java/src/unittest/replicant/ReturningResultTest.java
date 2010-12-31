// Copyright 2009 Kiel Hodges
package replicant;

import junit.framework.*;
import replicant.testing.testobjects.TestValue.*;

public class ReturningResultTest extends TestCase {

  private static final A RETURN_VALUE       = new A(1);
  private static final A OTHER_RETURN_VALUE = new A(2);

  public void testResult() throws Exception {
    ReturningResult<A> response = new ReturningResult<A>(RETURN_VALUE);
    
    A returnValue = response.returnValue();
    
    assertEquals(RETURN_VALUE, returnValue);
  }
  
  public void testEquality() throws Exception {
    ReturningResult<A> response = new ReturningResult<A>(RETURN_VALUE);

    assertEquals(false, response.equals(null));
    assertEquals(false, response.equals("not a ResultResponse"));
    assertEquals(false, response.equals(new ReturningResult<A>(OTHER_RETURN_VALUE)));
    assertEquals(true,  response.equals(new ReturningResult<A>(RETURN_VALUE)));
    assertEquals(true,  response.equals(response));
    assertEquals(true,  new ReturningResult<A>(null).equals(new ReturningResult<A>(null)));
    assertEquals(false, response.equals(new ReturningResult<A>(null)));
    assertEquals(false, new ReturningResult<A>(null).equals(response));
  }
  
}
