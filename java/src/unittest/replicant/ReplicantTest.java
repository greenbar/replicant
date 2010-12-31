// Copyright 2009 Kiel Hodges
package replicant;

import junit.framework.*;
import static replicant.Replicant.*;

public class ReplicantTest extends TestCase {

  private static final String METHOD = "method";
  private static final Mock   MOCK   = new Mock();
  
  public void testMethodMocker() throws Exception {
    MethodMockerBuilder<Object> builder = methodMocker(MOCK, METHOD);
    
    assertEquals(new MethodMockerBuilder<Object>(MOCK, METHOD, StandardReturnPolicy.instance()), 
                 builder);
  }
  
  public void testVoidMethodMocker() throws Exception {
    MethodMockerBuilder<Void> builder = voidMethodMocker(MOCK, METHOD);
    
    assertEquals(new MethodMockerBuilder<Void>(MOCK, METHOD, VoidReturnPolicy.instance()), 
                 builder);
  }
  
  public void testChainingMethodMocker() throws Exception {
    MethodMockerBuilder<Mock> builder = chainingMethodMocker(MOCK, METHOD);
    
    assertEquals(new MethodMockerBuilder<Mock>(MOCK, METHOD, new ChainingReturnPolicy<Mock>(MOCK)), 
                 builder);
  }
  
  public void testLenientExpectationEnforcer() throws Exception {
    ExpectationEnforcer expecter = lenientExpectationEnforcer();
    
    assertEquals(new LenientExpectationEnforcer(), expecter);
  }
  
  public void testStrictExpectationEnforcer() throws Exception {
    ExpectationEnforcer expecter = strictExpectationEnforcer();
    
    assertEquals(new StrictExpectationEnforcer(), expecter);
  }
  
  public void testNullExpecter() throws Exception {
    @SuppressWarnings("deprecation")
    Expecter expecter = nullExpecter();
    
    assertEquals(lenientExpectationEnforcer(), expecter);
  }
  
  public void testOrderedExpecter() throws Exception {
    @SuppressWarnings("deprecation")
    Expecter expecter = orderedExpecter();
    
    assertEquals(strictExpectationEnforcer(), expecter);
  }
  
}

