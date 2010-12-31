// Copyright 2009 Kiel Hodges
package replicant;

import java.util.*;

import junit.framework.*;
import replicant.testing.testobjects.TestValue.*;
import static replicant.collections.CollectionFunctions.*;

public class BaseMethodMockerAssertionsTest extends TestCase {

  private static final A            RESULT      = new A(1);
  private static final Mock         MOCK        = new Mock();
  private static final String       METHOD      = "method";
  private static final List<Object> ARGUMENTS1  = list("a1", new B(1), "c1");
  private static final List<Object> ARGUMENTS2  = list("a2", new B(2), "c2");
  private static final List<Object> ARGUMENTS3  = list("a3", new B(3), "c3");
  private static final List<Object> ARGUMENTS4  = list("a4", new B(4), "c4");
  private static final Call         CALL1       = new Call(MOCK, METHOD, ARGUMENTS1);
  private static final Call         CALL2       = new Call(MOCK, METHOD, ARGUMENTS2);
  private static final Call         CALL3       = new Call(MOCK, METHOD, ARGUMENTS3);
  private static final Call         CALL4       = new Call(MOCK, METHOD, ARGUMENTS4);
  
  private static final ExpectationEnforcer           EXPECTER        = new TestExpecter();
  private static final Responder<A>       RESPONDER       = new TestResponder<A>();
  private static final ResponsePolicy<A>  RESPONSE_POLICY = new TestResponsePolicy<A>(new A(1));

  private BaseMethodMocker<A> mocker;

  public void setUp() throws Exception {
    mocker = new BaseMethodMocker<A>(MOCK, METHOD, RESPONDER, RESPONSE_POLICY, EXPECTER);
  }
  
  public void testAssertExpectedCallsMadeWhenNoneExpected() throws Exception {
    mocker.assertExpectedCallsMade();
  }
  
  public void testAssertExpectedCallsMadeWhenExpectedCallsMade() throws Exception {
    mocker.expect(ARGUMENTS1).returning(RESULT);
    mocker.expect(ARGUMENTS2).returning(RESULT);
    mocker.call(ARGUMENTS1);
    mocker.call(ARGUMENTS2);
    
    mocker.assertExpectedCallsMade();
  }
  
  public void testAssertExpectedCallsMadeWhenExpectedCallNotMade() throws Exception {
    mocker.expect(ARGUMENTS1).returning(RESULT);
    mocker.expect(ARGUMENTS2).returning(RESULT);
    mocker.expect(ARGUMENTS3).returning(RESULT);
    mocker.expect(ARGUMENTS4).returning(RESULT);
    mocker.call(ARGUMENTS1);
    mocker.call(ARGUMENTS2);
    
    AssertionFailedError assertionFailedError = null;
    try {
      mocker.assertExpectedCallsMade();
    } catch (AssertionFailedError e) {
      assertionFailedError = e;
    }
    assertNotNull("AssertionFailedError not thrown as expected", assertionFailedError);
    assertEquals("Expected call never received" +
                   "\n  Received " + CALL1 +
                   "\n  Received " + CALL2 +
                   "\n" +
                   "\n  Awaiting " + CALL3 +
                   "\n  Awaiting " + CALL4,
                 assertionFailedError.getMessage());

  }
  
  public void testAssertNotCalledWithArgumentsWhenNotCalled() throws Exception {
    mocker.assertNotCalled(ARGUMENTS1);
  }
  
  public void testAssertNotCalledWithArgumentsWhenDifferentCallsMade() throws Exception {
    mocker.expect(ARGUMENTS1).returning(RESULT);
    mocker.expect(ARGUMENTS2).returning(RESULT);
    mocker.call(ARGUMENTS1);
    mocker.call(ARGUMENTS2);
    
    mocker.assertNotCalled(ARGUMENTS3);
  }
  
  public void testAssertNotCalledWithArgumentsWhenCallMade() throws Exception {
    mocker.expect(ARGUMENTS1).returning(RESULT);
    mocker.expect(ARGUMENTS2).returning(RESULT);
    mocker.call(ARGUMENTS1);
    mocker.call(ARGUMENTS2);
    
    AssertionFailedError assertionFailedError = null;
    try {
      mocker.assertNotCalled(ARGUMENTS1);
    } catch (AssertionFailedError e) {
      assertionFailedError = e;
    }
    assertNotNull("AssertionFailedError not thrown as expected", assertionFailedError);
    assertEquals("Did not expect " + CALL1 + ", but received:" + 
                   "\n  " + CALL1 +
                   "\n  " + CALL2,
                 assertionFailedError.getMessage());
  }
  
  public void testAssertNotCalledWhenNotCalled() throws Exception {
    mocker.assertNotCalled();
  }
  
  public void testAssertNotCalledWhenCalled() throws Exception {
    mocker.expect(ARGUMENTS1).returning(RESULT);
    mocker.expect(ARGUMENTS2).returning(RESULT);
    
    mocker.call(ARGUMENTS1);
    mocker.call(ARGUMENTS2);
    
    AssertionFailedError assertionFailedError = null;
    try {
      mocker.assertNotCalled();
    } catch (AssertionFailedError e) {
      assertionFailedError = e;
    }
    assertNotNull("AssertionFailedError not thrown as expected", assertionFailedError);
    assertEquals("Expected no calls to " + MOCK + '.' + METHOD + ", but received:" + 
                   "\n  " + CALL1 +
                   "\n  " + CALL2,
                 assertionFailedError.getMessage());
  }
  
  public void testAssertCalledWhenCalled() throws Exception {
    mocker.expect(ARGUMENTS1).returning(RESULT);
    mocker.expect(ARGUMENTS2).returning(RESULT);
    
    mocker.call(ARGUMENTS1);
    mocker.call(ARGUMENTS2);
    
    mocker.assertCalled(ARGUMENTS1);
    mocker.assertCalled(ARGUMENTS2);
  }
  
  public void testAssertCalledWhenNoCallsMade() throws Exception {
    mocker.expect(ARGUMENTS1).returning(RESULT);
    mocker.expect(ARGUMENTS2).returning(RESULT);
    
    AssertionFailedError assertionFailedError = null;
    try {
      mocker.assertCalled(ARGUMENTS3);
    } catch (AssertionFailedError e) {
      assertionFailedError = e;
    }
    assertNotNull("AssertionFailedError not thrown as expected", assertionFailedError);
    assertEquals("Expected " + CALL3 + ", but no calls were made", 
                 assertionFailedError.getMessage());
  }
  
  public void testAssertCalledWhenDifferentCallsMade() throws Exception {
    mocker.expect(ARGUMENTS1).returning(RESULT);
    mocker.expect(ARGUMENTS2).returning(RESULT);
    
    mocker.call(ARGUMENTS1);
    mocker.call(ARGUMENTS2);
    
    AssertionFailedError assertionFailedError = null;
    try {
      mocker.assertCalled(ARGUMENTS3);
    } catch (AssertionFailedError e) {
      assertionFailedError = e;
    }
    assertNotNull("AssertionFailedError not thrown as expected", assertionFailedError);
    assertEquals("Expected " + CALL3 + ", but received:" + 
                   "\n  " + CALL1 +
                   "\n  " + CALL2,
                 assertionFailedError.getMessage());
  }
  
  public void testAssertCalledOnceWhenCalledOnce() throws Exception {
    mocker.expect(ARGUMENTS1).returning(RESULT);
    mocker.expect(ARGUMENTS1).returning(RESULT);
    
    mocker.call(ARGUMENTS1);
    
    mocker.assertCalledOnce();
  }
  
  public void testAssertCalledOnceWhenNotCalled() throws Exception {
    mocker.expect(ARGUMENTS1).returning(RESULT);
    mocker.expect(ARGUMENTS2).returning(RESULT);
    
    AssertionFailedError assertionFailedError = null;
    try {
      mocker.assertCalledOnce();
    } catch (AssertionFailedError e) {
      assertionFailedError = e;
    }
    assertNotNull("AssertionFailedError not thrown as expected", assertionFailedError);
    assertEquals("Expected 1 call to " + MOCK + '.' + METHOD + ", but no calls were made", 
                 assertionFailedError.getMessage());
  }
  
  public void testAssertCalledOnceWhenCalledMoreThanOnce() throws Exception {
    mocker.expect(ARGUMENTS1).returning(RESULT);
    mocker.expect(ARGUMENTS2).returning(RESULT);
    mocker.call(ARGUMENTS1);
    mocker.call(ARGUMENTS2);
    
    AssertionFailedError assertionFailedError = null;
    try {
      mocker.assertCalledOnce();
    } catch (AssertionFailedError e) {
      assertionFailedError = e;
    }
    assertNotNull("AssertionFailedError not thrown as expected", assertionFailedError);
    assertEquals("Expected 1 call to " + MOCK + '.' + METHOD + ", but received:" + 
                   "\n  " + CALL1 +
                   "\n  " + CALL2,
                 assertionFailedError.getMessage());
  }
  
  private static class TestResponder<ReturnValue> implements Responder<ReturnValue> {
    public void addResponseFor(Call call, Result<ReturnValue> response) {
    }
    public Result<ReturnValue> resultFor(Call call) {
      return null;
    }
  }
  
  private static class TestResponsePolicy<ReturnValue> implements ResponsePolicy<ReturnValue> {
    private ReturningResult<ReturnValue> result;
    public TestResponsePolicy(ReturnValue returnValue) {
      result = new ReturningResult<ReturnValue>(returnValue);
    }
    public StandardResponse<ReturnValue> createExpectedResponse(Call call) {
      return new StandardResponse<ReturnValue>(call);
    }
    public Result<ReturnValue> resultFor(Call call, Responder<ReturnValue> responder) throws UnknownResponseException {
      return result;
    }
  }
  
  private static class TestExpecter implements ExpectationEnforcer {
    public void assertExpectationsMet() throws AssertionFailedError {
    }
    public void call(Call call) throws AssertionFailedError {
    }
    public void expect(Call call) {
    }
  }
  
}
