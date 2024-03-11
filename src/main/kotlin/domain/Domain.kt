package domain

import adapters.TodoListFileRepo
import domain.models.TodoItem
import java.time.LocalDateTime
import java.util.*
import ports.TodoListRepo

class Domain(val todoListRepo: TodoListRepo) {
    fun getTodoList(todoId: String = ""): List<TodoItem>  {
        val todoList: List<TodoItem> = todoListRepo.getTodos()
        if (todoId == "") {
            return todoList
        } else {
            return todoList.filter {
            it.id == todoId }
        }
    }

    fun addTodo(todoName: String): String {
        val todoList: MutableList<TodoItem> = getTodoList("").toMutableList()
        val newTodoItem = TodoItem(id = createID(), createdDate = timeStamp(), lastModifiedDate = timeStamp(), name = todoName)
        todoList.add(newTodoItem)
        todoListRepo.updateTodoList(todoList)
        return "'${todoName}' has been added as a todo."
    }

    fun updateTodoName(todoId: String, updatedTodoName: String): String {
        val todoList: MutableList<TodoItem> = getTodoList("").toMutableList()
        for (todoItem in todoList) {
            if (todoItem.id == todoId) {
                todoItem.name = updatedTodoName
                todoItem.lastModifiedDate = LocalDateTime.now().toString()
            }
        }
        todoListRepo.updateTodoList(todoList)
        return "Your todo has been updated to '${updatedTodoName}'."
    }

    fun markTodoAsDone(todoId: String): String {
        val todoList: MutableList<TodoItem> = getTodoList("").toMutableList()
        var nameOfUpdatedTodo: String? = null
        for (todoItem in todoList) {
            if (todoItem.id == todoId) {
                todoItem.status = "DONE"
                todoItem.lastModifiedDate = LocalDateTime.now().toString()
                nameOfUpdatedTodo = todoItem.name
            }
        }
        todoListRepo.updateTodoList(todoList)
        return "The status of your todo '${nameOfUpdatedTodo}' has been updated to 'DONE'."
    }

    fun markTodoAsNotDone(todoId: String): String {
        val todoList: MutableList<TodoItem> = getTodoList("").toMutableList()
        var nameOfUpdatedTodo: String? = null
        for (todoItem in todoList) {
            if (todoItem.id == todoId) {
                todoItem.status = "NOT_DONE"
                todoItem.lastModifiedDate = LocalDateTime.now().toString()
                nameOfUpdatedTodo = todoItem.name
            }
        }
        todoListRepo.updateTodoList(todoList)
        return "The status of your todo '${nameOfUpdatedTodo}' has been updated to 'NOT_DONE'."
    }

    fun getItemsByStatus(status: String): List<TodoItem> {
        val todoList: List<TodoItem> = getTodoList("")
        return todoList.filter { todo ->
            todo.status == status
        }
    }

    fun deleteTodo(todoId: String): String {
        val todoList: MutableList<TodoItem> = getTodoList("").toMutableList()
        var nameOfRemovedTodo: String? = null
        var todoToRemove: TodoItem? = null

        for (todoItem in todoList) {
            if (todoItem.id == todoId) {
                nameOfRemovedTodo = todoItem.name
                todoToRemove = todoItem
            }
        }
        todoList.remove(todoToRemove)
        todoListRepo.updateTodoList(todoList)

        return "Your todo '${nameOfRemovedTodo}' has been deleted."
    }

    private fun createID(): String {
        return UUID.randomUUID().toString()
    }

    private fun timeStamp(): String {
        return LocalDateTime.now().toString()
    }
}




