package replicant.experiment1

trait XMockController[TestDouble] {

  val testDouble: TestDouble

  import replicant.ResponseFallback
  import replicant.support.{Call, CallHandler}

  protected def method[ResultValue: ResponseFallback](methodName: String, target: () => ResultValue) = {
    val baseCall = Call(testDouble, methodName)
    Result[ResultValue](baseCall, CallHandler(baseCall, implicitly[ResponseFallback[ResultValue]]))
  }

  protected def method[Arg, ResultValue: ResponseFallback](methodName: String, target: (Arg) => ResultValue) = {
    val baseCall = Call(testDouble, methodName)
    ArgList[Arg, Result[ResultValue]](Result(baseCall, CallHandler(baseCall, implicitly[ResponseFallback[ResultValue]])))
  }

  protected def method[Arg1, Arg2, ResultValue: ResponseFallback](methodName: String, target: (Arg1, Arg2) => ResultValue) = {
    val baseCall = Call(testDouble, methodName)
    ArgList[(Arg1, Arg2), Result[ResultValue]](Result(baseCall, CallHandler(baseCall, implicitly[ResponseFallback[ResultValue]])))
  }

}