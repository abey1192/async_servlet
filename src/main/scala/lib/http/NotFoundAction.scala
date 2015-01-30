package lib.http

import javax.servlet.AsyncContext

import lib.exception.NotFoundException

import scala.concurrent.Future

class NotFoundAction(ctx:AsyncContext) extends BaseAction(ctx) {

  def action():Future[Result] = {
    throw new NotFoundException
  }

}
