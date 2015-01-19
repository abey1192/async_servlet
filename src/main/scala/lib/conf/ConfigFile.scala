package lib.conf

import java.io.FileInputStream
import java.util.Properties

import scala.collection.JavaConverters._

class ConfigFile(path:String) {

  private[this] lazy val props = {
    val p = new Properties()
    p.load(new FileInputStream(path))
    p
  }.asScala

  def property(key:String) = props.get(key)

}
