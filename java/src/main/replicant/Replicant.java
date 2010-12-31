// Copyright 2009 Kiel Hodges
package replicant;

public class Replicant {

  private Replicant() {}

  public static MethodMockerBuilder<Object> methodMocker(Object mock, String methodName) {
    return new MethodMockerBuilder<Object>(mock, methodName, StandardReturnPolicy.instance());
  }
  
  public static MethodMockerBuilder<Void> voidMethodMocker(Object mock, String methodName) {
    return new MethodMockerBuilder<Void>(mock, methodName, VoidReturnPolicy.instance());
  }
  
  public static <Mocked> MethodMockerBuilder<Mocked> chainingMethodMocker(Mocked mock, String methodName) {
    return new MethodMockerBuilder<Mocked>(mock, methodName, new ChainingReturnPolicy<Mocked>(mock));
  }
  
  public static ExpectationEnforcer lenientExpectationEnforcer() {
    return new LenientExpectationEnforcer();
  }
  
  public static ExpectationEnforcer strictExpectationEnforcer() {
    return new StrictExpectationEnforcer();
  }
  
  /** @deprecated Used Replicant.lenientExpectationEnforcer() instead */
  public static Expecter nullExpecter() {
    return (Expecter) lenientExpectationEnforcer();
  }
  
  /** @deprecated Used Replicant.strictExpectationEnforcer() instead */
  public static Expecter orderedExpecter() {
    return (Expecter) strictExpectationEnforcer();
  }
  
}
