package replicant.experiment1

private[experiment1] trait Replicant[Self] {

  def assertCalledOnce(): Unit
  def assertNotCalled(): Unit
  def assertAllResponsesUsed(): Unit

  private[experiment1] def withArgs[NewArgs](args: NewArgs): Self
  
}

object Replicant {

  import replicant.ResponseFallback
  
  def withNoArgList[ResultValue: ResponseFallback](mock: Any, methodName: String): Result[ResultValue] = {
    import replicant.{Call, CallHandler}
    val baseCall = Call(mock, methodName)
    Result(baseCall, CallHandler(baseCall, implicitly[ResponseFallback[ResultValue]]))
  }

  def with1ArgList[Args, ResultValue: ResponseFallback](mock: Any, methodName: String): 
    ArgList[Args, Result[ResultValue]] = 
      ArgList(withNoArgList(mock, methodName))

  def with2ArgLists[Args1, Args2, ResultValue: ResponseFallback](mock: Any, methodName: String): 
    ArgList[Args1, ArgList[Args2, Result[ResultValue]]] = 
      ArgList(with1ArgList(mock, methodName))
    
}