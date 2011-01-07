// Copyright 2011 Kiel Hodges
package replicant

case class Call(mock: Any, methodName: String, argList: Any) {
  
  override def toString = mock + "." + methodName + ArgList(argList)
  
}
  
