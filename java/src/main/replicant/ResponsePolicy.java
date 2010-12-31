// Copyright 2009 Kiel Hodges
package replicant;

interface ResponsePolicy<ReturnValue> {

  StandardResponse<ReturnValue> createExpectedResponse(Call call);
  Result<ReturnValue> resultFor(Call call, Responder<ReturnValue> responder) throws UnknownResponseException;
  
}
