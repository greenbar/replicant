// Copyright 2009 Kiel Hodges
package replicant;

import java.util.*;

import static replicant.collections.CollectionFunctions.*;

public final class MethodMocker0<ReturnValue> {

  private static final List<Object> ARGUMENTS = list();
  
  private final Object                        mock;
  private final String                        methodName;
  private final BaseMethodMocker<ReturnValue> baseMethodMocker;

  MethodMocker0(Object mock, String methodName, BaseMethodMocker<ReturnValue> baseMethodMocker) {
    this.mock             = mock;
    this.methodName       = methodName;
    this.baseMethodMocker = baseMethodMocker;
  }

  public Response<ReturnValue> expect() {
    return baseMethodMocker.expect(ARGUMENTS);
  }

  public ReturnValue call() throws TestingException {
    return baseMethodMocker.call(ARGUMENTS);
  }

  public void assertCalled() {
    baseMethodMocker.assertCalled(ARGUMENTS);
  }
  
  public void assertNotCalled() {
    baseMethodMocker.assertNotCalled();
  }
  
  public void assertCalledOnce() {
    baseMethodMocker.assertCalledOnce();
  }
  
  public void assertExpectedCallsMade() {
    baseMethodMocker.assertExpectedCallsMade();
  }

  public void assertExpectationsMet() {
    baseMethodMocker.assertExpectationsMet();
  }

  public final boolean equals(Object object) {
    if (object instanceof MethodMocker0<?>) {
      MethodMocker0<?> that = (MethodMocker0<?>) object;
      return this.mock.equals(that.mock) && 
             this.methodName.equals(that.methodName) && 
             this.baseMethodMocker.equals(that.baseMethodMocker);
    }
    return false;
  }

  public final int hashCode() {
    return 0;
  }

}
