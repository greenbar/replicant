// Copyright 2009 Kiel Hodges
package replicant.sample3;

import replicant.*;
import static replicant.Replicant.*;

public class MockRequestQueue implements RequestQueue {

  public MockRequestQueue(Expecter expecter) {
    nextRequest = methodMocker(this, "nextRequest").
      orderingResponses().
      enforcingExpectationsWith(expecter).
      with0Args();
  }

  public Request nextRequest() {
    return nextRequest.call();
  }
  
  public final MethodMocker0<Request> nextRequest;
  
}
