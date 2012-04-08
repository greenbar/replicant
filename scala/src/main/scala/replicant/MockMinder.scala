package replicant

trait MockMinder[Mock] {

  val mock: Mock

  protected def method[Result: ResponseFallback](methodName: String, target: () => Result) =
    Mocker0[Result](mock, methodName)

  protected def method[Arg, Result: ResponseFallback](methodName: String, target: (Arg) => Result) =
    Mocker[Arg, Result](mock, methodName)

  protected def method[Arg1, Arg2, Result: ResponseFallback](methodName: String, target: (Arg1, Arg2) => Result) =
    Mocker[(Arg1, Arg2), Result](mock, methodName)

}