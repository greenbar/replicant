// Copyright 2009 Kiel Hodges
package replicant;

import junit.framework.*;
import replicant.testing.testobjects.TestValue.*;

public class OrderedResponderFactoryTest extends TestCase {

  public void testOrderedResponderFactory() throws Exception {
    Responder<A> responder = OrderedResponderFactory.instance().createResponder();
    
    assertEquals(new OrderedResponder<A>(), responder);
  }
  
  public void testThatOnlyOneOrderedResponderFactoryIsCreated() throws Exception {
    assertSame(OrderedResponderFactory.instance(), OrderedResponderFactory.instance());
  }
  
}
