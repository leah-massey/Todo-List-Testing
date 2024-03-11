package adapters
import domain.models.TodoItem
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import ports.TodoListRepo
import java.io.File

class TodoListFileRepo(filePath: String): TodoListRepo {
    val todoListFile = File(filePath)
    val mapper: ObjectMapper = jacksonObjectMapper()// tool that converts to and from JSON data
    override fun getTodos(): List<TodoItem> {
        val todoList: List<TodoItem> = mapper.readValue(todoListFile) // turn to a list of todoItems
        return todoList
    }
    override fun updateTodoList(updatedTodoList:  List<TodoItem>) = mapper.writeValue(todoListFile, updatedTodoList)

}
