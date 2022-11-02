//> using scala "3.2.0"
//> using lib "com.softwaremill.sttp.client3::core:3.8.3"
//> using lib "com.typesafe.play::play-json:2.10.0-RC7"

import java.util.Calendar
import play.api.libs.json.{Json, JsValue}
import sttp.client3._
import java.text.SimpleDateFormat
import java.util.Date
import scala.sys.process._

object Main extends App:
  val calendar = Calendar.getInstance()
  val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
  val backend = HttpClientSyncBackend()
  val response = basicRequest
  .get(uri"https://api.sunrise-sunset.org/json?lat=41.8933203&lng=12.4829321&date=today")
  .send(backend)
  val json: JsValue = Json.parse(response.body.getOrElse(""))
  val sunrise = (json \\ "sunrise").head
  val sunset = (json \\ "sunset").head
  val formatter: SimpleDateFormat = SimpleDateFormat("h:mm:ss a")
  calendar.setTime(formatter.parse(sunrise.as[String]))
  val sunriseTime: Int = calendar.get(Calendar.HOUR_OF_DAY)
  calendar.setTime(formatter.parse(sunset.as[String]))
  val sunsetTime: Int = calendar.get(Calendar.HOUR_OF_DAY)
  if (sunriseTime to sunsetTime).contains(currentHour.toDouble) then
    "gsettings set org.gnome.desktop.interface color-scheme prefer-light".!!
  else
    "gsettings set org.gnome.desktop.interface color-scheme prefer-dark".!!
