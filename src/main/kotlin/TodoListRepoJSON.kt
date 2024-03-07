import Interfaces.TodoListRepoInterface
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.File

class TodoListRepoJSON: TodoListRepoInterface {

    override fun getTodos(): List<TodoItem> {
        val mapper: ObjectMapper = jacksonObjectMapper() // tool that converts to and from JSON data
        val todoListFile = File("./src/resources/todo_list.json")
        val todoList: List<TodoItem> = mapper.readValue<List<TodoItem>>(todoListFile) // turn to a list of todoItems
        return todoList
    }
}
