package models

import collection.mutable

object TaskListMemoryModel {
  private val users = mutable.Map[String, String]("Jamal" -> "walker")
  private val tasks = mutable.Map[String, List[String]]("Jamal" -> List("eat","sleep","code"))
  def validateUser(username:String, password:String): Boolean = {
    users.get(username).map(_ == password).getOrElse(false)
  }

  def createUser(username:String, password:String): Boolean = {
    if (users.contains(username)) false else{
      users(username) = password //we take user here if not in users list
      true
    }
  }

  def getTasks(username:String): Seq[String] = {
    tasks.get(username).getOrElse(Nil)
  }

    def addTask(username:String, task: String): Unit = {
      tasks(username) = task :: tasks.get(username).getOrElse(Nil)
    }

  def removeTask(username:String, index: Int): Boolean = {
    if(index < 0 || tasks.get(username).isEmpty || index >= tasks(username).length) false
    else {
      tasks(username) = tasks(username).patch(index, Nil, 1)
      true
    }
  }

}
