package asyncserv

import javax.servlet.AsyncContext
import lib.http.{ContentType, Result, BaseAction}
import lib.cache.Cache

import scala.concurrent.ExecutionContext.Implicits.global

import scalikejdbc._, async._

class Index(ctx:AsyncContext) extends BaseAction(ctx) {
  import org.json4s._, jackson.JsonMethods._

  protected def action() = {

    val lastId = Cache.find[Long]("last_user_id") {
      NamedAsyncDB('readonly).localTx { implicit s =>
        sql"SELECT id FROM users ORDER BY id DESC LIMIT 1".map(_.long(1)).single.future
      }
    }

    lastId.map { idOpt =>

      val str = idOpt.map { id => s"last user id is $id"}.getOrElse("no user")

      Result(compact(render(JObject(JField("result", JString(str))))), contentType = ContentType.Json)

    }
  }
}

