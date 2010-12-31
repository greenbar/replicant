// Copyright 2009 Kiel Hodges
package replicant;

import java.util.*;

import junit.framework.*;
import replicant.testing.testobjects.TestValue.*;
import static replicant.collections.CollectionFunctions.*;

public class BaseMethodMockerExpectationsTest extends TestCase {

  private static final Mock         MOCK        = new Mock();
  private static final String       METHOD      = "method";
  private static final List<Object> ARGUMENTS1  = list("a1", new B(1), "c1");
  private static final List<Object> ARGUMENTS2  = list("a2", new B(2), "c2");
  private static final Call         CALL1       = new Call(MOCK, METHOD, ARGUMENTS1);
  private static final Call         CALL2       = new Call(MOCK, METHOD, ARGUMENTS2);
  
  private static final TestResponder<A>       RESPONDER       = new TestResponder<A>();
  private static final TestResponsePolicy<A>  RESPONSE_POLICY = new TestResponsePolicy<A>(new A(1));


  public void testAddingExpectations() throws Exception {
    final List<Call> calls = list();
    TestExpecter expecter = new TestExpecter() {
      public void expect(Call call) {
        calls.add(call);
      }
    };
    BaseMethodMocker<A> mocker = new BaseMethodMocker<A>(MOCK, METHOD, RESPONDER, RESPONSE_POLICY, expecter);
    
    mocker.expect(ARGUMENTS1);
    mocker.expect(ARGUMENTS2);
    
    assertEquals(list(CALL1, CALL2), calls);
  }
  
  public void testRecordingCalls() throws Exception {
    final List<Call> calls = list();
    TestExpecter expecter = new TestExpecter() {
      public void call(Call call) throws AssertionFailedError {
        calls.add(call);
      }
    };
    BaseMethodMocker<A> mocker = new BaseMethodMocker<A>(MOCK, METHOD, RESPONDER, RESPONSE_POLICY, expecter);
    
    mocker.call(ARGUMENTS1);
    mocker.call(ARGUMENTS2);
    
    assertEquals(list(CALL1, CALL2), calls);
  }
  
  public void testAssertingThatExpectationsAreMet() throws Exception {
    final AssertionFailedError assertionFailedError = new AssertionFailedError("test");
    TestExpecter expecter = new TestExpecter() {
      public void assertExpectationsMet() throws AssertionFailedError {
        throw assertionFailedError;
      }
    };
    BaseMethodMocker<A> mocker = new BaseMethodMocker<A>(MOCK, METHOD, RESPONDER, RESPONSE_POLICY, expecter);
    
    AssertionFailedError thrownError = null;
    try {
      mocker.assertExpectationsMet();
    } catch (AssertionFailedError e) {
      thrownError = e;
    }
    assertSame(assertionFailedError, thrownError);
  }
  
  private static class TestExpecter implements ExpectationEnforcer {
    public void assertExpectationsMet() throws AssertionFailedError {
      fail("Did not expect call to this method"); 
    }
    public void call(Call call) throws AssertionFailedError {
      fail("Did not expect call to this method"); 
    }
    public void expect(Call call) {
      fail("Did not expect call to this method"); 
    }
  }
  
  private static class TestResponder<ReturnValue> implements Responder<ReturnValue> {
    public void addResponseFor(Call call, Result<ReturnValue> result) {
    }
    public Result<ReturnValue> resultFor(Call call) {
      return null;
    }
  }
  
  private static class TestResponsePolicy<ReturnValue> implements ResponsePolicy<ReturnValue> {
    private ReturningResult<ReturnValue> result;
    public TestResponsePolicy(ReturnValue returnValue) {
      this.result = new ReturningResult<ReturnValue>(returnValue);
    }
    public Result<ReturnValue> resultFor(Call call, Responder<ReturnValue> responder) {
      return result;
    }
    public StandardResponse<ReturnValue> createExpectedResponse(Call call) {
      return null;
    }
  }
  
}
