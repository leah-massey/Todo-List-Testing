
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import Interfaces.TodoListRepo
import java.io.File

class TodoListRepoJSON: TodoListRepo {

    val todoListFile = File("./src/resources/todo_list.json")
    val mapper: ObjectMapper = jacksonObjectMapper()// tool that converts to and from JSON data
    override fun getTodos(): MutableList<TodoItem> {
        val todoList: MutableList<TodoItem> = mapper.readValue(todoListFile) // turn to a list of todoItems
        return todoList
    }
    override fun updateTodos(updatedTodoList:  MutableList<TodoItem>) = mapper.writeValue(todoListFile, updatedTodoList)

}
