// Copyright 2009 Kiel Hodges
package replicant;

import java.util.*;

import junit.framework.*;
import replicant.testing.testobjects.TestValue.*;
import static replicant.collections.CollectionFunctions.*;

public class BaseMethodMockerCallTest extends TestCase {

  private static final Mock         MOCK          = new Mock();
  private static final String       METHOD        = "method";
  private static final List<Object> ARGUMENTS     = list("a1", new B(1), "c1");
  private static final Call         CALL          = new Call(MOCK, METHOD, ARGUMENTS);
  private static final A            RETURN_VALUE  = new A(1);
  
  private static final TestResponder<A> RESPONDER = new TestResponder<A>();
  
  public void testCallWithResponseReturningResult() throws Exception {
    final Result<A> response = new Result<A>() {
      public A returnValue() throws ResponseException {
        return RETURN_VALUE;
      }
    };
    TestResponsePolicy<A> responsePolicy = new TestResponsePolicy<A>() {
      public Result<A> resultFor(Call call, Responder<A> responder) throws UnknownResponseException {
        assertEquals(CALL,      call);
        assertEquals(RESPONDER, responder);
        return response;
      }
    };
    BaseMethodMocker<A> mocker = new BaseMethodMocker<A>(MOCK, METHOD, RESPONDER, responsePolicy, new TestExpecter());
    
    A result = mocker.call(ARGUMENTS);
    
    assertEquals(RETURN_VALUE, result);
  }
  
  public void testCallWhenNotExpected() throws Exception {
    final AssertionFailedError assertionFailedError = new AssertionFailedError("test");
    ExpectationEnforcer expecter = new TestExpecter() {
      public void call(Call call) throws AssertionFailedError {
        assertEquals(CALL, call);
        throw assertionFailedError; 
      }
    };
    TestResponsePolicy<A> responsePolicy = new TestResponsePolicy<A>() {
      public Result<A> resultFor(Call call, Responder<A> responder) throws UnknownResponseException {
        fail("Did not expect call to this method"); 
        return null;
      }
    };
    BaseMethodMocker<A> mocker = new BaseMethodMocker<A>(MOCK, METHOD, RESPONDER, responsePolicy, expecter);
    
    try {
      mocker.call(ARGUMENTS);
      fail("Expected an AssertionFailedError");
    } catch(AssertionFailedError e) {
      assertEquals(assertionFailedError, e);
    }
  }
  
  public void testCallWithResponseThrowingGeneralUncheckedException() throws Exception {
    final RuntimeException exception = new RuntimeException("test");
    final Result<A> response = new Result<A>() {
      public A returnValue() throws ResponseException {
        throw exception;
      }
    };
    TestResponsePolicy<A> responsePolicy = new TestResponsePolicy<A>() {
      public Result<A> resultFor(Call call, Responder<A> responder) throws UnknownResponseException {
        assertEquals(CALL,      call);
        assertEquals(RESPONDER, responder);
        return response;
      }
    };

    BaseMethodMocker<A> mocker = new BaseMethodMocker<A>(MOCK, METHOD, RESPONDER, responsePolicy, new TestExpecter());
    
    try {
      mocker.call(ARGUMENTS);
      fail("Expected an Exception.");
    } catch(Exception e) {
      assertEquals(exception, e);
    }
  }
  
  public void testCallWithResponseThrowingResponseException() throws Exception {
    final FooException exception = new FooException();
    final Result<A> response = new Result<A>() {
      public A returnValue() throws ResponseException {
        throw new ResponseException(exception);
      }
    };
    TestResponsePolicy<A> responsePolicy = new TestResponsePolicy<A>() {
      public Result<A> resultFor(Call call, Responder<A> responder) throws UnknownResponseException {
        assertEquals(CALL,      call);
        assertEquals(RESPONDER, responder);
        return response;
      }
    };
    BaseMethodMocker<A> mocker = new BaseMethodMocker<A>(MOCK, METHOD, RESPONDER, responsePolicy, new TestExpecter());
    try {
      
      try {
        mocker.call(ARGUMENTS);
      } catch(ResponseException e) {
        e.throwCauseIf(FooException.class).
          throwCauseIf(BarException.class).
          throwSelf();
      }
      
      fail("Expected a FooException.");
    } catch(FooException e) {
      assertEquals(exception, e);
    }
  }
  
  private static class TestResponder<ReturnValue> implements Responder<ReturnValue> {
    public void addResponseFor(Call call, Result<ReturnValue> response) {
      fail("Did not expect call to this method"); 
    }
    public Result<ReturnValue> resultFor(Call call) {
      return null;
    }
  }
  
  private static class TestExpecter implements ExpectationEnforcer {
    public void expect(Call call) {
      fail("Did not expect call to this method"); 
    }
    public void call(Call call) throws AssertionFailedError {
    }
    public void assertExpectationsMet() throws AssertionFailedError {
      fail("Did not expect call to this method"); 
    }
  }
  
  private static abstract class TestResponsePolicy<ReturnValue> implements ResponsePolicy<ReturnValue> {
    public StandardResponse<ReturnValue> createExpectedResponse(Call call) {
      return null;
    }
  }
  
  private static class FooException extends Exception {
    private static final long serialVersionUID = 1L;
  }
  
  private static class BarException extends Exception {
    private static final long serialVersionUID = 1L;
  }
  
}
