# scala-project

source: https://www.baeldung.com/scala/play-rest-api

## Installation
jenv: https://www.jenv.be/
sbt: https://www.scala-sbt.org/download.html

Note: play only support for java 1.6, 1.7, 1.8, 1.9

## Setup Project
Install Project
```sh
sbt new playframework/play-scala-seed.g8
```

Setup Project
```
This template generates a Play Scala project 

name [play-scala-seed]:
organization [com.example]: 
play_version [2.8.15]: 
scala_version [2.13.8]: 
```

Remove unused file:
- HomeController.scala
- index.scala.html 
- main.scala.html 
- Also remove the existing content of the `routes` file

## Code

Models `/app/models/TodoListItem.scala` :
```scala
package models

case class TodoListItem(id: Long, description: String, isItDone: Boolean)
```

Controller `/app/controller/TodoListController.scala`:
```scala
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
```

Routes `/conf/routes`:
```
GET     /todo                       controllers.TodoListController.getAll
```

## Run Project
```sh
sbt run
```

try to hit:
```
curl localhost:9000/todo

[
  {
    "id": 1,
    "description": "test",
    "isItDone": true
  },
  {
    "id": 2,
    "description": "some other value",
    "isItDone": false
  }
]
```

## Deployment

add new files called `system.properties` to set java version:
```
java.runtime.version=1.8
```