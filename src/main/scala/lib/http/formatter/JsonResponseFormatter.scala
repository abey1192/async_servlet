package lib.http.formatter

import lib.exception.AsyncServletException
import lib.http.ContentType
import org.joda.time.DateTime

class JsonResponseFormatter extends ResponseFormatter {
  import org.json4s._
  import org.json4s.jackson.JsonMethods._

  def contentType:ContentType = ContentType.Json

  def format(out:Map[_, _]):Array[Byte] = {
    val res = convert(out)
    compact(render(res)).getBytes
  }

  private def convert(res:Map[_,_]):JValue = {
    val values = res.map(toJField).toList
    JObject(values)
  }

  private def toJField(keyValue:(_,_)):JField = JField(keyValue._1.toString, toJValue(keyValue._2))

  private def toJValue(obj:Any):JValue = obj match {
    case obj:Int       => JInt(obj)
    case obj:Long      => JDecimal(obj)
    case obj:String    => JString(obj)
    case obj:Seq[_]    => JArray(obj.map(toJValue).toList)
    case obj:Map[_, _] => convert(obj)
    case obj:DateTime  => dumpDateTime(obj)
    case obj:Double    => JDouble(obj)
    case obj:Float     => JDouble(obj.toDouble)
    case _ => JNull
  }

  private def dumpDateTime(dt:DateTime) = JDecimal(dt.getMillis / 1000)
}
