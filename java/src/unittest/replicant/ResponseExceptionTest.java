// Copyright 2009 Kiel Hodges
package replicant;

import junit.framework.*;

public class ResponseExceptionTest extends TestCase {
  
  public void testResponseException() throws Exception {
    Throwable throwable = new FooException();
    
    ResponseException responseException = new ResponseException(throwable);
    
    assertEquals("Programmed response", responseException.getMessage());
    assertSame(responseException, responseException.throwCauseIf(BarException.class));
    try {
      responseException.throwCauseIf(FooException.class);
      fail("Expected a FooException");
    } catch (FooException e) {
      assertSame(throwable, e);
    }
    try {
      responseException.throwSelf();
      fail("Expected a ResponseException");
    } catch (ResponseException e) {
      assertSame(responseException, e);
    }
  }
 
  public void testEquality() throws Exception {
    Throwable throwable = new Throwable("throwable");
    ResponseException exception = new ResponseException(throwable);

    assertEquals(false, exception.equals(null));
    assertEquals(false, exception.equals("not a ResponseException"));
    assertEquals(false, exception.equals(new ResponseException(new Throwable("throwable"))));
    assertEquals(true,  exception.equals(new ResponseException(throwable)));
    assertEquals(true,  exception.equals(exception));
  }

  private static class FooException extends Exception {
    private static final long serialVersionUID = 1L;
  }

  private static class BarException extends Exception {
    private static final long serialVersionUID = 1L;
  }
  
}
