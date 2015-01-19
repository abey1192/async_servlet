package boot

import com.typesafe.config.Config
import lib.cache.Cache

import scala.collection.JavaConversions._

class CacheConfigurator {

  def setup(conf:Config) = {
    val cacheConf = conf.getConfig("memcached")
    val servers = cacheConf.getStringList("server").toList
    val defaultLifeTime = cacheConf.getInt("expire")

    Cache.configure(servers, defaultLifeTime)
  }

  def tearDown() = Cache.close()

}
