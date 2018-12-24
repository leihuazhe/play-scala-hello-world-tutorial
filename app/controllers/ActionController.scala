package controllers


import action.{Logging, LoggingAction}
import javax.inject._
import play.api.Configuration
import play.api.mvc._
//import config.AppConfig.configLoader

//A controller in Play is nothing more than an object that generates Action values
//Play中的控制器只不过是一个生成Action值的对象
@Singleton
class ActionController @Inject()(loggingAction: LoggingAction,
                                 config: Configuration,
                                 cc: ControllerComponents) extends AbstractController(cc) {

  def index = loggingAction {
    Ok("Hello World")
  }

  def submit: Action[String] = loggingAction(parse.text) { request =>
    Ok("Got a body " + request.body.length + " bytes long")
  }

  def logging[A](action: Action[A]): Action[A] = Action.async(action.parser) { request =>
    println("logging Calling action")
    action(request)
  }


  def index2 = Logging {
    Action {
      Ok("Hello World")
    }
  }

}

