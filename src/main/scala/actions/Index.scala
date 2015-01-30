package actions

import javax.servlet.AsyncContext

import lib.cache.Cache
import lib.http.{BaseAction, Result}
import scalikejdbc._
import scalikejdbc.async._

import scala.concurrent.ExecutionContext.Implicits.global

class Index(ctx:AsyncContext) extends BaseAction(ctx) {

  protected def action() = {

    val lastId = Cache.find[Long]("last_user_id") {
      NamedAsyncDB('readonly).localTx { implicit s =>
        sql"SELECT id FROM users ORDER BY id DESC LIMIT 1".map(_.long(1)).single.future
      }
    }

    lastId.map { idOpt =>

      val str = idOpt.map { id => s"last user id is $id"}.getOrElse("no user")

      Result(formatter.format(Map("lastId" -> str)), contentType = formatter.contentType)
    }
  }
}

