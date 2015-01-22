package lib.http

sealed class ContentType(val mimeType:String)

object ContentType {

  case object Html extends ContentType("text/html")
  case object Text extends ContentType("text/plain")
  case object Json extends ContentType("application/json")
  case object MsgPack extends ContentType("application/x-msgpack")

  def apply(mimeType:String) = new ContentType(mimeType)

}
