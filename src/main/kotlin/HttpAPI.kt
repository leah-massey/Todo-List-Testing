import Interfaces.TodoListRepoInterface
import org.http4k.core.Method.GET
import org.http4k.core.Status.Companion.OK
import org.http4k.filter.DebuggingFilters.PrintRequest
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.SunHttp
import org.http4k.server.asServer
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.http4k.core.*


val mapper: ObjectMapper = jacksonObjectMapper() // tool to allow us to convert to and from JSON data

val app: HttpHandler = routes(
    "/todos" bind GET to {request: Request ->

        val todoListRepo: TodoListRepoInterface = TodoListRepoJSON()
        val domain = Domain(todoListRepo)
        val todoList: List<TodoItem> = domain.getTodoList()
        val toDoListAsJsonString: String = mapper.writeValueAsString(todoList) // turn back to a json string
        Response(OK).body(toDoListAsJsonString)
    },
)

fun main() {
    val printingApp: HttpHandler = PrintRequest().then(app)

    val server = printingApp.asServer(SunHttp(9000)).start()

    println("Server started on " + server.port())


}



//
//        val taskId: String = request.query("id")

// if no query parameter, return entire list
//        if (taskId == null) {
//            Response(OK).body(toDoListAsJsonString)
// if taskId declared, return task matching the id
//        } else {
//            val singleTask: List<TodoItem> = toDoList.filter {
//                it.id == taskId }
//                    Response(OK).body(mapper.writeValueAsString(singleTask))
//                }
// add an item to task list
//    "/todo" bind POST to {request: Request ->
//        val jsonStringNewTodo = request.bodyString()
//        val newTodo: TodoItem = mapper.readValue(jsonStringNewTodo, TodoItem::class.java) // telling the object mapper to instantiate the ToDoItem class when it parses the jsonString
//        todoList.add(newTodo)
//        val updatedListAsJson = mapper.writeValueAsString(todoList)
//        Response(OK).body("your todo has been added" )
//    }



//    "/formats/json/jackson" bind GET to {
//        Response(OK).with(jacksonMessageLens of JacksonMessage("Barry", "Hello there!"))
//    },
//
//    "/testing/hamkrest" bind GET to {request ->
//        Response(OK).body("Echo '${request.bodyString()}'")
//    }