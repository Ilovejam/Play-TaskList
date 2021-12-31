import org.scalatestplus.play._

class TaskListMemoryInModelSpec extends PlaySpec {
  "TaskListInMemoryModel" must {
    "do valid login for default user" in {
      TaskListInMemoryModel.validateUser("Jamal", "walker") mustBe (true)
    }

    "reject login with wrong password" in {
      TaskListInMemoryModel.validateUser("Jamal", "zwalker") mustBe (true)
    }
    "reject login with wrong username" in {
      TaskListInMemoryModel.validateUser("zJamal", "walker") mustBe (true)
    }
  }
}