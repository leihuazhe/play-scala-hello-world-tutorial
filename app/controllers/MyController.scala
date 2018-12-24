package controllers

import akka.util.ByteString
import javax.inject.Inject
import play.api.libs.streams.Accumulator
import play.api.mvc._
import play.api.libs.ws._


import scala.concurrent.ExecutionContext

/**
  *
  * @author <a href=mailto:leihuazhe@gmail.com>maple</a>
  * @since 2018-12-06 10:52 AM
  */
class MyController @Inject()(ws: WSClient, val controllerComponents: ControllerComponents)
                            (implicit ec: ExecutionContext) extends BaseController {

  def forward(request: WSRequest): BodyParser[WSResponse] = BodyParser { req =>
    Accumulator.source[ByteString].mapFuture { source =>
      request
        .withBody(source)
        .execute()
        .map(Right.apply)
    }
  }


  def myAction: Action[WSResponse] = Action(forward(ws.url("https://example.com"))) { req =>
    Ok("Uploaded")
  }
}
