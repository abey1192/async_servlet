package lib.http

case class Result(status:HttpStatus, contentType:ContentType, contentBody:Array[Byte])

object Result {

  def apply(body:String, status:HttpStatus = HttpStatus.Ok, contentType:ContentType = ContentType.Html) = {
    new Result(status = status, contentType = contentType, contentBody = body.getBytes)
  }

}