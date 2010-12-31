// Copyright 2009 Kiel Hodges
package replicant;

import java.util.*;

import static replicant.collections.CollectionFunctions.*;

public final class MethodMocker<ReturnValue> {

  private final Object                        mock;
  private final String                        methodName;
  private final BaseMethodMocker<ReturnValue> baseMethodMocker;

  MethodMocker(Object mock, String methodName, BaseMethodMocker<ReturnValue> baseMethodMocker) {
    this.mock             = mock;
    this.methodName       = methodName;
    this.baseMethodMocker = baseMethodMocker;
  }

  public Response<ReturnValue> expect(Object... arguments) {
    return baseMethodMocker.expect(argList(arguments));
  }

  public ReturnValue call(Object... arguments) throws TestingException {
    return baseMethodMocker.call(argList(arguments));
  }

  public void assertCalled(Object... arguments) {
    baseMethodMocker.assertCalled(argList(arguments));
  }

  public void assertCalledOnce() {
    baseMethodMocker.assertCalledOnce();
  }
  
  public void assertExpectationsMet() {
    baseMethodMocker.assertExpectationsMet();
  }

  public void assertExpectedCallsMade() {
    baseMethodMocker.assertExpectedCallsMade();
  }

  public void assertNotCalled() {
    baseMethodMocker.assertNotCalled();
  }

  private List<Object> argList(Object... arguments) {
    return list(arguments);
  }

  public final boolean equals(Object object) {
    if (object instanceof MethodMocker<?>) {
      MethodMocker<?> that = (MethodMocker<?>) object;
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
