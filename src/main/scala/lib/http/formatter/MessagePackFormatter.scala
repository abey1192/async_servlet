package lib.http.formatter

import lib.exception.AsyncServletException
import lib.http.ContentType
import org.msgpack.ScalaMessagePack


class MessagePackFormatter extends ResponseFormatter  {
  import org.msgpack.ScalaMessagePack._

  val contentType = ContentType.OctetStream

  def format(out:Map[_,_]):Array[Byte] = {
    messagePack.write(out)
  }

}

