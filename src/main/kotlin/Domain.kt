import Interfaces.TodoListRepoInterface
import java.time.LocalDateTime
import java.util.*

class Domain(val todoListRepo: TodoListRepoInterface) {

    fun getTodoList(todoId: String): MutableList<TodoItem>  {
        val todoList: MutableList<TodoItem> = todoListRepo.getTodos() // get todoList

        if (todoId == "") {
            return todoList
        } else {
            return todoList.filter {
            it.id == todoId }.toMutableList()
        }
    }

    fun createNewTodo(todoName: String): TodoItem {
        val newTodoName: String = todoName
        val newTodoId: String = UUID.randomUUID().toString()
        val createdDate: String = LocalDateTime.now().toString()
        val newTodoItem: TodoItem = TodoItem(id = newTodoId, createdDate = createdDate, lastModifiedDate = createdDate, name = newTodoName)

        return newTodoItem
    }

    fun addTodoItem(todoName: String): String {
        val newTodoItem: TodoItem = createNewTodo(todoName)
        val todoList: MutableList<TodoItem> = getTodoList("")
        todoList.add(newTodoItem)

        todoListRepo.updateTodos(todoList)
        return "your item has been added"
    }
}

data class TodoItem(
    val id: String,
    val createdDate: String,
    val lastModifiedDate: String,
    var name: String,
    var status: String = "NOT_DONE"
)

//data class TodoList(
//    val items: MutableList<TodoItem> = mutableListOf()
//)

