package lib.exception

import lib.http.HttpStatus

class ServerErrorException extends AsyncServletException(1) {
  val status = HttpStatus.ServerError
}

