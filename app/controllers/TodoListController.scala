package controllers

import javax.inject._
import models.{TodoListItem}
import play.api._
import play.api.mvc._
import play.api.libs.json._
import scala.collection.mutable

@Singleton
class TodoListController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {
    private val todoList = new mutable.ListBuffer[TodoListItem]()
    todoList += TodoListItem(1, "test", true)
    todoList += TodoListItem(2, "some other value", false)
    
    // we create the JSON formatter inside the TodoListController class
    // We make it an implicit field to avoid having to pass it to the Json.toJson function all the time.
    implicit val todoListJson = Json.format[TodoListItem]

    def getAll(): Action[AnyContent] = Action {
        if (todoList.isEmpty) NoContent else Ok(Json.toJson(todoList))
    }
}