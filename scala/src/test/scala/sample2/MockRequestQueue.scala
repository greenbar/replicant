// Copyright 2011 Kiel Hodges
package sample2

class MockRequestQueue extends RequestQueue { mock =>
  object method {
    import replicant._
    import experiment1._
    val nextRequest: Result[Option[Request]] = Replicant.withNoArgList(mock, "nextRequest")
  }
  def nextRequest = method.nextRequest.response
}
