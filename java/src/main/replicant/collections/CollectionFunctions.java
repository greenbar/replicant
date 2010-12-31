// Copyright 2009 Kiel Hodges
package replicant.collections;

import java.util.*;

public class CollectionFunctions {

  private CollectionFunctions() {}

  public static <Element> Set<Element> set(Element... elements) {
    Set<Element> result = new HashSet<Element>();
    for (Element element : elements)
      result.add(element);
    return result;
  }
  
  public static <Element> List<Element> list(Element... elements) {
    List<Element> result = emptyList();
    for (Element element : elements)
      result.add(element);
    return result;
  }
  
  public static <Element> List<Element> list() {
    return emptyList();
  }
  
  private static <Element> List<Element> emptyList() {
    return new ArrayList<Element>();
  }

  public static <Key, Value> Map<Key, Value> map() {
    return new HashMap<Key, Value>();
  }
  
  public static <Key, Value> Map<Key, Value> map(Key key, Value value) {
    Map<Key, Value> map = map();
    map.put(key, value);
    return map;
  }
  
  public static <First, Second> Pair<First, Second> pair(First first, Second second) {
    return new Pair<First, Second>(first, second);
  }
  
}