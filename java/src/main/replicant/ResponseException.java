// Copyright 2009 Kiel Hodges
package replicant;

public class ResponseException extends RuntimeException {
  private static final long serialVersionUID = 1L;
  
  public ResponseException(Throwable cause) {
    super("Programmed response", cause);
  }

  public <NestedException extends Throwable> ResponseException throwCauseIf(Class<NestedException> exceptionType) 
  throws NestedException {
    Throwable cause = getCause();
    if (exceptionType.isInstance(cause))
      throw exceptionType.cast(cause);
    return this;
  }

  public void throwSelf() {
    throw this;
  }
  
  public boolean equals(Object object) {
    if (object instanceof ResponseException) {
      ResponseException that = (ResponseException) object;
      return this.getCause().equals(that.getCause()) &&
      this.getMessage().equals(that.getMessage());
    }
    return false;
  }
  
  public int hashCode() {
    return 0;
  }
  
}
