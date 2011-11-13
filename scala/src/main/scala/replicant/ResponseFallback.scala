// Copyright 2011 Kiel Hodges
package replicant

sealed abstract class ResponseFallback[+Result] {
  private[replicant] def apply(error: String): Result
}

private[replicant] object NoResponse extends ResponseFallback[Nothing] {
  def apply(error: String) = throw new UnknownResponseException(error)
}

private[replicant] object UnitFallback extends ResponseFallback[Unit] {
  def apply(error: String) { }
}

object ResponseFallback {
  implicit def responseFallbackFor[Result]: ResponseFallback[Result] = NoResponse
  implicit val responseFallbackForUnit:     ResponseFallback[Unit]   = UnitFallback
}