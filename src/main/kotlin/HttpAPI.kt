import Interfaces.TodoListRepoInterface
import com.fasterxml.jackson.databind.JsonNode
import org.http4k.core.Method.GET
import org.http4k.core.Method.POST
import org.http4k.core.Method.PUT
import org.http4k.core.Method.DELETE
import org.http4k.core.Status.Companion.OK
import org.http4k.filter.DebuggingFilters.PrintRequest
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.SunHttp
import org.http4k.server.asServer
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.http4k.core.*
import org.http4k.format.write
import org.http4k.routing.path
import org.w3c.dom.Node

class HttpApi(domain: Domain) {
    val mapper: ObjectMapper = jacksonObjectMapper() // tool to allow us to convert to and from JSON data

    val app: HttpHandler = routes(
        // get todos
        "/todos" bind GET to {request: Request ->
            val todoList: MutableList<TodoItem> = domain.getTodoList()
            val toDoListAsJsonString: String = mapper.writeValueAsString(todoList) // turn back to a json string
            Response(OK).body(toDoListAsJsonString)
        },

        // add a new todo
        "/todos" bind POST to {request: Request ->
            val newTodoData: String  = request.bodyString() // returns json tododata in string format //todo handle errors
            val newTodoName: String = mapper.readTree(newTodoData).get("name").asText() // convert to json string to json node, then to text and extract name
            val confirmationOfTodoAdded = domain.addTodoItem(newTodoName)
            Response(OK).body(confirmationOfTodoAdded)
        },

        // update a todo by name or status
        "/todos/{todoId}" bind PUT to {request: Request ->
            val todoId: String = request.path("todoId")!! // handle errors

            val todoDataToUpdate: String = request.bodyString()

            val todoNameUpdate: String? = mapper.readTree(todoDataToUpdate).get("name")?.asText()
            val todoStatusUpdate: String? = mapper.readTree(todoDataToUpdate).get("status")?.asText()

            var updateConfirmation: String = ""

            if (todoNameUpdate != null) {
                updateConfirmation += " ${domain.updateTodoItemName(todoId, todoNameUpdate)}"
            }

            if (todoStatusUpdate != null) {
                updateConfirmation += " ${domain.updateTodoItemStatus(todoId, todoStatusUpdate)}"
            }

//            val confirmationOfTodoNameUpdate = domain.updateTodoItemName(todoId, updatedTodoName)
            Response(OK).body(updateConfirmation)
        },

        // get todo by id
        "/todos/{todoId}" bind GET to {request: Request ->
            val todoId: String = request.path("todoId")!! // handle errors if id is incorrect
            val todoList: MutableList<TodoItem> = domain.getTodoList(todoId)
            val toDoListAsJsonString: String = mapper.writeValueAsString(todoList) // turn back to a json string
            Response(OK).body(toDoListAsJsonString)
        },

        // delete todo by id
        "/todos/{todoId}" bind DELETE to {request: Request ->
            val todoId: String = request.path("todoId")!!
//            val todoList: MutableList<TodoItem> = domain.deleteTodo(todoId)
            val todoDeletionConfirmation = domain.deleteTodo(todoId)
            Response(OK).body(todoDeletionConfirmation)
        }

    )
}

fun main() {
    val todoListRepo: TodoListRepoInterface = TodoListRepoJSON()
    val domain = Domain(todoListRepo)

    val printingApp: HttpHandler = PrintRequest().then(HttpApi(domain).app)

    val server = printingApp.asServer(SunHttp(3000)).start()

    println("Server started on " + server.port())


}


