// Copyright 2011 Kiel Hodges
package sample

import replicant._

class MockRequestQueue extends MockController[RequestQueue] { self => 

  val mock: RequestQueue = new RequestQueue {
    def nextRequest: Option[Request] = self.nextRequest()
  }

  val nextRequest = method("nextRequest", mock.nextRequest _)

}
