// Copyright 2011 Kiel Hodges
package replicant

sealed abstract class ResponseFallback[+Result] {
  def apply(exception: UnknownResponseException): () => Result
}

object NoResponse extends ResponseFallback[Nothing] {
  def apply(exception: UnknownResponseException) = throw exception
}

object UnitFallback extends ResponseFallback[Unit] {
  def apply(exception: UnknownResponseException) = () => ()
}
