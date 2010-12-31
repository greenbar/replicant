// Copyright 2009 Kiel Hodges
package replicant;

import junit.framework.*;

public class VoidReturnPolicyTest extends TestCase {

  public void testPolicy() throws Exception {
    VoidReturnPolicy returnPolicy = VoidReturnPolicy.instance();
    
    ResponsePolicy<?> responsePolicy = returnPolicy.responsePolicy();
    
    assertEquals(VoidResponsePolicy.instance(), responsePolicy);
  }
  
}
