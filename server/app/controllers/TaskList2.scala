package controllers

import models.TaskListMemoryModel

import javax.inject._
import play.api.mvc._
import play.api.i18n._
import play.api.data._
import play.api.data.Forms._

@Singleton
class TaskList2 @Inject() (cc: ControllerComponents) extends AbstractController(cc) {
  def load = Action { implicit request =>
    val usernameOption = request.session.get("username")
    usernameOption.map { username =>
      Ok(views.html.taskList2(TaskListMemoryModel.getTasks(username)))
    }.getOrElse(Ok(views.html.taskList2Main()))
  }

  def login = Action {
    Ok(views.html.login2())
  }

  def validate(username: String, password:String) = Action {
    if(TaskListMemoryModel.validateUser(username,password)){
      Ok(views.html.taskList2(TaskListMemoryModel.getTasks(username))).withSession("username" -> username)
    } else {
      Ok(views.html.login2())
    }
  }

  def createUser(username: String, password:String) = Action {
    println("creating a user")
    if(TaskListMemoryModel.createUser(username,password)){
      Ok(views.html.taskList2(TaskListMemoryModel.getTasks(username))).withSession("username" -> username)
    } else {
      Ok(views.html.login2())
    }
  }

  def delete(index: Int) = Action { implicit request =>
    val usernameOption = request.session.get("username")
    usernameOption.map { username =>
      TaskListMemoryModel.removeTask(username, index)
      Ok(views.html.taskList2(TaskListMemoryModel.getTasks(username)))
    }.getOrElse(Ok(views.html.login2()))
  }

  def addTask(task: String) = Action { implicit request =>
    val usernameOption = request.session.get("username")
    usernameOption.map { username =>
        TaskListMemoryModel.addTask(username, task)
        Ok(views.html.taskList2(TaskListMemoryModel.getTasks(username)))
      }.getOrElse(Ok(views.html.login2()))

  }

  def logout = Action {
    Redirect(routes.TaskList2.load()).withNewSession
  }

  def generatedJS = Action {
    Ok(views.js.generatedJS())
  }
}