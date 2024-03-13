package domain

import domain.models.Todo
import ports.TodoListRepo
import java.time.LocalDateTime
import java.util.*

class WriteDomain(val todoListRepo: TodoListRepo, val readDomain: ReadDomain) {

    fun addTodo(todoName: String): Todo {
        val todoList: MutableList<Todo> = readDomain.getTodoList("").toMutableList()
        val newTodoItem = Todo(id = createID(), createdDate = timeStamp(), lastModifiedDate = timeStamp(), name = todoName)
        todoList.add(newTodoItem)
        todoListRepo.updateTodoList(todoList)
        return newTodoItem
    }

    fun updateTodoName(todoId: String, updatedTodoName: String): Todo? {
        val todoList: MutableList<Todo> = readDomain.getTodoList("").toMutableList()
        var updatedTodo: Todo? = null
        for (todoItem in todoList) {
            if (todoItem.id == todoId) {
                todoItem.name = updatedTodoName
                todoItem.lastModifiedDate = LocalDateTime.now().toString()
                updatedTodo = todoItem
            }
        }
        todoListRepo.updateTodoList(todoList)
        return updatedTodo
    }

    fun markTodoAsDone(todoId: String): String {
        val todoList: MutableList<Todo> = readDomain.getTodoList("").toMutableList()
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
        val todoList: MutableList<Todo> = readDomain.getTodoList("").toMutableList()
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

    private fun createID(): String {
        return UUID.randomUUID().toString()
    }

    private fun timeStamp(): String {
        return LocalDateTime.now().toString()
    }

}