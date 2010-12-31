// Copyright 2009 Kiel Hodges
package replicant;

public interface Responder<ReturnValue> {

  void addResponseFor(Call call, Result<ReturnValue> response);
  Result<ReturnValue> resultFor(Call call);
  
}
