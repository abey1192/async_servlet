package asyncserv

import javax.servlet.{ServletContextEvent, ServletContextListener}
import scalikejdbc.async.AsyncConnectionPool

class AsyncServletContextListener extends ServletContextListener {

  def contextInitialized(ctx:ServletContextEvent) = {
    AsyncConnectionPool.singleton("jdbc:mysql://localhost:3306/test", "root", "")
  }

  def contextDestroyed(ctx:ServletContextEvent) = {
    AsyncConnectionPool.closeAll()
  }

}
