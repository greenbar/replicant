// Copyright 2011 Kiel Hodges
package replicant

case class Call[ArgTuple](mock: Any, methodName: String, argTuple: ArgTuple) {
  
  override def toString = mock + "." + methodName + "(" + describe(argTuple) + ")"
  
  private def describe(argTuple: ArgTuple) = argTuple match {
    case ()               => ""
    case product: Product => product.productIterator.mkString(", ")
    case _                => argTuple.toString
  }

}
  
