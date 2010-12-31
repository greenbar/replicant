// Copyright 2009 Kiel Hodges
package replicant.sample;

public class Widget {

  private final int id;

  public Widget(int id) {
    this.id = id;
  }
  
  public boolean equals(Object object) {
    if (object instanceof Widget) {
      Widget that = (Widget) object;
      return this.id == that.id;
    }
    return false;
  }

  public int hashCode() {
    return id;
  }

  public String toString() {
    return "Widget(" + id + ')';
  }
  
}
