package controllers

import javax.inject._
import play.api.Configuration
import play.api.libs.json.JsValue
import play.api.mvc._
//import config.AppConfig.configLoader
/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */


//A controller in Play is nothing more than an object that generates Action values
//Play中的控制器只不过是一个生成Action值的对象
@Singleton
class RouteController @Inject()(config: Configuration, cc: ControllerComponents) extends AbstractController(cc) {


  def show(id: Long) = Action {
//    Redirect(routes.HelloController.echo())
    Ok(s"your request id $id")

  }

  def json(id: Long) = Action { request: Request[AnyContent] ⇒
    val body: Option[JsValue] = request.body.asJson


    body.map { json ⇒
      Ok("Got Json " + (json \ "name").as[String])
    }.getOrElse {
      BadRequest("Expecting application/json request body")
    }
  }


}

