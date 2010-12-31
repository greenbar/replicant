// Copyright 2009 Kiel Hodges
package replicant;

import static replicant.collections.CollectionFunctions.*;
import junit.framework.*;

public class LenientExpectationEnforcerTest extends TestCase {

  private static final Call CALL1 = new Call(new Mock(), "method1", list("1"));
  private static final Call CALL2 = new Call(new Mock(), "method2", list("2"));
  private static final Call CALL3 = new Call(new Mock(), "method3", list("3"));
  private static final Call CALL4 = new Call(new Mock(), "method4", list("4"));
  
  private LenientExpectationEnforcer enforcer;

  public void setUp() throws Exception {
    enforcer = new LenientExpectationEnforcer();
  }
  
  public void testExpecterWhenExpectationsMet() throws Exception {
    enforcer.expect(CALL1);
    enforcer.expect(CALL2);
    enforcer.expect(CALL3);
    
    enforcer.call(CALL3);
    enforcer.call(CALL1);
    enforcer.call(CALL2);
    
    enforcer.assertExpectationsMet();
  }
  
  public void testThatExtraExpectionsAreIgnored() throws Exception {
    enforcer.expect(CALL1);
    enforcer.expect(CALL1);
    enforcer.expect(CALL1);
    
    enforcer.call(CALL1);
    
    enforcer.assertExpectationsMet();
  }
  
  public void testThatExtraCallsAreOk() throws Exception {
    enforcer.expect(CALL1);
    
    enforcer.call(CALL1);
    enforcer.call(CALL2);
    enforcer.call(CALL1);
    
    enforcer.assertExpectationsMet();
  }
  
  public void testExpecterWhenExpectedCallNeverMade() throws Exception {
    enforcer.expect(CALL1);
    enforcer.expect(CALL2);
    enforcer.expect(CALL3);
    enforcer.expect(CALL4);
    enforcer.call(CALL2);
    enforcer.call(CALL1);
    
    AssertionFailedError assertionFailedError = null;
    try {
      enforcer.assertExpectationsMet();
    } catch (AssertionFailedError e) {
      assertionFailedError = e;
    }
    assertNotNull("AssertionFailedError not thrown as expected", assertionFailedError);
    assertEquals("Expected call never received" +
                   "\n  Received " + CALL2 +
                   "\n  Received " + CALL1 +
                   "\n" +
                   "\n  Awaiting " + CALL3 +
                   "\n  Awaiting " + CALL4,
                 assertionFailedError.getMessage());
  }
 
  public void testExpecterWhenNoCallsExpected() throws Exception {
    enforcer.assertExpectationsMet();
  }
  
  public void testExpecterEquality() throws Exception {
    LenientExpectationEnforcer expecter2 = new LenientExpectationEnforcer();
    
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
    
    enforcer.call(CALL2);
    assertEquals(false, enforcer.equals(expecter2));
    
    expecter2.call(CALL2);
    assertEquals(true, enforcer.equals(expecter2));
    
    enforcer.expect(CALL1);
    enforcer.expect(CALL2);
    expecter2.expect(CALL2);
    expecter2.expect(CALL1);
    assertEquals(true, enforcer.equals(expecter2));
  }
  

}
