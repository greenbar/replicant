// Copyright 2009 Kiel Hodges
package replicant;

import java.util.*;

import junit.framework.*;
import replicant.testing.testobjects.TestValue.*;
import static replicant.collections.CollectionFunctions.*;

public class BaseMethodMockerExpectTest extends TestCase {

  private static final Mock                 MOCK      = new Mock();
  private static final String               METHOD    = "method";
  private static final List<Object>         ARGUMENTS = list("a1", new B(1), "c1");
  private static final Call                 CALL      = new Call(MOCK, METHOD, ARGUMENTS);
  private static final StandardResponse<A>  RESPONSE  = new StandardResponse<A>(null);
  
  
  @SuppressWarnings("unchecked")
  public void testExpect() throws Exception {
    final List<Call> expectedCalls = list();
    ExpectationEnforcer expecter = new TestExpecter() {
      public void expect(Call call) {
        expectedCalls.add(call);
      }
    };
    ResponsePolicy<A> responsePolicy = new ResponsePolicy<A>() {
      public StandardResponse<A> createExpectedResponse(Call call) {
        assertEquals(CALL, call);
        return RESPONSE;
      }
      public Result<A> resultFor(Call call, Responder<A> responder) throws UnknownResponseException {
        return null;
      }
    };
    final List<Result<A>> results = list();
    Responder<A> responder = new TestResponder() {
      public void addResponseFor(Call call, Result<A> result) {
        results.add(result);
        assertEquals(CALL, call);
      }
    };
    BaseMethodMocker<A> mocker = new BaseMethodMocker<A>(MOCK, METHOD, responder, responsePolicy, expecter);
 
    Response<A> response = mocker.expect(ARGUMENTS);
    
    assertEquals(RESPONSE,       response);
    assertEquals(list(RESPONSE), results);
    assertEquals(list(CALL),     expectedCalls);
  }
  
  private static abstract class TestResponder implements Responder<A> {
    public Result<A> resultFor(Call call) {
      fail("Did not expect call to this method"); 
      return null;
    }
  }

  private static abstract class TestExpecter implements ExpectationEnforcer {
    public void assertExpectationsMet() throws AssertionFailedError {
      fail("Did not expect call to this method"); 
    }
    public void call(Call call) throws AssertionFailedError {
      fail("Did not expect call to this method"); 
    }
  }
  
}
