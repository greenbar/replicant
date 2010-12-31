// Copyright 2009 Kiel Hodges
package replicant;

class MappedResponderFactory implements ResponderFactory {

  public static ResponderFactory instance() {
    return INSTANCE;
  }

  private static final MappedResponderFactory INSTANCE = new MappedResponderFactory();
  
  private MappedResponderFactory() {
  }
  
  public <ReturnValue> Responder<ReturnValue> createResponder() {
    return new MappedResponder<ReturnValue>();
  }

}