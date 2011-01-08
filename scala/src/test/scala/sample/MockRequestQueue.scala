// Copyright 2011 Kiel Hodges
package sample

class MockRequestQueue extends RequestQueue { mock =>
  object method {
    import replicant._
    val nextRequest = Mocker0[Option[Request]](mock, "nextRequest")
  }
  def nextRequest = method.nextRequest()
}
