package lib.http

import javax.servlet.http.HttpServletResponse

case class Result(status:HttpStatus, contentType:ContentType, contentBody:Array[Byte]) {

  def write(response:HttpServletResponse):Unit = {
    response.setStatus(status.toInt)
    response.setContentType(contentType.toString)
    response.setContentLength(contentBody.length)
    response.getOutputStream.write(contentBody)
  }

}

object Result {

  def apply(body:String) = new Result(contentBody = body.getBytes, status = HttpStatus.Ok, contentType = ContentType.Text)


  def apply(body:Array[Byte], status:HttpStatus = HttpStatus.Ok, contentType:ContentType = ContentType.Html) = {
    new Result(status = status, contentType = contentType, contentBody = body)
  }

}
