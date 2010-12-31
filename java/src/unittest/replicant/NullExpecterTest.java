// Copyright 2009 Kiel Hodges
package replicant;

import static replicant.collections.CollectionFunctions.*;

import junit.framework.*;

public class NullExpecterTest extends TestCase {

  private static final Call CALL1 = new Call(new Mock(), "method", list("1"));
  private static final Call CALL2 = new Call(new Mock(), "method", list("2"));
  private static final Call CALL3 = new Call(new Mock(), "method", list("3"));
  
  public void testNullExpecter() throws Exception {
    NullExpecter expecter = NullExpecter.instance();
    
    expecter.expect(CALL3);
    
    expecter.call(CALL1);
    expecter.call(CALL2);
    expecter.call(CALL1);
    expecter.call(CALL3);
    
    expecter.assertExpectationsMet();
  }

  public void testEquality() throws Exception {
    NullExpecter expecter = NullExpecter.instance();
    
    assertEquals(false, expecter.equals(null));
    assertEquals(false, expecter.equals("not a NullExpecter"));
    assertEquals(true,  expecter.equals(expecter));
    assertSame(expecter, NullExpecter.instance());
  }

}
