package replicant.support

class TestCallHandler[Result] extends CallHandler[Result] with testing.NotImplemented {
  import replicant.Call
  def update(call: Call, response: => Result): Unit = notImplemented
  def apply(call: Call): Result                     = notImplemented
  def assertExpectationsMet: Unit                   = notImplemented
  def assertNotCalled: Unit                         = notImplemented
  def assertCalled(call: Call): Unit                = notImplemented
  def assertCalledOnce: Unit                        = notImplemented
}
