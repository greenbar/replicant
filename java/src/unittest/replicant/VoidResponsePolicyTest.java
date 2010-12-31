// Copyright 2009 Kiel Hodges
package replicant;

import junit.framework.*;
import static replicant.collections.CollectionFunctions.*;

public class VoidResponsePolicyTest extends TestCase {

  private static final TestResult<Void> RESULT = new TestResult<Void>();
  private static final Call               CALL     = new Call(new Mock(), "method", list(1, 2, 3));
  private VoidResponsePolicy policy;
  
  public void setUp() throws Exception {
    policy = VoidResponsePolicy.instance();
  }
  
  public void testCreateExpectedResponse() throws Exception {
    
    StandardResponse<Void> response = policy.createExpectedResponse(CALL);
    
    StandardResponse<Void> expectedResponse = new StandardResponse<Void>(CALL);
    expectedResponse.returning(null);
    assertEquals(expectedResponse, response);
  }

  public void testResultForWhenResponderReturnsResponse() throws Exception {
    Responder<Void> responder = new TestResponder<Void>() {
      public Result<Void> resultFor(Call call) {
        return RESULT;
      }
    };
    
    Result<Void> result = policy.resultFor(CALL, responder);
    
    assertEquals(RESULT, result);
  }

  public void testResultForWhenResponderThrowsUnknownResponseException() throws Exception {
    Responder<Void> responder = new TestResponder<Void>() {
      public Result<Void> resultFor(Call call) {
        throw new UnknownResponseException("test");
      }
    };
    
    Result<Void> result = policy.resultFor(CALL, responder);
    
    assertEquals(new ReturningResult<Void>(null), result);
  }
  
  public void testResultForWhenResponderThrowsOtherException() throws Exception {
    final RuntimeException exception = new RuntimeException("test");
    Responder<Void> responder = new TestResponder<Void>() {
      public Result<Void> resultFor(Call call) {
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
    assertEquals(false, VoidResponsePolicy.instance().equals(null));
    assertEquals(false, VoidResponsePolicy.instance().equals(StandardResponsePolicy.instance()));
    assertEquals(true,  VoidResponsePolicy.instance().equals(VoidResponsePolicy.instance()));
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
