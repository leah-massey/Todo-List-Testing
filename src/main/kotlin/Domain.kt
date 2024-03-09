import Interfaces.TodoListRepoInterface
import java.time.LocalDateTime
import java.util.*

class Domain(val todoListRepo: TodoListRepoInterface) {

    fun getTodoList(todoId: String = ""): MutableList<TodoItem>  {
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

    fun updateTodoItemName(todoId: String, updatedName: String): String {
        val todoList: MutableList<TodoItem> = getTodoList("")

        for (todoItem in todoList) {
            if (todoItem.id == todoId) {
                todoItem.name = updatedName
            }
        }

        todoListRepo.updateTodos(todoList)
        return "Your todo has been updated"
    }

    fun deleteTodo(todoId: String): String {
        val todoList: MutableList<TodoItem> = getTodoList("")

        var todoItemToRemove: TodoItem? = null

        for (todoItem in todoList) {
            if (todoItem.id == todoId) {
                todoItemToRemove = todoItem
            }
        }

        todoList.remove(todoItemToRemove)

        todoListRepo.updateTodos(todoList) // send updated list back to json file
        return "Your todo has been deleted"
    }

    //update todo status
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

fun main() {
    val repo = TodoListRepoJSON()
    val domain = Domain(repo)

    println(domain.deleteTodo("ea3cff7c-6f57-4304-b403-3af4e637f4ca"))
}

