// Copyright 2009 Kiel Hodges
package replicant;

import junit.framework.*;

public interface ExpectationEnforcer {

  void expect(Call call);
  void call(Call call) throws AssertionFailedError;
  void assertExpectationsMet() throws AssertionFailedError;
  
}
