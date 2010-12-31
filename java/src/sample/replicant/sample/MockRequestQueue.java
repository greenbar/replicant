// Copyright 2009 Kiel Hodges
package replicant.sample;

import replicant.*;
import static replicant.Replicant.*;

public class MockRequestQueue implements RequestQueue {

  public Request nextRequest() {
    return nextRequest.call();
  }
  
  public final MethodMocker0<Request> nextRequest = 
    methodMocker(this, "nextRequest").orderingResponses().with0Args();
  
}
