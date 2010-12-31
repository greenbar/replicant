// Copyright 2009 Kiel Hodges
package replicant;

import junit.framework.*;

public class StandardReturnPolicyTest extends TestCase {

  public void testPolicy() throws Exception {
    StandardReturnPolicy returnPolicy = StandardReturnPolicy.instance();
    
    ResponsePolicy<?> responsePolicy = returnPolicy.responsePolicy();
    
    assertEquals(StandardResponsePolicy.instance(), responsePolicy);
  }
  
}
