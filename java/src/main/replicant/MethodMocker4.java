// Copyright 2009 Kiel Hodges
package replicant;

import static replicant.collections.CollectionFunctions.*;

import java.util.*;

public final class MethodMocker4<ReturnValue, Arg1, Arg2, Arg3, Arg4> {

  private final Object                        mock;
  private final String                        methodName;
  private final BaseMethodMocker<ReturnValue> baseMethodMocker;

  MethodMocker4(Object mock, String methodName, BaseMethodMocker<ReturnValue> baseMethodMocker) {
    this.mock             = mock;
    this.methodName       = methodName;
    this.baseMethodMocker = baseMethodMocker;
  }

  public Response<ReturnValue> expect(Arg1 arg1, Arg2 arg2, Arg3 arg3, Arg4 arg4) {
    return baseMethodMocker.expect(argList(arg1, arg2, arg3, arg4));
  }

  public ReturnValue call(Arg1 arg1, Arg2 arg2, Arg3 arg3, Arg4 arg4) throws TestingException {
    return baseMethodMocker.call(argList(arg1, arg2, arg3, arg4));
  }

  public void assertCalled(Arg1 arg1, Arg2 arg2, Arg3 arg3, Arg4 arg4) {
    baseMethodMocker.assertCalled(argList(arg1, arg2, arg3, arg4));
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

  private List<Object> argList(Arg1 arg1, Arg2 arg2, Arg3 arg3, Arg4 arg4) {
    return list(arg1, arg2, arg3, arg4);
  }

  public final boolean equals(Object object) {
    if (object instanceof MethodMocker4<?,?,?,?,?>) {
      MethodMocker4<?,?,?,?,?> that = (MethodMocker4<?,?,?,?,?>) object;
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
