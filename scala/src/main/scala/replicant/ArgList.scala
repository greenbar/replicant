// Copyright 2011 Kiel Hodges
package replicant

case class ArgList(args: Any) {
  
  override def toString = "(" + describe(args) + ")"
  
  private def describe(args: Any) = args match {
    case ()               => ""
    case product: Product => product.productIterator.mkString(", ")
    case _                => args.toString
  }

}
  
