// Copyright 2011 Kiel Hodges
package sample

import replicant._

class MockRequestQueue extends MockMinder[RequestQueue] { minder => 

  val mock: RequestQueue = new RequestQueue {
    def nextRequest: Option[Request] = minder.nextRequest()
  }

  val nextRequest = method("nextRequest", mock.nextRequest _)

}
