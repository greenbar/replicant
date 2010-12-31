// Copyright 2009 Kiel Hodges
package replicant;

import junit.framework.*;

@SuppressWarnings("deprecation")
final class NullExpecter implements ExpectationEnforcer, Expecter {

  private static final NullExpecter INSTANCE = new NullExpecter();

  public static NullExpecter instance() {
    return INSTANCE;
  }

  private NullExpecter() {
  }
  
  public void expect(Call call) {
  }
  
  public void call(Call call) throws AssertionFailedError {
  }

  public void assertExpectationsMet() throws AssertionFailedError {
  }

  public String toString() {
    return "NullExpecter()";
  }

}
