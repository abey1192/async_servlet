package asyncserv

import javax.servlet.AsyncContext

import lib.http.{NotFoundAction, BaseAction}

import scala.util.Try


/*
 mapping path to ActionClass

 /package/names/class_name -> actions.package.names.ClassName

 */
trait ActionFinder {

  def findAction(path:String, ctx:AsyncContext):BaseAction = {
    findActionClass(path)
      .flatMap( createInstance(_, ctx) )
      .getOrElse(new NotFoundAction(ctx))
  }

  private def findActionClass(path:String):Option[Class[BaseAction]] = {
    try {
      fullClassName(path).map( Class.forName(_).asInstanceOf[Class[BaseAction]] )
    }
    catch {
      case e:ClassNotFoundException => None
      case e:Throwable              => throw e
    }
  }

  private def createInstance(actionClass:Class[BaseAction], ctx:AsyncContext) = {
    try {
      val cons = actionClass.getConstructor(classOf[AsyncContext])
      Some(cons.newInstance(ctx))
    }
    catch {
      case e:NoSuchMethodError => None
      case e:Throwable         => throw e
    }
  }

  private def fullClassName(path:String) = {
    val fragments = path.split("/").drop(1)
    fragments.lastOption.map { e =>  "actions." + packageName(fragments) + className(e) }
  }

  private def className(snakeCaseName:String) = snakeCaseName.split("_").map(_.capitalize).mkString

  private def packageName(fragments:Array[String]) = {
    val ss = fragments.dropRight(1)
    if(ss.length == 0) "" else ss.mkString(".") + "."
  }

}
