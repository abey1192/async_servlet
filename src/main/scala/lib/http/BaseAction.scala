package lib.http

import javax.servlet.AsyncContext
import javax.servlet.http.{HttpServletRequest, HttpServletResponse}

import lib.exception.AsyncServletException
import lib.http.formatter.ResponseFormatterRepository

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}



abstract class BaseAction(ctx:AsyncContext) extends Runnable {

  private[this] val request:HttpServletRequest = ctx.getRequest.asInstanceOf[HttpServletRequest]
  private[this] val response:HttpServletResponse = ctx.getResponse.asInstanceOf[HttpServletResponse]

  protected val formatter = ResponseFormatterRepository.formatter(request)

  def run() = {
    val result = invoke()

    result.map { r =>
      response.setStatus(r.status.toInt)
      response.setContentType(r.contentType.toString)
      response.setContentLength(r.contentBody.length)
      response.getOutputStream.write(r.contentBody)

    }.andThen {
      case Success(_) => ctx.complete()
      case Failure(e) => errorResponse(e)
    }
  }

  private def errorResponse(exception:Throwable) = exception match {
    case ex: AsyncServletException => setResponseWithException(ex)
    case _ => throw exception
  }

  private def setResponseWithException(ex:AsyncServletException) = {
    val result = formatter.exception(ex)

    response.setStatus(ex.status.toInt)
    response.setContentType(formatter.contentType.toString)
    response.setContentLength(result.length)
    response.getOutputStream.write(result)

    ctx.complete()
  }

  private def invoke():Future[Result] = {
    request.getMethod match {
      case "GET"  => action()
      case "POST" => action()
      case _      => action()
    }
  }


  protected def action():Future[Result]

}
