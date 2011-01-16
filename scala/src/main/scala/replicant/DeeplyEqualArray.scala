package replicant

class DeeplyEqualArray[Type](val self: Array[Type]) {

  override def equals(other: Any) = other match {
    case that: DeeplyEqualArray[_] => self.deep == that.self.deep
    case _ => false
  }

  override def hashCode: Int = self.deep.hashCode
  
  override def toString: String = self.deep.toString
}
