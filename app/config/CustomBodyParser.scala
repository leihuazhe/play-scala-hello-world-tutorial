package config

import akka.stream.scaladsl._
import akka.util.ByteString
import play.api.libs.streams.Accumulator
import play.api.mvc.{BodyParser, RequestHeader, Result}

import scala.concurrent.{ExecutionContext, Future}

/**
  *
  * @author <a href=mailto:leihuazhe@gmail.com>maple</a>
  * @since 2018-12-06 10:15 AM
  */
class CustomBodyParser extends BodyParser[Seq[Seq[String]]] {

  override def apply(v1: RequestHeader): Accumulator[ByteString, Either[Result, Seq[Seq[String]]]] = {
    // A flow that splits the stream into CSV lines
    val sink: Sink[ByteString, Future[Seq[Seq[String]]]] = Flow[ByteString]
      // We split by the new line character, allowing a maximum of 1000 characters per line
      .via(Framing.delimiter(ByteString("\n"), 1000, allowTruncation = true))
      // Turn each line to a String and split it by commas
      .map(_.utf8String.trim.split(",").toSeq)
      // Now we fold it into a list
      .toMat(Sink.fold(Seq.empty[Seq[String]])(_ :+ _))(Keep.right)

    // Convert the body to a Right either
    Accumulator(sink).map(Right.apply)(ExecutionContext.global)
  }
}
