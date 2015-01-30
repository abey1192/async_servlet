package lib.http

import java.net.{URLDecoder, URLEncoder}
import javax.servlet.http.HttpServletRequest

class HttpRequest(req:HttpServletRequest) {

  lazy val  queryString:Map[String, List[String]] = {
    req.getQueryString match {
      case null => Map.empty[String, List[String]]
      case qs => toQueryStringMap(qs)
    }
  }

  private def toQueryStringMap(qs:String) = {
    val kvs = qs.split("&").map(decodeKeyValue)
    kvs.flatten
      .groupBy(_._1)
      .map { e => (e._1, e._2.map(_._2).toList) }
  }

  private def decodeKeyValue(kv:String) = {
    val pair = kv.split("=", 2).map(URLDecoder.decode(_, "utf-8"))

    val key = pair(0)
    if(key == "") {
      None
    }
    else if(pair.length == 1) {
      Some(key, "")
    }
    else {
      Some(key, pair(1))
    }
  }

  private def urlDecode(encoded:String) = {
    URLDecoder.decode(encoded, "utf-8")
  }


}
