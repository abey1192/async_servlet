package lib.exception

import lib.http.HttpStatus

class NotFoundException extends AsyncServletException(2) {

  val status = HttpStatus.NotFound

}
