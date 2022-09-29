
import java.util.Calendar
import play.api.libs.json.{Json, JsValue}
import sttp.client3._
import java.text.SimpleDateFormat
import java.util.Date
import scala.sys.process._

object Main extends App:
  val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
  val backend = HttpClientSyncBackend()
  val response = basicRequest
  .get(uri"https://api.sunrise-sunset.org/json?lat=41.8933203&lng=12.4829321&date=today")
  .send(backend)
  val json: JsValue = Json.parse(response.body.getOrElse(""))
  val sunrise = (json \\ "sunrise").head
  val sunset = (json \\ "sunset").head
  val formatter: SimpleDateFormat = SimpleDateFormat("h:mm:ss a")
  val sunriseTime: Date = formatter.parse(sunrise.as[String])
  val sunsetTime: Date = formatter.parse(sunset.as[String])
  if Seq(sunriseTime.getHours() to sunsetTime.getHours()).contains(currentHour.toDouble) then
    "gsettings set org.gnome.desktop.interface color-scheme prefer-light".!!
  else
    "gsettings set org.gnome.desktop.interface color-scheme prefer-dark".!!
