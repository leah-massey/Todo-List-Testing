package adapters

import domain.Domain
import domain.models.TodoItem
import org.http4k.core.Method.GET
import org.http4k.core.Method.POST
import org.http4k.core.Method.PUT
import org.http4k.core.Method.DELETE
import org.http4k.core.Status.Companion.OK
import org.http4k.routing.bind
import org.http4k.routing.routes
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.http4k.core.*
import org.http4k.routing.path

class HttpApi(domain: Domain) {
    val mapper: ObjectMapper = jacksonObjectMapper()


    val app: HttpHandler = routes(
        "/todos" bind GET to {request: Request ->
            val todoList: MutableList<TodoItem> = domain.getTodoList()
            val toDoListAsJsonString: String = mapper.writeValueAsString(todoList) // turn back to a json string
            Response(OK).body(toDoListAsJsonString)
        },

        "/todos" bind POST to {request: Request ->
            val newTodoData: String  = request.bodyString() // returns json tododata in string format //todo handle errors
            val newTodoName: String = mapper.readTree(newTodoData).get("name").asText() // convert to json string to json node, then to text and extract name
            val confirmationOfTodoAdded = domain.addTodo(newTodoName)
            Response(OK).body(confirmationOfTodoAdded)
        },

        "/todos/{todoId}" bind PUT to {request: Request ->
            val todoId: String = request.path("todoId")!! // handle errors
            val todoDataToUpdate: String = request.bodyString()
            val todoNameUpdate: String? = mapper.readTree(todoDataToUpdate).get("name")?.asText()
            val todoStatusUpdate: String? = mapper.readTree(todoDataToUpdate).get("status")?.asText()

            var updateConfirmation: String = ""

            if (todoNameUpdate != null) {
                updateConfirmation += " ${domain.updateTodoName(todoId, todoNameUpdate)}"
            }

            if (todoStatusUpdate != null) {
                updateConfirmation += " ${domain.updateTodoStatus(todoId, todoStatusUpdate)}"
            }

            Response(OK).body(updateConfirmation)
        },

        "/todos/{todoId}" bind GET to {request: Request ->
            val todoId: String = request.path("todoId")!! // handle errors if id is incorrect
            val todoList: MutableList<TodoItem> = domain.getTodoList(todoId)
            val toDoListAsJsonString: String = mapper.writeValueAsString(todoList) // turn back to a json string
            Response(OK).body(toDoListAsJsonString)
        },

        "/todos/{todoId}" bind DELETE to {request: Request ->
            val todoId: String = request.path("todoId")!!
            val todoDeletionConfirmation = domain.deleteTodo(todoId)
            Response(OK).body(todoDeletionConfirmation)
        }

    )
}




