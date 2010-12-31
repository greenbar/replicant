package replicant.collections;

import junit.framework.*;
import replicant.testing.testobjects.TestValue.*;

public class PairTest extends TestCase {

  private static final A A1 = new A(1);
  private static final A A2 = new A(2);
  private static final B B1 = new B(1);
  private static final B B2 = new B(2);

  public void testPairAttributes() throws Exception {
    Pair<A, B> pair = new Pair<A, B>(A1, B1);
    
    assertEquals(A1,  pair.first());
    assertEquals(B1, pair.second());
  }
  
  public void testPairEquality() throws Exception {
    Pair<A, B> pair = new Pair<A, B>(A1, B1);

    assertEquals(false, pair.equals(null));
    assertEquals(false, pair.equals("not a Pair"));
    assertEquals(false, pair.equals(new Pair<A, B>(A2, B1)));
    assertEquals(false, pair.equals(new Pair<A, B>(A1, B2)));
    assertEquals(true,  pair.equals(new Pair<A, B>(A1, B1)));
    
    assertEquals(new Pair<A, B>(A1, B1).hashCode(),  new Pair<A, B>(A1, B1).hashCode());
  }
  
  public void testToString() throws Exception {
    Pair<A, B> pair = new Pair<A, B>(A1, B1);

    assertEquals("Pair(A(1), B(1))", pair.toString());
  }

}
