package lib.exception

import javax.servlet.http.HttpServletResponse

import lib.http.HttpStatus

abstract class AsyncServletException(val resultCode:Int) extends RuntimeException {

  val status:HttpStatus

}

