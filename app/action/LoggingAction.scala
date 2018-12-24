package action

import javax.inject.Inject
import play.api.mvc._


import scala.concurrent.{ExecutionContext, Future}

/**
  *
  * @author <a href=mailto:leihuazhe@gmail.com>Maple Ray</a>
  * @since 2018-12-18 6:02 PM
  */
class LoggingAction @Inject()(parser: BodyParsers.Default)(implicit ec: ExecutionContext) extends ActionBuilderImpl(parser) {

  override def invokeBlock[A](request: Request[A], block: (Request[A]) => Future[Result]) = {
    println("Calling action")
    block(request)
  }

  override def composeAction[A](action: Action[A]) = Logging(action)

}
