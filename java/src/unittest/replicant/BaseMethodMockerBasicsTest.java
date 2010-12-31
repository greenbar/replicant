// Copyright 2009 Kiel Hodges
package replicant;

import junit.framework.*;
import replicant.testing.testobjects.TestValue.*;

public class BaseMethodMockerBasicsTest extends TestCase {

  private static final Mock   MOCK1    = new Mock();
  private static final Mock   MOCK2    = new Mock();
  private static final String METHOD1  = "method1";
  private static final String METHOD2  = "method2";
  
  public void testEquality() throws Exception {
    TestResponder<A> responder1           = new TestResponder<A>();
    TestResponder<A> responder2           = new TestResponder<A>();
    TestExpecter expecter1                = new TestExpecter();
    TestExpecter expecter2                = new TestExpecter();
    TestResponsePolicy<A> responsePolicy1 = new TestResponsePolicy<A>();
    TestResponsePolicy<A> responsePolicy2 = new TestResponsePolicy<A>();
    BaseMethodMocker<A> mocker = new BaseMethodMocker<A>(MOCK1, METHOD1, responder1, responsePolicy1, expecter1);
      
    assertEquals(false, mocker.equals(null));
    assertEquals(false, mocker.equals("not a BaseMethodMocker"));
    assertEquals(false, mocker.equals(new BaseMethodMocker<A>(MOCK2, METHOD1, responder1, responsePolicy1, expecter1)));
    assertEquals(false, mocker.equals(new BaseMethodMocker<A>(MOCK1, METHOD2, responder1, responsePolicy1, expecter1)));
    assertEquals(false, mocker.equals(new BaseMethodMocker<A>(MOCK1, METHOD1, responder2, responsePolicy1, expecter1)));
    assertEquals(false, mocker.equals(new BaseMethodMocker<A>(MOCK1, METHOD1, responder1, responsePolicy2, expecter1)));
    assertEquals(false, mocker.equals(new BaseMethodMocker<A>(MOCK1, METHOD1, responder1, responsePolicy1, expecter2)));
    assertEquals(true,  mocker.equals(new BaseMethodMocker<A>(MOCK1, METHOD1, responder1, responsePolicy1, expecter1)));
    assertEquals(true,  mocker.equals(mocker));
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
      fail("Did not expect call to this method"); 
    }
    public Result<ReturnValue> resultFor(Call call) {
      fail("Did not expect call to this method"); 
      return null;
    }
  }

  private static class TestResponsePolicy<ReturnValue> implements ResponsePolicy<ReturnValue> {
    public StandardResponse<ReturnValue> createExpectedResponse(Call call) {
      fail("Did not expect call to this method"); 
      return null;
    }

    public Result<ReturnValue> resultFor(Call call, Responder<ReturnValue> responder) throws UnknownResponseException {
      fail("Did not expect call to this method"); 
      return null;
    }
  }
  
}
