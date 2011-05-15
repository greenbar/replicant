// Copyright 2011 Kiel Hodges
package replicant

sealed abstract class ResponseFallback[+Result] {
  def apply(error: String): Result
}

object NoResponse extends ResponseFallback[Nothing] {
  def apply(error: String) = throw new UnknownResponseException(error)
}

object UnitFallback extends ResponseFallback[Unit] {
  def apply(error: String) { }
}
