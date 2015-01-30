package asyncserv

import javax.servlet.http.{HttpServlet, HttpServletRequest, HttpServletResponse}

import actions.Index

class ServletEntry extends HttpServlet with ActionFinder {

  override protected def doGet(req:HttpServletRequest, res:HttpServletResponse):Unit = {
    val asyncContext = req.startAsync(req, res)

    asyncContext.setTimeout(5 * 60 * 60)

    val action = findAction(req.getPathInfo, asyncContext)

    asyncContext.start(action)
  }


}

