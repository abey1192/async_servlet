package lib.http


sealed class HttpStatus(var code:Int) {
  def isError:Boolean = code >= 500
}

object HttpStatus {

  case object Ok extends HttpStatus(200)
  case object Created extends HttpStatus(201)

  case object BadRequest extends HttpStatus(400)
  case object Unauthorized extends HttpStatus(401)
  case object Forbidden extends HttpStatus(403)
  case object NotFound extends HttpStatus(404)

  case object RequestEntityTooLarge extends HttpStatus(413)

  case object ServerError extends HttpStatus(500)
  case object BadGateway extends HttpStatus(502)
  case object ServiceUnavailable extends HttpStatus(503)

}

