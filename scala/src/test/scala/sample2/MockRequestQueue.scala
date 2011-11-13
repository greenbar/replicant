// Copyright 2011 Kiel Hodges
package sample2

import replicant._
import experiment1._

class MockRequestQueue extends XMockController[RequestQueue] { self =>

  val testDouble: RequestQueue = new RequestQueue {
    def nextRequest: Option[Request] = self.nextRequest.response
  }

  val nextRequest = method("nextRequest", testDouble.nextRequest _)
  
}
