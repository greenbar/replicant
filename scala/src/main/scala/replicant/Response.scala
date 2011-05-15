package replicant

private abstract class Response[Result] {

  def value(fallback: ResponseFallback[Result]): Result

}

private class ValueResponse[Result](value: () => Result) extends Response[Result] {
  
  def value(fallback: ResponseFallback[Result]): Result = value()

}

private class UnknownResponse[Result](description: String) extends Response[Result] {
  
  def value(fallback: ResponseFallback[Result]): Result = fallback(description)
  
}