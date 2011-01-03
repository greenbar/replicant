package replicant

class MappedResponder[ArgTuple, Result] extends Responder[ArgTuple, Result] {
  
  import scala.collection.mutable.Map
  private val responses: Map[Call[ArgTuple], () => Result] = Map()

  def update(call: Call[ArgTuple], response: () => Result) { responses(call) = response }
  
  def apply(call: Call[ArgTuple]): Either[UnknownResponseException, () => Result] = responses.get(call) match {
    case Some(response) => Right(response)
    case None           => Left(new UnknownResponseException("No response expected for " + call))
  }
  
}
  
