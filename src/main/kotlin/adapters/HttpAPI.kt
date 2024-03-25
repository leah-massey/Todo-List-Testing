package adapters

import org.http4k.core.Method.GET
import org.http4k.core.Method.POST
import org.http4k.core.Method.PATCH
import org.http4k.core.Status.Companion.OK
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import domain.ReadDomain
import domain.WriteDomain
import domain.models.Todo
import domain.models.TodoClientView
import domain.models.TodoNameUpdate
import domain.models.TodoStatusUpdate
import org.http4k.core.*
import org.http4k.core.Status.Companion.BAD_REQUEST
import org.http4k.core.Status.Companion.CREATED
import org.http4k.routing.*

class HttpApi(readDomain: ReadDomain, writeDomain: WriteDomain) {

    val app: HttpHandler = routes(
        "/todos" bind GET to { request: Request ->
            val todoStatus: String = request.query("status") ?: ""

            if (todoStatus == "") {
                val todoList: List<TodoClientView> = readDomain.getTodoListClientView()

                val toDoListAsJsonString: String = mapper.writeValueAsString(todoList)
                Response(OK)
                    .body(toDoListAsJsonString)
                    .header("content-type", "application/json")
            } else {
                val todoListFilteredByStatus: List<TodoClientView> = readDomain.getTodoListByStatusClientView(todoStatus)
                val todoListFilteredByStatusAsJSONString: String = mapper.writeValueAsString(todoListFilteredByStatus)
                Response(OK)
                    .body(todoListFilteredByStatusAsJSONString)
                    .header("content-type", "application/json")
            }
        },

        "/todos" bind POST to { request: Request ->
            val newTodoData: String = request.bodyString()  //todo handle errors
            val newTodoName: String = mapper.readTree(newTodoData).get("name").asText()
            val newTodo = writeDomain.createNewTodo(newTodoName)
            val newTodoId = newTodo.id
            val newTodoURL = "http://localhost:3000/todos/${newTodoId}"
            val newTodoAsJsonString = mapper.writeValueAsString(newTodo)

            Response(CREATED)
                .body(newTodoAsJsonString)
                .header("content-type", "application/json")
                .header("Location", newTodoURL)
        },

        "/todos/{todoId}" bind PATCH to bind@{ request: Request ->
            val todoId: String = request.path("todoId")!! // handle errors
            val todoDataToUpdate: String = request.bodyString()
            val todoNameUpdate: String? = mapper.readTree(todoDataToUpdate).get("name")?.asText()
            val todoStatusUpdate: String? = mapper.readTree(todoDataToUpdate).get("status")?.asText()


            if (todoNameUpdate != null) {
                writeDomain.updateTodoName(todoId, todoNameUpdate)
                val updatedTodo: TodoNameUpdate = readDomain.getTodoAfterNameUpdate(todoId)
                val updatedTodoAsJson = mapper.writeValueAsString(updatedTodo)
                return@bind Response(OK)
                    .body(updatedTodoAsJson)
                    .header("Content-Type", "application/json")
            }

            if (todoStatusUpdate == "DONE") {
                val updatedTodoStatusDone: TodoStatusUpdate = readDomain.getTodoAfterStatusUpdate(todoId)
                val updatedTodoAsJson = mapper.writeValueAsString(updatedTodoStatusDone)
                return@bind Response(OK)
                    .body(updatedTodoAsJson)
                    .header("Content-Type", "application/json")
            }

            if (todoStatusUpdate == "NOT_DONE") {
                val updatedTodoStatusNotDone: TodoStatusUpdate = readDomain.getTodoAfterStatusUpdate(todoId)
                val updatedTodoAsJson = mapper.writeValueAsString(updatedTodoStatusNotDone)
                return@bind Response(OK)
                    .body(updatedTodoAsJson)
                    .header("Content-Type", "application/json")
            }
            Response(BAD_REQUEST)
        },

        "/todos/{todoId}" bind GET to { request: Request ->
            val todoId: String = request.path("todoId")!! // handle errors if id is not valid
            val todoItem: List<TodoClientView> = readDomain.getTodoListClientView(todoId)
            val toDoListAsJsonString: String = mapper.writeValueAsString(todoItem)
            Response(OK)
                .body(toDoListAsJsonString)
                .header("content-type", "application/json")
        },
    )

    private val mapper: ObjectMapper = jacksonObjectMapper()
}




