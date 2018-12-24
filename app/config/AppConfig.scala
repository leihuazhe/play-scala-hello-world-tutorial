package config

import java.net.URI

import com.typesafe.config.Config
import play.api.ConfigLoader

/**
  *
  * @author <a href=mailto:leihuazhe@gmail.com>maple</a>
  * @since 2018-12-04 1:50 PM
  */
case class AppConfig(title: String, baseUri: URI)


object AppConfig {

  implicit val configLoader: ConfigLoader[AppConfig] = new ConfigLoader[AppConfig] {

    def load(rootConfig: Config, path: String): AppConfig = {
      val config = rootConfig.getConfig(path)
      AppConfig(
        title = config.getString("title"),
        baseUri = new URI(config.getString("baseUri"))
      )
    }
  }


}
