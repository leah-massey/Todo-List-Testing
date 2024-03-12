package adapters

import domain.Domain
import domain.models.TodoItem
import org.http4k.core.Method.GET
import org.http4k.core.Method.POST
import org.http4k.core.Method.PATCH
import org.http4k.core.Method.DELETE
import org.http4k.core.Status.Companion.OK
import org.http4k.routing.bind
import org.http4k.routing.routes
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.http4k.core.*
import org.http4k.core.Status.Companion.CREATED
import org.http4k.routing.path

class HttpApi(domain: Domain) {

    val app: HttpHandler = routes(
        "/todos" bind GET to { request: Request ->
            val todoStatus: String = request.query("status") ?: ""

            if (todoStatus == "") {
                val todoList: List<TodoItem> = domain.getTodoList()
                val toDoListAsJsonString: String = mapper.writeValueAsString(todoList)
                Response(OK)
                    .body(toDoListAsJsonString)
                    .headers(listOf("content-type" to "application/json"))
            } else {
                val todoListFilteredByStatus: List<TodoItem> = domain.getItemsByStatus(todoStatus)
                val todoListFilteredByStatusAsJSONString: String = mapper.writeValueAsString(todoListFilteredByStatus)
                Response(OK)
                    .body(todoListFilteredByStatusAsJSONString)
                    .headers(listOf("content-type" to "application/json"))
            }
        },

        "/todos" bind POST to {request: Request ->
            val newTodoData: String  = request.bodyString()  //todo handle errors
            val newTodoName: String = mapper.readTree(newTodoData).get("name").asText()
            val newTodo = domain.addTodo(newTodoName)
            val newTodoAsJsonString = mapper.writeValueAsString(newTodo)
            Response(CREATED)
                .body(newTodoAsJsonString)
                .headers(listOf("content-type" to "application/json"))
        },

        "/todos/{todoId}" bind PATCH to {request: Request ->
            val todoId: String = request.path("todoId")!! // handle errors
            val todoDataToUpdate: String = request.bodyString()
            val todoNameUpdate: String? = mapper.readTree(todoDataToUpdate).get("name")?.asText()
            val todoStatusUpdate: String? = mapper.readTree(todoDataToUpdate).get("status")?.asText()

            if (todoNameUpdate != null) {
                domain.updateTodoName(todoId, todoNameUpdate)
            }

            if (todoStatusUpdate != null && todoStatusUpdate == "DONE") {
                domain.markTodoAsDone(todoId)
            }

            if (todoStatusUpdate != null && todoStatusUpdate == "NOT_DONE") {
                domain.markTodoAsNotDone(todoId)
            }

            val updatedTodo: List<TodoItem> = domain.getTodoList(todoId)
            val updatedTodoAsJson = mapper.writeValueAsString(updatedTodo)

                Response(OK)
                    .body(updatedTodoAsJson)
                    .headers(listOf("content-type" to "application/json"))
        },

        "/todos/{todoId}" bind GET to {request: Request ->
            val todoId: String = request.path("todoId")!! // handle errors if id is not valid
            val todoItem: List<TodoItem> = domain.getTodoList(todoId)
            val toDoListAsJsonString: String = mapper.writeValueAsString(todoItem)
            Response(OK)
                .body(toDoListAsJsonString)
                .headers(listOf("content-type" to "application/json"))
        },

        "/todos/{todoId}" bind DELETE to {request: Request ->
            val todoId: String = request.path("todoId")!!
            val todoDeletionConfirmation = domain.deleteTodo(todoId)
            Response(OK).body(todoDeletionConfirmation)
        }

    )

    private val mapper: ObjectMapper = jacksonObjectMapper()
}




