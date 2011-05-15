package replicant

abstract class Response[Result] {
  def value(fallback: ResponseFallback[Result]): Result
}

private case class ValueResponse[Result](result: () => Result) extends Response[Result] {
  def value(fallback: ResponseFallback[Result]): Result = result()
}

private case class UnknownResponse[Result](description: String) extends Response[Result] {
  def value(fallback: ResponseFallback[Result]): Result = fallback(description)
}