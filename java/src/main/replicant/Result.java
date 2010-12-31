// Copyright 2009 Kiel Hodges
package replicant;

interface Result<ReturnValue> {

  ReturnValue returnValue() throws ResponseException;
  
}
