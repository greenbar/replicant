// Copyright 2009 Kiel Hodges
package replicant;

public interface ResponderFactory {
  public <ReturnValue> Responder<ReturnValue> createResponder();
}