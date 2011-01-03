package object replicant {

  implicit val responseFallbackForAnyRef : ResponseFallback[Null   ] = NoResponse
  implicit val responseFallbackForBoolean: ResponseFallback[Boolean] = NoResponse
  implicit val responseFallbackForChar   : ResponseFallback[Char   ] = NoResponse
  implicit val responseFallbackForByte   : ResponseFallback[Byte   ] = NoResponse
  implicit val responseFallbackForShort  : ResponseFallback[Short  ] = NoResponse
  implicit val responseFallbackForInt    : ResponseFallback[Int    ] = NoResponse
  implicit val responseFallbackForLong   : ResponseFallback[Long   ] = NoResponse
  implicit val responseFallbackForFloat  : ResponseFallback[Float  ] = NoResponse
  implicit val responseFallbackForDouble : ResponseFallback[Double ] = NoResponse
  implicit val responseFallbackForUnit   : ResponseFallback[Unit   ] = new FallbackValue(())

}