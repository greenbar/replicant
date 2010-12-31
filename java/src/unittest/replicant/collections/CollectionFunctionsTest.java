// Copyright 2009 Kiel Hodges
package replicant.collections;

import java.util.*;

import junit.framework.*;
import replicant.testing.testobjects.TestValue.*;
import static replicant.collections.CollectionFunctions.*;

public class CollectionFunctionsTest extends TestCase {

  private static final A A7 = new A(7);
  private static final A A6 = new A(6);
  private static final A A5 = new A(5);
  private static final A A4 = new A(4);
  private static final A A3 = new A(3);
  private static final A A2 = new A(2);
  private static final A A1 = new A(1);
  private static final B B1 = new B(1);

  public void testSet() throws Exception {
    Set<A> set = set(A1, A2, A3);
    
    HashSet<A> expected = new HashSet<A>();
    expected.add(A1);
    expected.add(A2);
    expected.add(A3);
    assertEquals(expected, set);
  }
  
  public void testListWithNoArgs() throws Exception {
    List<A> list = list();
    
    assertEquals(new ArrayList<A>(), list);
  }
  
  public void testListWithObjects() throws Exception {
    List<A> list = list(A1, A2, A3);
    
    List<A> expected = new ArrayList<A>();
    expected.add(A1);
    expected.add(A2);
    expected.add(A3);
    assertEquals(expected, list);
  }
  
  public void testThatListIsNotFixedSize() throws Exception {
    List<A> list = list(A1, A2, A3);
    list.remove(A2);
    list.add(A4);
    list.add(A5);
    list.add(0, A6);
    list.add(2, A7);
    list.remove(4);
    
    assertEquals(list(A6, A1, A7, A3, A5), list);
  }
  
  public void testListWithPrimitives() throws Exception {
    List<Integer> list = list(1, 7, 3);
    
    assertEquals(list(new Integer(1), new Integer(7), new Integer(3)), list);
  }
  
  public void testMap() throws Exception {
    Map<A, B> map = map();
    
    assertEquals(new HashMap<A, B>(), map);
  }
  
  public void testMapWithKeyAndValue() throws Exception {
    Map<A, B> map = map(A1, B1);
    
    Map<A, B> expectedMap = map();
    expectedMap.put(A1, B1);
    assertEquals(expectedMap, map);
  }
  
  public void testPair() throws Exception {
    Pair<A, B> pair = pair(A2, B1);
    
    assertEquals(new Pair<A, B>(A2, B1), pair);
  }
}
