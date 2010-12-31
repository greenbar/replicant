// Copyright 2009 Kiel Hodges
package replicant;

import junit.framework.*;
import replicant.testing.testobjects.TestValue.*;
import static replicant.collections.CollectionFunctions.*;

public class StandardResponsePolicyTest extends TestCase {

  private static final Call            CALL     = new Call(new Mock(), "method", list(1, 2, 3));
  private static final TestResult<A> RESPONSE = new TestResult<A>();
  
  private StandardResponsePolicy<A> policy;
  
  public void setUp() throws Exception {
    policy = StandardResponsePolicy.instance();
  }
  
  public void testCreateExpectedResponse() throws Exception {
    StandardResponse<A> response = policy.createExpectedResponse(CALL);
    
    assertEquals(new StandardResponse<A>(CALL), response);
  }
  
  public void testResponseForWhenResponderReturnsResponse() throws Exception {
    Responder<A> responder = new TestResponder<A>() {
      public Result<A> resultFor(Call call) {
        return RESPONSE;
      }
    };
    
    Result<A> result = policy.resultFor(CALL, responder);
    
    assertEquals(RESPONSE, result);
  }

  public void testResponseForWhenResponderThrowsUnknownResponseException() throws Exception {
    final UnknownResponseException unknownResponseException = new UnknownResponseException("test");
    Responder<A> responder = new TestResponder<A>() {
      public Result<A> resultFor(Call call) {
        throw unknownResponseException;
      }
    };
    
    try {
      policy.resultFor(CALL, responder);
      fail("Expected an UnknownResponseException");
    } catch (UnknownResponseException e) {
      assertEquals(unknownResponseException, e);
    }
  }
  
  public void testResponseForWhenResponderThrowsOtherException() throws Exception {
    final RuntimeException exception = new RuntimeException("test");
    Responder<A> responder = new TestResponder<A>() {
      public Result<A> resultFor(Call call) {
        throw exception;
      }
    };
    
    try {
      policy.resultFor(CALL, responder);
      fail("Expected an RuntimeException");
    } catch (RuntimeException e) {
      assertEquals(exception, e);
    }
  }
  
  public void testEquality() throws Exception {
    assertEquals(false, StandardResponsePolicy.instance().equals(null));
    assertEquals(false, StandardResponsePolicy.instance().equals(VoidResponsePolicy.instance()));
    assertEquals(true,  StandardResponsePolicy.instance().equals(StandardResponsePolicy.instance()));
  }

  private static final class TestResult<ReturnValue> implements Result<ReturnValue> {
    public ReturnValue returnValue() throws ResponseException {
      return null;
    }
  }

  private static abstract class TestResponder<ReturnValue> implements Responder<ReturnValue> {
    public void addResponseFor(Call call, Result<ReturnValue> result) {
    }
  }

}
