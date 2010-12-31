// Copyright 2009 Kiel Hodges
package replicant;

import junit.framework.*;
import replicant.testing.testobjects.TestValue.*;

public class ThrowingResultTest extends TestCase {
  
  public void testResultWithCheckedException() throws Exception {
    Exception exception = new Exception("checked");
    ThrowingResult<A> result = new ThrowingResult<A>(exception);

    try {
      result.returnValue();
      fail("Expected a ResponseException.");
    } catch(ResponseException e) {
      assertEquals(new ResponseException(exception), e);
    }
  }
  
  public void testResultWithUncheckedException() throws Exception {
    Exception exception = new RuntimeException("unchecked");
    ThrowingResult<A> result = new ThrowingResult<A>(exception);
    
    try {
      result.returnValue();
      fail("Expected an Exception.");
    } catch(Exception e) {
      assertEquals(exception, e);
    }
  }
  
  public void testEquality() throws Exception {
    Exception exception = new Exception("exception");
    ThrowingResult<A> result = new ThrowingResult<A>(exception);
    
    assertEquals(false, result.equals(null));
    assertEquals(false, result.equals("not a ThrowingResult"));
    assertEquals(false, result.equals(new ThrowingResult<A>(new Exception("other"))));
    assertEquals(true,  result.equals(new ThrowingResult<A>(exception)));
    assertEquals(true,  result.equals(result));
  }

}
