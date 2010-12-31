// Copyright 2009 Kiel Hodges
package replicant;

public interface Response<ReturnValue> {

  Response<ReturnValue> checking(Check assertion);
  Response<ReturnValue> triggering(Event event);
  void returning(ReturnValue result);
  void throwing(Exception exception);

}