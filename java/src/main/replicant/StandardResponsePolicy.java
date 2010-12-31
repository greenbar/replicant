// Copyright 2009 Kiel Hodges
package replicant;

final class StandardResponsePolicy<ReturnValue> implements ResponsePolicy<ReturnValue> {

  @SuppressWarnings("unchecked")
  public static <ReturnValue> StandardResponsePolicy<ReturnValue> instance() {
    return (StandardResponsePolicy<ReturnValue>) INSTANCE;
  }
  
  private static final StandardResponsePolicy<Object> INSTANCE = new StandardResponsePolicy<Object>();

  public StandardResponse<ReturnValue> createExpectedResponse(Call call) {
    return new StandardResponse<ReturnValue>(call);
  }

  public Result<ReturnValue> resultFor(Call call, Responder<ReturnValue> responder) throws UnknownResponseException {
    return responder.resultFor(call);
  }

}
