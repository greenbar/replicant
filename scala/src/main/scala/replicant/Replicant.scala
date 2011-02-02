package replicant

private[replicant] trait Replicant[Self] {

  private[replicant] def withArgs[NewArgs](args: NewArgs): Self

}

object Replicant {

  def withNoArgList[ResultValue](mock: Any, methodName: String)
    (implicit fallback: ResponseFallback[ResultValue]): Result[ResultValue] = {
      val baseCall = Call(mock, methodName)
      Result(baseCall, CallHandler(baseCall, fallback))
    }

  def with1ArgList[Args, ResultValue](mock: Any, methodName: String)
    (implicit fallback: ResponseFallback[ResultValue]): ArgList[Args, Result[ResultValue]] = 
      ArgList(withNoArgList(mock, methodName))

  def with2ArgLists[Args1, Args2, ResultValue](mock: Any, methodName: String)
    (implicit fallback: ResponseFallback[ResultValue]): ArgList[Args1, ArgList[Args2, Result[ResultValue]]] = 
      ArgList(with1ArgList(mock, methodName))
    
}