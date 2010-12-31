// Copyright 2009,2010 Kiel Hodges
package replicant;

final class ThrowingResult<ReturnValue> implements Result<ReturnValue> {

  public ThrowingResult(Exception exception) {
    this.exception  = exception;
  }

  private final Exception exception;
  
  public ReturnValue returnValue() throws ResponseException {
    if (exception instanceof RuntimeException)
      throw (RuntimeException)exception;
    else
      throw new ResponseException(exception);
  }

  public boolean equals(Object object) {
    if (object instanceof ThrowingResult<?>) {
      ThrowingResult<?> that = (ThrowingResult<?>) object;
      return this.exception.equals(that.exception);
    }
    return false;
  }

  public int hashCode() {
    return 0;
  }
  
  public String toString() {
    return "ThrowingResponse(" + exception + ')';
  }

}
