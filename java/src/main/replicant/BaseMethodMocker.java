// Copyright 2009 Kiel Hodges
package replicant;

import java.util.*;

import junit.framework.*;
import static junit.framework.Assert.*;
import static replicant.collections.CollectionFunctions.*;

class BaseMethodMocker<ReturnValue> {

  private final Object mock;
  private final String methodName;
  
  private final ResponsePolicy<ReturnValue> responsePolicy;
  private final Responder<ReturnValue>      responder;
  private final ExpectationEnforcer         expectationEnforcer;
  
  private final List<Call> expectedCalls = list();
  private final List<Call> receivedCalls = list();

  public BaseMethodMocker(Object mock, String methodName, Responder<ReturnValue> responder, 
                          ResponsePolicy<ReturnValue> responsePolicy, ExpectationEnforcer expectationEnforcer) {
    this.mock                 = mock;
    this.methodName           = methodName;
    this.responder            = responder;
    this.responsePolicy       = responsePolicy;
    this.expectationEnforcer  = expectationEnforcer;
  }
  
  public Response<ReturnValue> expect(List<Object> arguments) {
    Call call = callWith(arguments);
    expectedCalls.add(call);
    expectationEnforcer.expect(call);
    StandardResponse<ReturnValue> response = responsePolicy.createExpectedResponse(call);
    responder.addResponseFor(call, response);
    return response;
  }
  
  public ReturnValue call(List<Object> arguments) throws AssertionFailedError {
    Call call = callWith(arguments);
    receivedCalls.add(call);
    expectationEnforcer.call(call);
    return responsePolicy.resultFor(call, responder).returnValue();
  }
  
  public void assertNotCalled() {
    assertTrue("Expected no calls to " + mock + '.' + methodName + ", but " + historyDescription(), 
               receivedCalls.isEmpty());
  }
  
  public void assertNotCalled(List<Object> arguments) {
    Call call = callWith(arguments);
    assertFalse("Did not expect " + call + ", but " + historyDescription(), 
                receivedCalls.contains(call));
  }
  
  public void assertCalledOnce() {
    assertTrue("Expected 1 call to " + mock + '.' + methodName + ", but " + historyDescription(), 
               receivedCalls.size() == 1);
  }
  
  public void assertCalled(List<Object> arguments) {
    Call call = callWith(arguments);
    assertTrue("Expected " + call + ", but " + historyDescription(), 
               receivedCalls.contains(call));
  }
  
  private String historyDescription() {
    if (receivedCalls.isEmpty())
      return "no calls were made";
    StringBuilder builder = new StringBuilder("received:");
    for (Call receivedCall : receivedCalls)
      builder.append("\n  ").append(receivedCall);
    return builder.toString();
  }
  
  public void assertExpectationsMet() {
    expectationEnforcer.assertExpectationsMet();
  }

  public  void assertExpectedCallsMade() {
    assertTrue(stateMessage(new StringBuilder("Expected call never received")), 
               receivedCalls.containsAll(expectedCalls));
  }

  private String stateMessage(StringBuilder builder) {
    for (Call receivedCall : receivedCalls)
      builder.append("\n  Received ").append(receivedCall);
    builder.append("\n");
    for (Call pendingCall : pendingCalls())
      builder.append("\n  Awaiting ").append(pendingCall);
    return builder.toString();
  }

  private List<Call> pendingCalls() {
    List<Call> pendingCalls = new ArrayList<Call>(expectedCalls);
    pendingCalls.removeAll(receivedCalls);
    return pendingCalls;
  }
  
  private Call callWith(List<Object> arguments) {
    return new Call(mock, methodName, arguments);
  }

  public boolean equals(Object object) {
    if (object instanceof BaseMethodMocker<?>) {
      BaseMethodMocker<?> that = (BaseMethodMocker<?>) object;
      return this.mock.equals(that.mock) &&
             this.methodName.equals(that.methodName) &&
             this.responder.equals(that.responder) &&
             this.responsePolicy.equals(that.responsePolicy) &&
             this.expectationEnforcer.equals(that.expectationEnforcer);
    }
    return false;
  }

  public int hashCode() {
    return 0;
  }

  public String toString() {
    return stringFor(getClass());
  }

  public String stringFor(Class<?> type) {
    return shortClassName(type) + '(' + responder + ", " + responsePolicy + ", " + expectationEnforcer + ')';
  }

  private String shortClassName(Class<?> type) {
    String className = type.getName();
    int lastDot = className.lastIndexOf('.', className.length());
    return className.substring(lastDot + 1);
  }

}
