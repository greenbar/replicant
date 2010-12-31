// Copyright 2009 Kiel Hodges
package replicant;

final class UnknownResponseException extends RuntimeException {

  private static final long serialVersionUID = 1L;
  
  public UnknownResponseException(String message) {
    super(message);
  }
  
}
