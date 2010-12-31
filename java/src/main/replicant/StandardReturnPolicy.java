// Copyright 2009 Kiel Hodges
package replicant;

class StandardReturnPolicy implements ReturnPolicy {

  private static final StandardReturnPolicy INSTANCE = new StandardReturnPolicy();

  private StandardReturnPolicy() {
  }
  
  public static StandardReturnPolicy instance() {
    return INSTANCE;
  }

  public ResponsePolicy<?> responsePolicy() {
    return StandardResponsePolicy.instance();
  }

}
