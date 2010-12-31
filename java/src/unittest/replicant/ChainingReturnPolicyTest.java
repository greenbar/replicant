// Copyright 2009 Kiel Hodges
package replicant;

import junit.framework.*;

public class ChainingReturnPolicyTest extends TestCase {

  private static final Mock MOCK       = new Mock();
  private static final Mock OTHER_MOCK = new Mock();
  
  public void testPolicy() throws Exception {
    ChainingReturnPolicy<Mock> returnPolicy = new ChainingReturnPolicy<Mock>(MOCK);
    
    ResponsePolicy<?> responsePolicy = returnPolicy.responsePolicy();
    
    assertEquals(new ChainingResponsePolicy<Mock>(MOCK), responsePolicy);
  }
  
  public void testEquality() throws Exception {
    ChainingReturnPolicy<Mock> returnPolicy = new ChainingReturnPolicy<Mock>(MOCK);
    assertEquals(false, returnPolicy.equals(null));
    assertEquals(false, returnPolicy.equals(StandardReturnPolicy.instance()));
    assertEquals(false, returnPolicy.equals(new ChainingReturnPolicy<Mock>(OTHER_MOCK)));
    assertEquals(true,  returnPolicy.equals(new ChainingReturnPolicy<Mock>(MOCK)));
  }

}
