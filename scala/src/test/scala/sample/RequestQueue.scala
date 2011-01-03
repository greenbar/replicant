package sample

trait RequestQueue {
  def nextRequest: Option[Request]
}

