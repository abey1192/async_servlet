package lib.http.formatter

import javax.servlet.http.HttpServletRequest

import lib.http.{HttpStatus, BaseAction, Result, ContentType}

trait ResponseFormatterTrait { this:BaseAction =>

  private[this] val jsonFormatter = new JsonResponseFormatter
  private[this] val msgpackFormatter = new MessagePackFormatter

  protected def formatter:ResponseFormatter = request.getHeader("ACCEPT") match {
    case ContentType.Json.mimeType => jsonFormatter
    case ContentType.MsgPack.mimeType => msgpackFormatter
    case _ => jsonFormatter
  }

  def render(in:Map[_, _], status:HttpStatus = HttpStatus.Ok) = {
    val f = formatter
    Result(status = status, f.contentType, f.format(in))
  }

}

