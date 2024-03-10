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
                todoItem.lastModifiedDate = LocalDateTime.now().toString()
            }
        }

        todoListRepo.updateTodos(todoList)
        return "Your todo has been updated"
    }

    fun deleteTodo(todoId: String): String {
        val todoList: MutableList<TodoItem> = getTodoList("")

        var nameOfRemovedTodo: String? = null

        for (todoItem in todoList) {
            if (todoItem.id == todoId) {
                todoList.remove(todoItem)
                nameOfRemovedTodo = todoItem.name
            }
        }
        todoListRepo.updateTodos(todoList) // send updated list back to json file

        return "Your todo '${nameOfRemovedTodo}' has been deleted."
    }

    //update todo status
    fun updateTodoItemStatus(todoId: String, newTodoStatus: String): String {
        val todoList: MutableList<TodoItem> = getTodoList("")

        var nameOfUpdatedTodo: String? = null

        for (todoItem in todoList) {
            if (todoItem.id == todoId) {
                todoItem.status = newTodoStatus
                todoItem.lastModifiedDate = LocalDateTime.now().toString()
                nameOfUpdatedTodo = todoItem.name
            }
        }
        todoListRepo.updateTodos(todoList)
        return "The status of todo '${nameOfUpdatedTodo}' has been updated to '${newTodoStatus}'."
    }
}

data class TodoItem(
    val id: String,
    val createdDate: String,
    var lastModifiedDate: String,
    var name: String,
    var status: String = "NOT_DONE"
)

//data class TodoList(
//    val items: MutableList<TodoItem> = mutableListOf()
//)

fun main() {
    val repo = TodoListRepoJSON()
    val domain = Domain(repo)

    println(domain.updateTodoItemStatus("8bf2c64b-ddd9-42a9-a604-716ada155fc9", "DONE"))
}

