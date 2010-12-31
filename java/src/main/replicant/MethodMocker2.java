// Copyright 2009 Kiel Hodges
package replicant;

import java.util.*;

import static replicant.collections.CollectionFunctions.*;

public final class MethodMocker2<ReturnValue, Arg1, Arg2> {

  private final Object                        mock;
  private final String                        methodName;
  private final BaseMethodMocker<ReturnValue> baseMethodMocker;

  MethodMocker2(Object mock, String methodName, BaseMethodMocker<ReturnValue> baseMethodMocker) {
    this.mock             = mock;
    this.methodName       = methodName;
    this.baseMethodMocker = baseMethodMocker;
  }

  public Response<ReturnValue> expect(Arg1 arg1, Arg2 arg2) {
    return baseMethodMocker.expect(argList(arg1, arg2));
  }

  public ReturnValue call(Arg1 arg1, Arg2 arg2) throws TestingException {
    return baseMethodMocker.call(argList(arg1, arg2));
  }

  public void assertCalled(Arg1 arg1, Arg2 arg2) {
    baseMethodMocker.assertCalled(argList(arg1, arg2));
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

  private List<Object> argList(Arg1 arg1, Arg2 arg2) {
    return list(arg1, arg2);
  }

  public final boolean equals(Object object) {
    if (object instanceof MethodMocker2<?,?,?>) {
      MethodMocker2<?,?,?> that = (MethodMocker2<?,?,?>) object;
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
