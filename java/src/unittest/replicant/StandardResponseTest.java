// Copyright 2009 Kiel Hodges
package replicant;

import java.util.*;

import junit.framework.*;
import replicant.testing.testobjects.TestValue.*;
import static replicant.collections.CollectionFunctions.*;

public class StandardResponseTest extends TestCase {

  private static final A         RETURN_VALUE       = new A(1);
  private static final A         OTHER_RETURN_VALUE = new A(123);
  private static final Exception EXCEPTION          = new Exception("test");
  private static final Exception OTHER_EXCEPTION    = new Exception("other");
  private static final Call      CALL               = new Call(new Mock(), "method", list(1, 2, 3));
  private static final Call      OTHER_CALL         = new Call(new Mock(), "other", list(1, 2, 3));

  private StandardResponse<A> response;

  public void setUp() throws Exception {
    response = new StandardResponse<A>(CALL);
  }
  
  public void testResponseWithNoReturnValue() throws Exception {
    try {
      response.returnValue();
      fail("Expected a TestingException");
    } catch (TestingException e) {
      assertEquals("There is no response for " + CALL + '.', e.getMessage());
    }
  }

  public void testResponseWithReturnValue() throws Exception {
    response.returning(RETURN_VALUE);
    
    A returnValue = response.returnValue();
    
    assertEquals(RETURN_VALUE, returnValue);
  }
  
  public void testChecks() throws Exception {
    final RuntimeException exception2 = new RuntimeException("exception2");
    final RuntimeException exception3 = new RuntimeException("exception3");
    response.
      checking(new Check() {
        public void check() {
        }
      }).
      checking(new Check() {
        public void check() {
          throw exception2;
        }
      }).
      checking(new Check() {
        public void check() {
          throw exception3;
        }
      }).
      returning(RETURN_VALUE);
    
    try {
      response.returnValue();
      fail("Expected an RuntimeException.");
    } catch (RuntimeException e) {
      assertSame(exception2, e);
    }
  }
  
  public void testEvents() throws Exception {
    final List<String> events = list();
    response.
      triggering(new Event() {
        public void fire() {
          events.add("event1");
        }
      }).
      triggering(new Event() {
        public void fire() {
          events.add("event2");
        }
      }).
      returning(RETURN_VALUE);
    assertEquals(list(), events);
    
    response.returnValue();
    
    assertEquals(list("event1", "event2"), events);
  }
  
  public void testOrderOfChecksAndEvents() throws Exception {
    final List<String> events = list();
    response.
      checking(new Check() {
        public void check() {
          assertEquals(list(), events);
        }
      }).
      triggering(new Event() {
        public void fire() {
          events.add("event1");
        }
      }).
      checking(new Check() {
        public void check() {
          assertEquals(list("event1"), events);
        }
      }).
      triggering(new Event() {
        public void fire() {
          events.add("event2");
        }
      }).
      checking(new Check() {
        public void check() {
          assertEquals(list("event1", "event2"), events);
        }
      }).
      returning(RETURN_VALUE);
    
    assertEquals(list(), events);
    
    response.returnValue();
    assertEquals(list("event1", "event2"), events);
  }
  
  public void testResponseWithCheckedException() throws Exception {
    Exception exception = new Exception("checked");
    response.throwing(exception);

    try {
      response.returnValue();
      fail("Expected a ResponseException.");
    } catch(ResponseException e) {
      assertEquals(new ResponseException(exception), e);
    }
  }
  
  public void testResponseWithUncheckedException() throws Exception {
    Exception exception = new RuntimeException("unchecked");
    response.throwing(exception);
    
    try {
      response.returnValue();
      fail("Expected an Exception.");
    } catch(Exception e) {
      assertEquals(exception, e);
    }
  }
  
  public void testEquality() throws Exception {
    assertEquals(false, response.equals(null));
    assertEquals(false, response.equals("not a ResponseAdder"));
    assertEquals(false, response.equals(new StandardResponse<A>(OTHER_CALL)));
    assertEquals(true,  response.equals(new StandardResponse<A>(CALL)));
    assertEquals(true,  response.equals(response));
    
    StandardResponse<A> returnValueResponse      = new StandardResponse<A>(CALL);
    StandardResponse<A> sameReturnValueResponse  = new StandardResponse<A>(CALL);
    StandardResponse<A> otherReturnValueResponse = new StandardResponse<A>(CALL);
    returnValueResponse.returning(RETURN_VALUE);
    sameReturnValueResponse.returning(RETURN_VALUE);
    otherReturnValueResponse.returning(OTHER_RETURN_VALUE);
    assertEquals(true,  returnValueResponse.equals(sameReturnValueResponse));
    assertEquals(false, returnValueResponse.equals(otherReturnValueResponse));
    
    StandardResponse<A> throwingResponse      = new StandardResponse<A>(CALL);
    StandardResponse<A> sameThrowingResponse  = new StandardResponse<A>(CALL);
    StandardResponse<A> otherThrowingResponse = new StandardResponse<A>(CALL);
    throwingResponse.throwing(EXCEPTION);
    sameThrowingResponse.throwing(EXCEPTION);
    otherThrowingResponse.throwing(OTHER_EXCEPTION);
    assertEquals(true,  throwingResponse.equals(sameThrowingResponse));
    assertEquals(false, throwingResponse.equals(otherThrowingResponse));
    assertEquals(false, throwingResponse.equals(returnValueResponse));
    
    assertEquals(false, response.equals(returnValueResponse));
    assertEquals(false, response.equals(throwingResponse));
    assertEquals(false, returnValueResponse.equals(response));
    assertEquals(false, throwingResponse.equals(response));
  }
  
}
