// Copyright 2009 Kiel Hodges
package replicant.sample2;

import replicant.*;
import static replicant.Replicant.*;

public class MockRequestQueue implements RequestQueue {

  public MockRequestQueue(ExpectationEnforcer expectationEnforcer) {
    nextRequest = methodMocker(this, "nextRequest").
      orderingResponses().
      enforcingExpectationsWith(expectationEnforcer).
      with0Args();
  }

  public Request nextRequest() {
    return nextRequest.call();
  }
  
  public final MethodMocker0<Request> nextRequest;
  
}
