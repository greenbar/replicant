// Copyright 2011 Kiel Hodges
package replicant

sealed class Call private(val mock: Any, val methodName: String, val argLists: List[ArgList]) {
  
  def apply(args: Any) = new Call(mock, methodName, this.argLists :+ ArgList(args)) 
  
  override def equals(other: Any) = other match {
    case that: Call => this.mock == that.mock && this.methodName == that.methodName && this.argLists == that.argLists   
    case _ => false
  }
  
  override def hashCode = 41 * (41 * (41 + mock.hashCode) + methodName.hashCode) + argLists.hashCode 

  override def toString = mock + "." + methodName + argLists.mkString
  
}

object Call {

  def apply(mock: Any, methodName: String) = new Call(mock, methodName, Nil)

  def describe(calls: Traversable[Call]): String = 
    if (calls.isEmpty)
      "no calls"
    else
      calls.map("\n  " + _).mkString
}  
