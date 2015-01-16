package asyncserv

import javax.servlet.AsyncContext
import scala.concurrent.ExecutionContext.Implicits.global

import scalikejdbc._, async._

class Index(ctx:AsyncContext) extends BaseRoute(ctx) {

  protected def action() = {

    val lastId = AsyncDB.localTx { implicit s =>
      sql"SELECT id FROM users ORDER BY id DESC LIMIT 1".map(_.long(1)).single.future
    }

    lastId.map {
      case Some(id) => s"last user id is $id"
      case None => "no user"
    }.map(_.getBytes)
  }
}

