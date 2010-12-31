// Copyright 2009 Kiel Hodges
package replicant;

import junit.framework.*;

import static replicant.collections.CollectionFunctions.*;

public class StrictExpectationEnforcerTest extends TestCase {

  private static final Call CALL1 = new Call(new Mock(), "method1", list("1"));
  private static final Call CALL2 = new Call(new Mock(), "method2", list("2"));
  private static final Call CALL3 = new Call(new Mock(), "method3", list("3"));
  
  private StrictExpectationEnforcer enforcer;

  public void setUp() throws Exception {
    enforcer = new StrictExpectationEnforcer();
  }
  
  public void testExpecterWhenExpectationsMet() throws Exception {
    enforcer.expect(CALL1);
    enforcer.expect(CALL2);
    enforcer.expect(CALL1);
    enforcer.expect(CALL3);
    
    enforcer.call(CALL1);
    enforcer.call(CALL2);
    enforcer.call(CALL1);
    enforcer.call(CALL3);
    
    enforcer.assertExpectationsMet();
  }
  
  public void testExpecterWhenUnexpectedCallMade() throws Exception {
    enforcer.expect(CALL1);
    enforcer.expect(CALL2);
    enforcer.expect(CALL1);
    enforcer.expect(CALL3);
    enforcer.expect(CALL1);
    enforcer.call(CALL1);
    enforcer.call(CALL2);

    AssertionFailedError assertionFailedError = null;
    try {
      enforcer.call(CALL3);
    } catch (AssertionFailedError e) {
      assertionFailedError = e;
    }
    assertNotNull("AssertionFailedError not thrown as expected", assertionFailedError);
    assertEquals("Unexpected call: " + CALL3 +
                   "\n  Received " + CALL1 +
                   "\n  Received " + CALL2 +
                   "\n" +
                   "\n  Awaiting " + CALL1 +
                   "\n  Awaiting " + CALL3 +
                   "\n  Awaiting " + CALL1,
                 assertionFailedError.getMessage());
  }
  
  public void testExpecterWhenExpectedCallNeverMade() throws Exception {
    enforcer.expect(CALL1);
    enforcer.expect(CALL2);
    enforcer.expect(CALL3);
    enforcer.call(CALL1);
    enforcer.call(CALL2);
    
    AssertionFailedError assertionFailedError = null;
    try {
      enforcer.assertExpectationsMet();
    } catch (AssertionFailedError e) {
      assertionFailedError = e;
    }
    assertNotNull("AssertionFailedError not thrown as expected", assertionFailedError);
    assertEquals("Expected call never received" +
                   "\n  Received " + CALL1 +
                   "\n  Received " + CALL2 +
                   "\n" +
                   "\n  Awaiting " + CALL3,
                 assertionFailedError.getMessage());
  }
 
  public void testExpecterWhenNoCallsExpected() throws Exception {
    enforcer.assertExpectationsMet();
  }
  
  public void testExpecterEquality() throws Exception {
    StrictExpectationEnforcer expecter2 = new StrictExpectationEnforcer();
    
    assertEquals(false, enforcer.equals(null));
    assertEquals(false, enforcer.equals("Not an Expecter"));
    assertEquals(true,  enforcer.equals(enforcer));
    assertEquals(true,  enforcer.equals(expecter2));
    
    enforcer.expect(CALL1);
    enforcer.expect(CALL2);
    assertEquals(false, enforcer.equals(expecter2));
    
    expecter2.expect(CALL1);
    assertEquals(false, enforcer.equals(expecter2));
    
    expecter2.expect(CALL2);
    assertEquals(true, enforcer.equals(expecter2));
    
    enforcer.call(CALL1);
    assertEquals(false, enforcer.equals(expecter2));
    
    expecter2.call(CALL1);
    assertEquals(true, enforcer.equals(expecter2));
    
    enforcer.expect(CALL1);
    enforcer.expect(CALL2);
    expecter2.expect(CALL2);
    expecter2.expect(CALL1);
    assertEquals(false, enforcer.equals(expecter2));
  }
  
}
