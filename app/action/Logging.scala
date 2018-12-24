package action

import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}

/**
  *
  * @author <a href=mailto:leihuazhe@gmail.com>Maple Ray</a>
  * @since 2018-12-18 6:09 PM
  */
case class Logging[A](action: Action[A]) extends Action[A] {

  def apply(request: Request[A]): Future[Result] = {
    println("Wrapper calling action")
    action(request)
  }

  override def parser: BodyParser[A] = action.parser

  override def executionContext: ExecutionContext = action.executionContext
}
