package asyncserv

import javax.servlet.http.{HttpServletResponse, HttpServletRequest, HttpServlet}

class Entry extends HttpServlet {

  override protected def doGet(req:HttpServletRequest, res:HttpServletResponse):Unit = {
    val asyncContext = req.startAsync(req, res)

    asyncContext.setTimeout(5 * 60 * 60)

    asyncContext.start(new Index(asyncContext))
  }

}
