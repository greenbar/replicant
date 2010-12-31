// Copyright 2009 Kiel Hodges
package replicant;

import java.util.*;

import junit.framework.*;
import static junit.framework.Assert.*;
import static replicant.collections.CollectionFunctions.*;

@SuppressWarnings("deprecation")
final class LenientExpectationEnforcer implements ExpectationEnforcer, Expecter {

  private final List<Call> receivedCalls = list();
  private final List<Call> expectedCalls = list();
  
  public void expect(Call call) {
    if (!expectedCalls.contains(call))
      expectedCalls.add(call);
  }
  
  public void call(Call call) throws AssertionFailedError {
    receivedCalls.add(call);
  }

  public void assertExpectationsMet() throws AssertionFailedError {
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

  private Set<Call> receivedCalls() {
    return new HashSet<Call>(receivedCalls);
  }
  
  private Set<Call> expectedCalls() {
    return new HashSet<Call>(expectedCalls);
  }
  
  private List<Call> pendingCalls() {
    List<Call> pendingCalls = new ArrayList<Call>(expectedCalls);
    pendingCalls.removeAll(receivedCalls);
    return pendingCalls;
  }
  
  public boolean equals(Object object) {
    if (object instanceof LenientExpectationEnforcer) {
      LenientExpectationEnforcer that = (LenientExpectationEnforcer) object;
      return this.receivedCalls().equals(that.receivedCalls()) &&
             this.expectedCalls().equals(that.expectedCalls());
    }
    return false;
  }

  public int hashCode() {
    return 0;
  }
}
