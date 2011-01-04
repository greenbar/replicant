// Copyright 2011 Kiel Hodges
package replicant

sealed abstract class ResponseFallback[+Result] {
  def apply(exception: UnknownResponseException): () => Result
}

object NoResponse extends ResponseFallback[Nothing] {
  def apply(exception: UnknownResponseException) = throw exception
}

class FallbackValue[Result](val value: Result) extends ResponseFallback[Result] {
  def apply(exception: UnknownResponseException) = () => value
}
