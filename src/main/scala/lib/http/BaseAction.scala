package lib.http

import javax.servlet.AsyncContext
import javax.servlet.http.HttpServletResponse

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}



abstract class BaseAction(ctx:AsyncContext) extends Runnable {

  private[this] val response:HttpServletResponse = ctx.getResponse.asInstanceOf[HttpServletResponse]

  def run() = {
    val result = action()

    result.map { r =>
      response.setStatus(r.status.code)
      response.setContentType(r.contentType.mimeType)
      response.setContentLength(r.contentBody.length)
      response.getOutputStream.write(r.contentBody)

      if(r.status.isError) {
        response.sendError(r.status.code)
      }

    }.andThen {
      case Success(_) => ctx.complete()
      case Failure(e) => errorResponse(e)
    }
  }

  protected def errorResponse(e:Throwable) = {
    val message = s"<html><body>Error ${e.getMessage}</body></html>".getBytes

    response.setContentType("text/html")
    response.setContentLength(message.length)
    response.getOutputStream.write(message)

    ctx.complete()
  }

  protected def action():Future[Result]

}
