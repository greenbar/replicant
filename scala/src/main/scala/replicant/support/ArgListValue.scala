// Copyright 2011 Kiel Hodges
package replicant.support

case class ArgListValue(args: Any) {
  
  override def toString = "(" + describe(args) + ")"
  
  private def describe(args: Any) = args match {
    case ()               => ""
    case product: Product => product.productIterator.mkString(", ")
    case _                => String.valueOf(args)
  }

}
  
