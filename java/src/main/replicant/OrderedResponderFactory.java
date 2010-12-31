// Copyright 2009 Kiel Hodges
package replicant;

final class OrderedResponderFactory implements ResponderFactory {

  public static ResponderFactory instance() {
    return INSTANCE;
  }

  private static final OrderedResponderFactory INSTANCE = new OrderedResponderFactory();
  
  private OrderedResponderFactory() {}
  
  public <ReturnValue> Responder<ReturnValue> createResponder() {
    return new OrderedResponder<ReturnValue>();
  }

}