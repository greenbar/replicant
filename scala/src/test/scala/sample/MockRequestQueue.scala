package sample

class MockRequestQueue extends RequestQueue { mock =>
  object method {
    import replicant._
    val nextRequest = Mocker[Unit, Option[Request]](mock, "nextRequest")
  }
  def nextRequest = method.nextRequest()
}
