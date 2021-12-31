package controllers

import javax.inject._

import shared.SharedMessages
import play.api.mvc._
import play.api.i18n._
import models.TaskListMemoryModel
import play.api.libs.json.Json

@Singleton
class Application @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def index = Action { implicit request =>
    Ok(views.html.index(SharedMessages.itWorks))
  }

  def product(prodType: String, prodNum: Int) = Action {
    Ok(s"Product type is: $prodType and product number is $prodNum")
  }

  def randomNumber = Action {
    Ok(util.Random.nextInt(100).toString)
  }

  def randomString(length:Int) = Action {
    Ok(util.Random.nextString(length))
  }
}
