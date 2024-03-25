package domain

import adapters.TodoListEventFileRepo
import adapters.TodoListFileRepo
import domain.models.*
import ports.TodoListEventRepo
import ports.TodoListRepo
import java.time.LocalDateTime
import java.util.*

class WriteDomain(val todoListRepo: TodoListRepo, val todoListEventRepo: TodoListEventRepo, val readDomain: ReadDomain) {
    fun createNewTodo(todoName: String): TodoClientView {
        val newTodo = Todo(id = createID(), createdTimestamp = timeStamp(), lastModifiedTimestamp = timeStamp(), name = todoName)

        val newTodoEvent = TodoCreatedEvent(
        eventId = createID(),
        eventCreatedDate = timeStamp(),
        entityId = newTodo.id,
        eventDetails = newTodo
        )
        todoListEventRepo.addEvent(newTodoEvent)
        return todoClientView(newTodo)
    }
    fun updateTodoName(todoId: String, updatedTodoName: String): TodoNameUpdate {

        val updatedNameTodoEvent = TodoNameUpdatedEvent(
            eventId = createID(),
            eventCreatedDate = timeStamp(),
            entityId = todoId,
            eventDetails = TodoNameUpdate(id = todoId, name = updatedTodoName)
        )
        todoListEventRepo.addEvent(updatedNameTodoEvent)
        return updatedNameTodoEvent.eventDetails
    }


    fun markTodoAsDone(todoId: String): TodoStatusUpdate {

        val updatedStatusTodoEvent = TodoStatusUpdatedEvent(
            eventId = createID(),
            eventCreatedDate = timeStamp(),
            entityId = todoId,
            eventDetails = TodoStatusUpdate(id = todoId, status = "DONE")
        )

        todoListEventRepo.addEvent(updatedStatusTodoEvent)
        return updatedStatusTodoEvent.eventDetails
    }

    fun markTodoAsNotDone(todoId: String): TodoStatusUpdate {

        val updatedStatusTodoEvent = TodoStatusUpdatedEvent(
            eventId = createID(),
            eventCreatedDate = timeStamp(),
            entityId = todoId,
            eventDetails = TodoStatusUpdate(id = todoId, status = "NOT_DONE")
        )

        todoListEventRepo.addEvent(updatedStatusTodoEvent)
        return updatedStatusTodoEvent.eventDetails
    }


//    fun markTodoAsDone(todoId: String): String {
//        val todoList: MutableList<Todo> = readDomain.getTodoList("").toMutableList()
//        var nameOfUpdatedTodo: String? = null
//        for (todoItem in todoList) {
//            if (todoItem.id == todoId) {
//                todoItem.status = "DONE"
//                todoItem.lastModifiedDate = LocalDateTime.now().toString()
//                nameOfUpdatedTodo = todoItem.name
//            }
//        }
//        todoListRepo.updateTodoList(todoList)
//        return "The status of your todo '${nameOfUpdatedTodo}' has been updated to 'DONE'."
//    }

//    fun markTodoAsNotDone(todoId: String): String {
//        val todoList: MutableList<Todo> = readDomain.getTodoList("").toMutableList()
//        var nameOfUpdatedTodo: String? = null
//        for (todoItem in todoList) {
//            if (todoItem.id == todoId) {
//                todoItem.status = "NOT_DONE"
//                todoItem.lastModifiedDate = LocalDateTime.now().toString()
//                nameOfUpdatedTodo = todoItem.name
//            }
//        }
//        todoListRepo.updateTodoList(todoList)
//        return "The status of your todo '${nameOfUpdatedTodo}' has been updated to 'NOT_DONE'."
//    }

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
    val readDomain = ReadDomain(todoListEventRepo)
    val writeDomain = WriteDomain(todoListRepo, todoListEventRepo, readDomain)

//println(writeDomain.markTodoAsDone("c94f495c-6f2e-4f4f-96b2-d2342e59e690"))
//println(writeDomain.createNewTodo("wash car"))
//    println(writeDomain.updateTodoName("c94f495c-6f2e-4f4f-96b2-d2342e59e690", "mouse"))

}