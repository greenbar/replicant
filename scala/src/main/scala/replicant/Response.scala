package replicant

private abstract class Response[Result] {
  def value(fallback: ResponseFallback[Result]): Result
}

private class ValueResponse[Result](result: Result) extends Response[Result] {
  def value(fallback: ResponseFallback[Result]): Result = result
}

private class UnknownResponse[Result](description: String) extends Response[Result] {
  def value(fallback: ResponseFallback[Result]): Result = fallback(description)
}