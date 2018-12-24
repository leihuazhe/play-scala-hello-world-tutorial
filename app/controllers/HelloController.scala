package controllers

import java.io.File

import akka.util.ByteString
import config.AppConfig
import javax.inject._
import play.api.Configuration
import play.api.http.{ContentTypes, HttpEntity}
import play.api.libs.json.JsValue
import play.api.mvc._
//import config.AppConfig.configLoader

//A controller in Play is nothing more than an object that generates Action values
//Play中的控制器只不过是一个生成Action值的对象
@Singleton
class HelloController @Inject()(config: Configuration, cc: ControllerComponents) extends AbstractController(cc) {

  /**
    * Create an Action to render an HTML page.
    *
    * The configuration in the `routes` file means that this method
    * will be called when the application receives a `GET` request with
    * a path of `/`.
    */

  //ConfigLoader implicit
  def getFoo() = Action { implicit request: Request[AnyContent] =>
    //    Ok(views.html.index())
    //        Ok(config.get[String]("hello.foo"))
    val get = config.get[Seq[String]]("hello.listOfFoos")
    println(get)

    val res: String = config.getAndValidate[String]("hello.foo", Set("hello,maple", "baz"))
    println(res)

    val appConfig = config.get[AppConfig]("app.config")

    println(appConfig)

    Ok(config.get[String]("hello.foo"))
  }

  def echo = Action { request =>
    Ok("Got request [" + request + "]")
  }

  //Body parsers will be covered later in this manual.
  // For now you just need to know that the other methods of creating Action values use a default Any content body parser.
  def echo2: Action[JsValue] = Action(parse.json) { implicit request =>
    doEcho()
    Ok("Got request [" + request + "]")
  }


  def index = Action {

    val ok = Ok("Hello world!")
    val notFound = NotFound
    val pageNotFound = NotFound(<h1>Page not found</h1>)
    //    val badRequest = BadRequest(views.html.form(formWithErrors))

    val oops = InternalServerError("Oops")

    val anyStatus = Status(488)("Strange response type")

    Result(
      header = ResponseHeader(200, Map.empty),
      body = HttpEntity.Strict(ByteString("Hello world!"), Some("text/plain"))
    )
  }

  def index2 = Action {
    Redirect("/user/home", MOVED_PERMANENTLY)

    /**
      * default {@link ContentTypes#SEE_OTHER}
      */
    Redirect("/echo")
  }

  def todo() = TODO


  private def doEcho()(implicit request: Request[_]): Unit = {
    println(s"implicit request: $request")
  }

  /**
    * 组合 body parsers
    */
  def save: Action[File] = Action(storeInUserFile) { request =>
    Ok("Saved the request content to " + request.body)
  }

  val storeInUserFile: BodyParser[File] = parse.using { request =>
    request.session.get("username").map { user =>
      parse.file(to = new File("/tmp/" + user + ".upload"))
    }.getOrElse {
      sys.error("You don't have the right to upload here")
    }
  }


}

