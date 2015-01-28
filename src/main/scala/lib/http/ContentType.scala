package lib.http

sealed class ContentType(val mimeType:String) {
  override def toString:String = mimeType
}

object ContentType {

  case object Html extends ContentType("text/html")
  case object Text extends ContentType("text/plain")
  case object Json extends ContentType("application/json")
  case object MsgPack extends ContentType("application/x-msgpack")
  case object OctetStream extends ContentType("application/octet-stream")

  def apply(mimeType:String) = new ContentType(mimeType)

}
