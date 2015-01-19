package boot

import java.util

import com.typesafe.config.ConfigException.Missing
import com.typesafe.config.{ConfigObject, Config}
import scalikejdbc.async.AsyncConnectionPool
import scala.collection.JavaConverters._


class DatabaseConfigurator {

  def setup(conf:Config):Unit = {
    val db = conf.getConfig("db")

    setupDefault(db)

    db.root.unwrapped.asScala.foreach {
      case (name:String, value:util.HashMap[String,String]) => setupConnection(name, value)
      case _ =>
    }
  }

  private def setupConnection(name:String, conf:util.HashMap[String,String]) = {

    System.out.println(s"named = $name, conf=${conf.get("url")}")

    AsyncConnectionPool.add(name, conf.get("url"), conf.get("username"), conf.get("password"))
  }

  private def setupDefault(conf:Config) = {
    try {
      val url = conf.getString("url")
      val username = conf.getString("username")
      val password = conf.getString("password")

      AsyncConnectionPool.singleton(url, username, password)
    }
    catch {
      case _:Missing =>
      case e:Throwable => throw e
    }
  }


  def tearDown():Unit = {
    AsyncConnectionPool.closeAll()
  }
}

