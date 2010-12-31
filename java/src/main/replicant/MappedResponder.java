// Copyright 2009 Kiel Hodges
package replicant;

import java.util.*;

import static replicant.collections.CollectionFunctions.*;

final class MappedResponder<ReturnValue> implements Responder<ReturnValue> {
  
  private final Map<Call, Result<ReturnValue>> responses = map();
  
  public void addResponseFor(Call call, Result<ReturnValue> response) {
    responses.put(call, response);
  }

  public Result<ReturnValue> resultFor(Call call) {
    if (responses.containsKey(call))
      return responses.get(call);
    
    StringBuffer message = 
      new StringBuffer("There is no response for ").append(call).append(".\nThere are responses for: ");
    for (Call expectedCall : responses.keySet())
      message.append("\n  ").append(expectedCall);
    throw new UnknownResponseException(message.toString());
  }
  
  public boolean equals(Object object) {
    if (object instanceof MappedResponder<?>) {
      MappedResponder<?> that = (MappedResponder<?>) object;
      return this.responses.equals(that.responses);
    }
    return false;
  }

  public int hashCode() {
    return 0;
  }
  
  
  public String toString() {
    return "MappedResponder(" + responses + ')';
  }

}