// Copyright 2011 Kiel Hodges
package object replicant  {

  implicit def toDeeplyEqualArray[Type](array: Array[Type]): DeeplyEqualArray[Type] = new DeeplyEqualArray(array)

}
