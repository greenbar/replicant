// Copyright 2009 Kiel Hodges
package replicant;

class VoidReturnPolicy implements ReturnPolicy {

  private static final VoidReturnPolicy INSTANCE = new VoidReturnPolicy();

  private VoidReturnPolicy() {
  }
  
  public static VoidReturnPolicy instance() {
    return INSTANCE;
  }

  public ResponsePolicy<Void> responsePolicy() {
    return VoidResponsePolicy.instance();
  }

}
