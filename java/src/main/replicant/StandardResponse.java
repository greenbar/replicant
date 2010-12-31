// Copyright 2009 Kiel Hodges
package replicant;

import java.util.*;

import static replicant.collections.CollectionFunctions.*;

final class StandardResponse<ReturnValue> implements Response<ReturnValue>, Result<ReturnValue> {

  private final Call call;
  private final List<Runnable> runnables = list();
  
  private Result<ReturnValue> result;

  public StandardResponse(Call call) {
    this.call = call;
  }

  public Response<ReturnValue> checking(final Check check) {
    runnables.add(new Runnable() {
      public void run() {
        check.check();
      }
    });
    return this;
  }
  
  public Response<ReturnValue> triggering(final Event event) {
    runnables.add(new Runnable() {
      public void run() {
        event.fire();
      }
    });
    return this;
  }
  
  public void returning(ReturnValue returnValue) {
    result = new ReturningResult<ReturnValue>(returnValue);
  }

  public void throwing(Exception exception) {
    result = new ThrowingResult<ReturnValue>(exception);
  }

  public ReturnValue returnValue() throws ResponseException {
    for (Runnable runnable : runnables)
      runnable.run();
    if (result == null)
      throw new TestingException("There is no response for " + call + '.');
    return result.returnValue();
  }
  
  public boolean equals(Object object) {
    // TODO Consider Checks and Events
    if (object instanceof StandardResponse<?>) {
      StandardResponse<?> that = (StandardResponse<?>) object;
      return areEqual(this.call, that.call) && areEqual(this.result, that.result);
    }
    return false;
  }

  private boolean areEqual(Object object1, Object object2) {
    return object1 == object2 || 
           (object1 != null && object1.equals(object2));
  }

  public int hashCode() {
    return 0;
  }

}
