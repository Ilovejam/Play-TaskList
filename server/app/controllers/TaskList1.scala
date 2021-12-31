package controllers

import models.TaskListMemoryModel

import javax.inject._
import play.api.mvc._
import play.api.i18n._
import play.api.data._
import play.api.data.Forms._

case class LoginData(username: String, password: String)

@Singleton
class TaskList1 @Inject()(cc: MessagesControllerComponents) extends MessagesAbstractController(cc) {
  val loginForm = Form(mapping(
    "Username" -> text(3,10),
    "Password" -> text(8)
  )(LoginData.apply)(LoginData.unapply))


  def login = Action { implicit request =>
    Ok(views.html.login1(loginForm))
  }

  def validateLoginGet (username:String, password: String) = Action{
    Ok(s"Fuck in you fucking $username and your fucking password is $password")
  }

  def validateLoginPost = Action { implicit request =>
    val postVals = request.body.asFormUrlEncoded
    postVals.map { args =>
      val username = args("username").head
      val password = args("password").head
      if(TaskListMemoryModel.validateUser(username,password)){
        Redirect(routes.TaskList1.taskList()).withSession("username" -> username)
      } else {
        Redirect(routes.TaskList1.login()).flashing("error" -> "Invalid username or password")
      }

    }.getOrElse(Redirect(routes.TaskList1.login()))
  }

  def validateLoginForm = Action { implicit request =>
    loginForm.bindFromRequest.fold(
        formWithErrors => BadRequest(views.html.login1(formWithErrors)),
        ld =>
            if(TaskListMemoryModel.validateUser(ld.username,ld.password)){
                Redirect(routes.TaskList1.taskList()).withSession("username" -> ld.username)
            } else {
                Redirect(routes.TaskList1.login()).flashing("error" -> "Invalid username or password")
            }
    )
  }

  def createUser = Action { implicit request =>
    val postVals = request.body.asFormUrlEncoded
    postVals.map { args =>
      val username = args("username").head
      val password = args("password").head
      if(TaskListMemoryModel.createUser(username,password)){
        Redirect(routes.TaskList1.taskList()).withSession("username" -> username)
      } else {
        Redirect(routes.TaskList1.login()).flashing("error" -> "Invalid username or password")
      }

    }.getOrElse(Redirect(routes.TaskList1.login()))
  }

  def taskList = Action { implicit request =>
    val usernameOption = request.session.get("username")
    usernameOption.map{ username =>
      val tasks = TaskListMemoryModel.getTasks(username)
      Ok(views.html.taskList1(tasks))
    }.getOrElse(Redirect(routes.TaskList1.taskList()))

  }

  def logout = Action {
    Redirect(routes.TaskList1.login()).withNewSession
  }

  def addTask = Action{ implicit request =>
    val usernameOption = request.session.get("username")
    usernameOption.map { username =>
      val postVals = request.body.asFormUrlEncoded
      postVals.map { args =>
        val task = args("newTask").head
        TaskListMemoryModel.addTask(username, task)
        Redirect(routes.TaskList1.taskList())
      }.getOrElse(Redirect(routes.TaskList1.taskList()))
    }.getOrElse(Redirect(routes.TaskList1.login()))
  }

  def deleteTask = Action{ implicit request =>
   val usernameOption = request.session.get("username")
   usernameOption.map { username =>
     val postVals = request.body.asFormUrlEncoded
     postVals.map { args =>
       val index = args("index").head.toInt
       TaskListMemoryModel.removeTask(username, index)
       Redirect(routes.TaskList1.taskList())
     }.getOrElse(Redirect(routes.TaskList1.taskList()))
   }.getOrElse(Redirect(routes.TaskList1.login()))
 }
}
