// Copyright 2009 Kiel Hodges
package replicant;

import java.util.*;

import junit.framework.*;
import static junit.framework.Assert.*;
import static replicant.collections.CollectionFunctions.*;

final class StrictExpectationEnforcer implements ExpectationEnforcer, Expecter {

  private final List<Call> receivedCalls  = list();
  private final List<Call> pendingCalls   = list();
  
  public void expect(Call call) {
    pendingCalls.add(call);
  }
  
  public void call(Call call) throws AssertionFailedError {
    // TODO Must throw a better exception when pendingCalls is empty
    if (!pendingCalls.get(0).equals(call))
      fail(stateMessage(new StringBuilder("Unexpected call: ").append(call)));
    
    receivedCalls.add(pendingCalls.remove(0));
  }

  public void assertExpectationsMet() throws AssertionFailedError {
    assertTrue(stateMessage(new StringBuilder("Expected call never received")), 
               pendingCalls.isEmpty());
  }
  
  private String stateMessage(StringBuilder builder) {
    for (Call receivedCall : receivedCalls)
      builder.append("\n  Received ").append(receivedCall);
    builder.append("\n");
    for (Call pendingCall : pendingCalls)
      builder.append("\n  Awaiting ").append(pendingCall);
    return builder.toString();
  }

  public boolean equals(Object object) {
    if (object instanceof StrictExpectationEnforcer) {
      StrictExpectationEnforcer that = (StrictExpectationEnforcer) object;
      return this.receivedCalls.equals(that.receivedCalls) &&
             this.pendingCalls.equals(that.pendingCalls);
    }
    return false;
  }

  public int hashCode() {
    return 0;
  }
}
