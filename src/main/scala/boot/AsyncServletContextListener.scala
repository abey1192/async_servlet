package boot

import javax.servlet.{ServletContextEvent, ServletContextListener}

import com.typesafe.config.ConfigFactory

class AsyncServletContextListener extends ServletContextListener {
  private[this] val database = new DatabaseConfigurator
  private[this] val memcached = new CacheConfigurator

  def contextInitialized(contextEvent:ServletContextEvent) = {
    val conf = ConfigFactory.load(environment)

    database.setup(conf)
    memcached.setup(conf)
  }


  def contextDestroyed(ctx:ServletContextEvent) = {
    memcached.tearDown()
    database.tearDown()
  }

  private def environment = System.getProperties.getProperty("environment") match {
    case null => "development.conf"
    case env  => env + ".conf"
  }
}

