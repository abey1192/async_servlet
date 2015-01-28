package lib.http.formatter

import javax.servlet.http.HttpServletRequest

import lib.http.ContentType

object ResponseFormatterRepository {

  private[this] val jsonFormatter = new JsonResponseFormatter
  private[this] val msgpackFormatter = new MessagePackFormatter

  def formatter(req:HttpServletRequest) = req.getHeader("ACCEPT") match {
    case ContentType.Json.mimeType => jsonFormatter
    case ContentType.MsgPack.mimeType => msgpackFormatter
    case _ => jsonFormatter
  }

}
