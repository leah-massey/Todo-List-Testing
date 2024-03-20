package domain

import adapters.HttpApi
import adapters.TodoListEventFileRepo
import adapters.TodoListFileRepo
import domain.models.Todo
import domain.models.TodoCreatedEvent
import org.http4k.core.then
import org.http4k.filter.DebuggingFilters
import org.http4k.server.SunHttp
import org.http4k.server.asServer
import ports.TodoListEventRepo
import ports.TodoListRepo
import java.time.LocalDateTime
import java.util.*

class WriteDomain(val todoListRepo: TodoListRepo, val todoListEventRepo: TodoListEventRepo, val readDomain: ReadDomain) {

    fun addTodo(todoName: String): Todo {
        val todoList: MutableList<Todo> = readDomain.getTodoList("").toMutableList()
        val newTodoItem = Todo(id = createID(), createdDate = timeStamp(), lastModifiedDate = timeStamp(), name = todoName)
        todoList.add(newTodoItem)
        todoListRepo.updateTodoList(todoList)
        return newTodoItem
    }

    fun createTodo(todoName: String): Todo {
        val newTodo = Todo(id = createID(), createdDate = timeStamp(), lastModifiedDate = timeStamp(), name = todoName)

        val newTodoEvent = TodoCreatedEvent(
        eventId = createID(),
        eventCreatedDate = timeStamp(),
        entityId = newTodo.id,
        eventDetails = newTodo
        )

        todoListEventRepo.addEvent(newTodoEvent)

        return newTodo

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

fun main() {
    val todoListRepo: TodoListRepo = TodoListFileRepo("./src/resources/todo_list.json")
    val todoListEventRepo: TodoListEventRepo = TodoListEventFileRepo("./src/resources/todo_list_event_log.ndjson")
    val readDomain = ReadDomain(todoListRepo, todoListEventRepo)
    val writeDomain = WriteDomain(todoListRepo, todoListEventRepo, readDomain)


    println(writeDomain.createTodo("cat"))
}