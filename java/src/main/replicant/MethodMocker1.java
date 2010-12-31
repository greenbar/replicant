// Copyright 2009 Kiel Hodges
package replicant;

import java.util.*;

import static replicant.collections.CollectionFunctions.*;

public final class MethodMocker1<ReturnValue, Arg1> {

  private final Object                        mock;
  private final String                        methodName;
  private final BaseMethodMocker<ReturnValue> baseMethodMocker;

  MethodMocker1(Object mock, String methodName, BaseMethodMocker<ReturnValue> baseMethodMocker) {
    this.mock             = mock;
    this.methodName       = methodName;
    this.baseMethodMocker = baseMethodMocker;
  }

  public Response<ReturnValue> expect(Arg1 arg) {
    return baseMethodMocker.expect(argList(arg));
  }

  public ReturnValue call(Arg1 arg) throws TestingException {
    return baseMethodMocker.call(argList(arg));
  }

  public void assertCalled(Arg1 arg) {
    baseMethodMocker.assertCalled(argList(arg));
  }
  
  public void assertNotCalled(Arg1 arg) {
    baseMethodMocker.assertNotCalled(argList(arg));
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

  private List<Object> argList(Arg1 arg) {
    return list((Object)arg);
  }

  public final boolean equals(Object object) {
    if (object instanceof MethodMocker1<?, ?>) {
      MethodMocker1<?, ?> that = (MethodMocker1<?, ?>) object;
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
