package asyncserv

import javax.servlet.AsyncContext

import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success, Try}

abstract class BaseRoute(ctx:AsyncContext) extends Runnable {

  private[this] val response = ctx.getResponse

  def run() = {
    val result = action()

    result.map { bytes =>
      response.setContentType("text/plain")
      response.setContentLength(bytes.length)
      response.getOutputStream.write(bytes)

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

  protected def action():Future[Array[Byte]]

}
