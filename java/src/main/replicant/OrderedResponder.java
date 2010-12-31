// Copyright 2009 Kiel Hodges
package replicant;

import java.util.*;

import replicant.collections.*;
import static replicant.collections.CollectionFunctions.*;

final class OrderedResponder<ReturnValue> implements Responder<ReturnValue> {

  private final List<Pair<Call, Result<ReturnValue>>> results = list();
  
  public void addResponseFor(Call call, Result<ReturnValue> response) {
    results.add(pair(call, response));
  }

  public Result<ReturnValue> resultFor(Call call) {
    if (results.isEmpty())
      throw new UnknownResponseException("There is no response for " + call + " because responses have been exhausted.");

    Pair<Call, Result<ReturnValue>> result = results.remove(0);
    if (result.first().equals(call))
      return result.second();
    
    throw new UnknownResponseException("The next response is for " + result.first() + ", not for " + call + '.');
  }

  public boolean equals(Object object) {
    if (object instanceof OrderedResponder) {
      OrderedResponder<?> that = (OrderedResponder<?>) object;
      return this.results.equals(that.results);
    }
    return false;
  }

  public int hashCode() {
    return 0;
  }
  
  public String toString() {
    return "OrderedResponder(" + results + ')';
  }


}
