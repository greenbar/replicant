// Copyright 2009 Kiel Hodges
package replicant;

import junit.framework.*;
import replicant.testing.testobjects.TestValue.*;

public class MappedResponderFactoryTest extends TestCase {

  public void testMappedResponderFactory() throws Exception {
    Responder<A> responder = MappedResponderFactory.instance().createResponder();
    
    assertEquals(new MappedResponder<A>(), responder);
  }
  
  public void testThatOnlyOneMappedResponderFactoryIsCreated() throws Exception {
    assertSame(MappedResponderFactory.instance(), MappedResponderFactory.instance());
  }
  
}
