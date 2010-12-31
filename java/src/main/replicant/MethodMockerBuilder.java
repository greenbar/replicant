// Copyright 2009 Kiel Hodges
package replicant;

public class MethodMockerBuilder<ReturnValueUpperBound> {

  private final Object       mock;
  private final String       methodName;
  private final ReturnPolicy returnPolicy;
  
  private ExpectationEnforcer expectationEnforcer = new LenientExpectationEnforcer();
  private ResponderFactory    responderFactory    = MappedResponderFactory.instance();

  MethodMockerBuilder(Object mock, String methodName, ReturnPolicy returnPolicy) {
    this.mock         = mock;
    this.methodName   = methodName;
    this.returnPolicy = returnPolicy;
  }

  public MethodMockerBuilder<ReturnValueUpperBound> enforcingExpectationsWith(ExpectationEnforcer expectationEnforcer) {
    this.expectationEnforcer = expectationEnforcer;
    return this;
  }
  
  public MethodMockerBuilder<ReturnValueUpperBound> enforcingExpectationsStrictly() {
    return enforcingExpectationsWith(new StrictExpectationEnforcer());
  }
  
  public MethodMockerBuilder<ReturnValueUpperBound> mappingResponses() {
    return respondingWith(MappedResponderFactory.instance());
  }

  public MethodMockerBuilder<ReturnValueUpperBound> orderingResponses() {
    return respondingWith(OrderedResponderFactory.instance());
  }

  private MethodMockerBuilder<ReturnValueUpperBound> respondingWith(ResponderFactory responderFactory) {
    this.responderFactory = responderFactory;
    return this;
  }

  public <ReturnValue extends ReturnValueUpperBound> 
  MethodMocker<ReturnValue> withUntypedArgs() {
    BaseMethodMocker<ReturnValue> methodMocker = baseMethodMocker();
    return new MethodMocker<ReturnValue>(mock, methodName, methodMocker);
  }
  
  public <ReturnValue extends ReturnValueUpperBound> 
  MethodMocker0<ReturnValue> with0Args() {
    BaseMethodMocker<ReturnValue> methodMocker = baseMethodMocker();
    return new MethodMocker0<ReturnValue>(mock, methodName, methodMocker);
  }
  
  public <ReturnValue extends ReturnValueUpperBound, Arg1> 
  MethodMocker1<ReturnValue, Arg1> with1Arg() {
    BaseMethodMocker<ReturnValue> methodMocker = baseMethodMocker();
    return new MethodMocker1<ReturnValue, Arg1>(mock, methodName, methodMocker);
  }
  
  public <ReturnValue extends ReturnValueUpperBound, Arg1, Arg2> 
  MethodMocker2<ReturnValue, Arg1, Arg2> with2Args() {
    BaseMethodMocker<ReturnValue> methodMocker = baseMethodMocker();
    return new MethodMocker2<ReturnValue, Arg1, Arg2>(mock, methodName, methodMocker);
  }
  
  public <ReturnValue extends ReturnValueUpperBound, Arg1, Arg2, Arg3> 
  MethodMocker3<ReturnValue, Arg1, Arg2, Arg3> with3Args() {
    BaseMethodMocker<ReturnValue> methodMocker = baseMethodMocker();
    return new MethodMocker3<ReturnValue, Arg1, Arg2, Arg3>(mock, methodName, methodMocker);
  }
  
  public <ReturnValue extends ReturnValueUpperBound, Arg1, Arg2, Arg3, Arg4> 
  MethodMocker4<ReturnValue, Arg1, Arg2, Arg3, Arg4> with4Args() {
    BaseMethodMocker<ReturnValue> methodMocker = baseMethodMocker();
    return new MethodMocker4<ReturnValue, Arg1, Arg2, Arg3, Arg4>(mock, methodName, methodMocker);
  }
  
  public <ReturnValue extends ReturnValueUpperBound, Arg1, Arg2, Arg3, Arg4, Arg5> 
  MethodMocker5<ReturnValue, Arg1, Arg2, Arg3, Arg4, Arg5> with5Args() {
    BaseMethodMocker<ReturnValue> methodMocker = baseMethodMocker();
    return new MethodMocker5<ReturnValue, Arg1, Arg2, Arg3, Arg4, Arg5>(mock, methodName, methodMocker);
  }
  
  private <ReturnValue> BaseMethodMocker<ReturnValue> baseMethodMocker() {
    Responder<ReturnValue>      responder = responderFactory.createResponder();
    ResponsePolicy<ReturnValue> responsePolicy  = responsePolicy();
    return new BaseMethodMocker<ReturnValue>(mock, methodName, responder, responsePolicy, expectationEnforcer);
  }
  
  @SuppressWarnings("unchecked")
  private <ReturnValue> ResponsePolicy<ReturnValue> responsePolicy() {
    return (ResponsePolicy<ReturnValue>)returnPolicy.responsePolicy();
  }
  

  public boolean equals(Object object) {
    if (object instanceof MethodMockerBuilder<?>) {
      MethodMockerBuilder<?> that = (MethodMockerBuilder<?>) object;
      return this.mock.equals(that.mock) &&
             this.methodName.equals(that.methodName) &&
             this.returnPolicy.equals(that.returnPolicy) &&
             this.expectationEnforcer.equals(that.expectationEnforcer);
    }
    return false;
  }

  public int hashCode() {
    return 0;
  }
  
  public String toString() {
    return "MethodMockerBuilder(" + mock + '.' + methodName + ", " + 
                          returnPolicy + "; " + expectationEnforcer + ')';
  }
}
