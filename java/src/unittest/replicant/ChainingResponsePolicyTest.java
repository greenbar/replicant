// Copyright 2009 Kiel Hodges
package replicant;

import junit.framework.*;
import static replicant.collections.CollectionFunctions.*;

public class ChainingResponsePolicyTest extends TestCase {

  private static final Mock             MOCK   = new Mock();
  private static final TestResult<Mock> RESULT = new TestResult<Mock>();
  private static final Call             CALL   = new Call(MOCK, "method", list(1, 2, 3));
  
  private ChainingResponsePolicy<Mock> policy;
  
  public void setUp() throws Exception {
    policy = new ChainingResponsePolicy<Mock>(MOCK);
  }
  
  public void testCreateExpectedResponse() throws Exception {
    StandardResponse<Mock> response = policy.createExpectedResponse(CALL);
    
    StandardResponse<Mock> expectedResponse = new StandardResponse<Mock>(CALL);
    expectedResponse.returning(MOCK);
    assertEquals(expectedResponse, response);
  }

  public void testResultForWhenResponderReturnsResponse() throws Exception {
    Responder<Mock> responder = new TestResponder<Mock>() {
      public Result<Mock> resultFor(Call call) {
        return RESULT;
      }
    };
    
    Result<Mock> result = policy.resultFor(CALL, responder);
    
    assertEquals(RESULT, result);
  }

  public void testResultForWhenResponderThrowsUnknownResponseException() throws Exception {
    Responder<Mock> responder = new TestResponder<Mock>() {
      public Result<Mock> resultFor(Call call) {
        throw new UnknownResponseException("test");
      }
    };
    
    Result<Mock> result = policy.resultFor(CALL, responder);
    
    assertEquals(new ReturningResult<Mock>(MOCK), result);
  }
  
  public void testResultForWhenResponderThrowsOtherException() throws Exception {
    final RuntimeException exception = new RuntimeException("test");
    Responder<Mock> responder = new TestResponder<Mock>() {
      public Result<Mock> resultFor(Call call) {
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
    assertEquals(false, policy.equals(null));
    assertEquals(false, policy.equals(StandardResponsePolicy.instance()));
    assertEquals(false, policy.equals(new ChainingResponsePolicy<Mock>(new Mock())));
    assertEquals(true,  policy.equals(new ChainingResponsePolicy<Mock>(MOCK)));
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
