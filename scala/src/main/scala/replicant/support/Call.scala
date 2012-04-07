// Copyright 2011 Kiel Hodges
package replicant.support

sealed class Call private(mock: Any, methodName: String, argLists: List[ArgListValue]) {
  
  def apply(args: Any) = new Call(mock, methodName, this.argLists :+ ArgListValue(args)) 
  
  override def toString = mock + "." + methodName + argLists.mkString
  
  private val equalityKey = (mock, methodName, argLists)

  override def equals(other: Any) = other match {
    case that: Call => this.equalityKey == that.equalityKey   
    case _ => false
  }
  
  override def hashCode = equalityKey.hashCode 

}

object Call {

  def apply(mock: Any, methodName: String) = new Call(mock, methodName, Nil)

  def describe(calls: Traversable[Call]): String = 
    if (calls.isEmpty) "no calls" else calls.map("\n  " + _).mkString
}  
