// Copyright 2009 Kiel Hodges
package replicant;

final class ReturningResult<ReturnValue> implements Result<ReturnValue> {

  public ReturningResult(ReturnValue returnValue) {
    this.returnValue = returnValue;
  }

  private final ReturnValue returnValue;
  
  public ReturnValue returnValue() {
    return returnValue;
  }

  public boolean equals(Object object) {
    if (object instanceof ReturningResult<?>) {
      ReturningResult<?> that = (ReturningResult<?>) object;
      Object object1 = this.returnValue;
      Object object2 = that.returnValue;
      return areEqual(object1, object2);
    }
    return false;
  }

  private boolean areEqual(Object object1, Object object2) {
    return object1 == null 
           ? object2 == null 
           : object1.equals(object2);
  }

  public int hashCode() {
    return 0;
  }

  
  public String toString() {
    return "ResultResponse(" + returnValue + ')';
  }
}
