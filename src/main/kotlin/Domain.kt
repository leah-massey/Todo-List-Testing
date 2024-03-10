import Interfaces.TodoListRepoInterface
import java.time.LocalDateTime
import java.util.*

class Domain(val todoListRepo: TodoListRepoInterface) {
    fun getTodoList(todoId: String = ""): MutableList<TodoItem>  {
        val todoList: MutableList<TodoItem> = todoListRepo.getTodos()
        if (todoId == "") {
            return todoList
        } else {
            return todoList.filter {
            it.id == todoId }.toMutableList()
        }
    }

    val todoList: MutableList<TodoItem> = getTodoList("")

    private fun createNewTodo(todoName: String): TodoItem {
        val newTodoName: String = todoName
        val newTodoId: String = UUID.randomUUID().toString()
        val createdDate: String = LocalDateTime.now().toString()
        val newTodoItem = TodoItem(id = newTodoId, createdDate = createdDate, lastModifiedDate = createdDate, name = newTodoName)

        return newTodoItem
    }

    fun addTodo(todoName: String): String {
        val newTodoItem: TodoItem = createNewTodo(todoName)
        todoList.add(newTodoItem)
        todoListRepo.updateTodos(todoList)
        return "'${todoName}' has been added as a todo."
    }

    fun updateTodoName(todoId: String, updatedTodoName: String): String {
        for (todoItem in todoList) {
            if (todoItem.id == todoId) {
                todoItem.name = updatedTodoName
                todoItem.lastModifiedDate = LocalDateTime.now().toString()
            }
        }
        todoListRepo.updateTodos(todoList)
        return "Your todo has been updated to '${updatedTodoName}'."
    }

    fun updateTodoStatus(todoId: String, updatedTodoStatus: String): String {
        var nameOfUpdatedTodo: String? = null
        for (todoItem in todoList) {
            if (todoItem.id == todoId) {
                todoItem.status = updatedTodoStatus
                todoItem.lastModifiedDate = LocalDateTime.now().toString()
                nameOfUpdatedTodo = todoItem.name
            }
        }
        todoListRepo.updateTodos(todoList)
        return "The status of your todo '${nameOfUpdatedTodo}' has been updated to '${updatedTodoStatus}'."
    }

    fun deleteTodo(todoId: String): String {
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

    println(domain.updateTodoStatus("8bf2c64b-ddd9-42a9-a604-716ada155fc9", "DONE"))
}

