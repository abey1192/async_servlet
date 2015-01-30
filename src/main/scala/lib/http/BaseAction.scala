package lib.http

import javax.servlet.AsyncContext
import javax.servlet.http.{HttpServletRequest, HttpServletResponse}

import lib.exception.AsyncServletException
import lib.http.formatter.ResponseFormatterTrait

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}



abstract class BaseAction(ctx:AsyncContext) extends Runnable with ResponseFormatterTrait {

  protected val request:HttpServletRequest = ctx.getRequest.asInstanceOf[HttpServletRequest]
  protected val response:HttpServletResponse = ctx.getResponse.asInstanceOf[HttpServletResponse]

  def run() = {
    try {
      val result = invoke()

      result.map(_.write(response)).andThen {
        case Success(_) => ctx.complete()
        case Failure(e) => errorResponse(e)
      }
    }
    catch {
      case e:AsyncServletException => errorResponse(e)
      case e:Throwable => throw e
      case _ =>
    }
  }

  private def invoke() = {
    request.getMethod match {
      case "GET"  => action()
      case "POST" => action()
      case _      => action()
    }
  }

  private def errorResponse(exception:Throwable) = exception match {
    case ex:AsyncServletException => setResponseWithException(ex)
    case _                        => throw exception
  }

  private def setResponseWithException(ex:AsyncServletException) = {
    Result(formatter.exception(ex), status = ex.status, contentType = formatter.contentType).write(response)
    ctx.complete()
  }

  protected def action():Future[Result]

}
