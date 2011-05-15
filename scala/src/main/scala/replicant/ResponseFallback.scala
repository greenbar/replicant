// Copyright 2011 Kiel Hodges
package replicant

sealed abstract class ResponseFallback[+Result] {
  def apply(exception: UnknownResponseException): () => Result
  def apply(error: String): Result
}

object NoResponse extends ResponseFallback[Nothing] {
  def apply(exception: UnknownResponseException) = throw exception
  def apply(error: String) = throw new UnknownResponseException(error)
}

object UnitFallback extends ResponseFallback[Unit] {
  def apply(exception: UnknownResponseException) = () => ()
  def apply(error: String) { }
}
