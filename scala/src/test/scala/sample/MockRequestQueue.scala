// Copyright 2011 Kiel Hodges
package sample

import replicant._

class MockRequestQueue extends MockController[RequestQueue] { controller => 

  val mock: RequestQueue = new RequestQueue {
    def nextRequest: Option[Request] = controller.nextRequest()
  }

  val nextRequest = method("nextRequest", mock.nextRequest _)

}
