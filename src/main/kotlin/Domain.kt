import Interfaces.TodoListRepoInterface
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.File

class Domain(val todoListRepo: TodoListRepoInterface) {

    fun getTodoList(): List<TodoItem>  {
        val todoList = todoListRepo.getTodos()
        return todoList
    }

    // addTodo
    // updateTodo
    // deleteTodo
}

data class TodoItem(
    val id: String,
    val createdDate: String,
    val modifiedDate: String,
    var name: String,
    var status: String
)

data class TodoList(
     val items: List<TodoItem>
)
