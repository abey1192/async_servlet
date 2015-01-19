package lib.cache

import shade.memcached._, MemcachedCodecs._
import scala.concurrent.{Future, duration}
import scala.concurrent.ExecutionContext.Implicits.{global => ec}
import duration._

object Cache {
  private[this] var memcached:Memcached = null

  private[this] var defaultLifeTime = 24.hours

  def configure(servers:List[String], expire:Int = 24 * 3600):Unit = {

    System.out.println(s"servers = ${servers.mkString(" ")}, expire = $expire")

    memcached = Memcached(Configuration(servers.mkString(" ")), ec)
    defaultLifeTime = expire.second
  }

  def close():Unit = if(memcached != null) memcached.close()


  case class CacheProxy[T](entity:T)

  def get[T](key:String):Future[Option[T]] = memcached.get[CacheProxy[T]](key).map { proxyOpt =>
    proxyOpt.map(_.entity)
  }

  def set[T](key:String, entity:T, expiration:Duration = defaultLifeTime) = {
    memcached.set[CacheProxy[T]](key, CacheProxy[T](entity), expiration)
  }

  def find[T](key:String)(f: => Future[Option[T]]):Future[Option[T]] = {
    get[T](key).flatMap {
      case None => store(key)(f)
      case Some(entity) => Future { Some(entity) }
    }
  }

  def store[T](key:String)(f: => Future[Option[T]]):Future[Option[T]] = f.map {
    case None => None
    case Some(entity) => set(key, entity); Some(entity)
  }

}