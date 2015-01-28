package lib.http.formatter

import lib.exception.AsyncServletException
import lib.http.ContentType

abstract class ResponseFormatter {

  def contentType:ContentType

  def format(out:Map[_,_]):Array[Byte]

  def exception(ex:AsyncServletException):Array[Byte] = {
    val out = Map("result" -> ex.resultCode, "message" -> ex.getMessage)
    format(out)
  }

}
