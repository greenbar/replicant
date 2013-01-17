package replicant

object Mock {
  implicit def toMock[Subject](minder: Mock[Subject]) : Subject = minder.mock
}

trait Mock[Subject] {

  val mock: Subject

  protected def method[Result: ResponseFallback](methodName: String, target: () => Result) =
    Mocker0[Result](mock, methodName)

  protected def method[Arg, Result: ResponseFallback](methodName: String, target: (Arg) => Result) =
    Mocker[Arg, Result](mock, methodName)

  protected def method[Arg1, Arg2, Result: ResponseFallback](methodName: String, target: (Arg1, Arg2) => Result) =
    Mocker[(Arg1, Arg2), Result](mock, methodName)


}