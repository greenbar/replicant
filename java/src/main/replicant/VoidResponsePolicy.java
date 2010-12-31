// Copyright 2009 Kiel Hodges
package replicant;

final class VoidResponsePolicy implements ResponsePolicy<Void> {

  public static VoidResponsePolicy instance() {
    return INSTANCE;
  }

  private static final VoidResponsePolicy INSTANCE = new VoidResponsePolicy();
  
  public StandardResponse<Void> createExpectedResponse(Call call) {
    StandardResponse<Void> response = new StandardResponse<Void>(call);
    response.returning(null);
    return response;
  }

  public Result<Void> resultFor(Call call, Responder<Void> responder) throws UnknownResponseException {
    try {
      return responder.resultFor(call);
      
    } catch (UnknownResponseException e) {
      return DEFAULT_RESULT;
    }
  }

  private static final ReturningResult<Void> DEFAULT_RESULT = new ReturningResult<Void>(null);
  
}
