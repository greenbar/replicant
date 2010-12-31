// Copyright 2009 Kiel Hodges
package replicant;

final class ChainingResponsePolicy<MockObject> implements ResponsePolicy<MockObject> {

  private final MockObject mockObject;

  public ChainingResponsePolicy(MockObject mockObject) {
    this.mockObject = mockObject;
  }

  public StandardResponse<MockObject> createExpectedResponse(Call call) {
    StandardResponse<MockObject> response = new StandardResponse<MockObject>(call);
    response.returning(mockObject);
    return response;
  }

  public Result<MockObject> resultFor(Call call, Responder<MockObject> responder) throws UnknownResponseException {
    try {
      return responder.resultFor(call);
      
    } catch (UnknownResponseException e) {
      return new ReturningResult<MockObject>(mockObject);
    }
  }
  
  public boolean equals(Object object) {
    if (object instanceof ChainingResponsePolicy<?>) {
      ChainingResponsePolicy<?> that = (ChainingResponsePolicy<?>) object;
      return this.mockObject == that.mockObject;
    }
    return false;
  }


  public int hashCode() {
    return 0;
  }

}
