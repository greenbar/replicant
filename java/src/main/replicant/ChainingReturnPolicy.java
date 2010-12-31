// Copyright 2009 Kiel Hodges
package replicant;

class ChainingReturnPolicy<Mocked> implements ReturnPolicy {

  private final Mocked mock;

  public ChainingReturnPolicy(Mocked mock) {
    this.mock = mock;
  }

  public ResponsePolicy<?> responsePolicy() {
    return new ChainingResponsePolicy<Mocked>(mock);
  }
  
  public String toString() {
    return "ChainingReturnPolicy(" + mock + ')';
  }
  
  public boolean equals(Object object) {
    if (object instanceof ChainingReturnPolicy<?>) {
      ChainingReturnPolicy<?> that = (ChainingReturnPolicy<?>) object;
      return this.mock == that.mock;
    }
    return false;
  }

  public int hashCode() {
    return 0;
  }

}
