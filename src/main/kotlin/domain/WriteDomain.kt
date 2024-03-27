package domain

import domain.models.*
import ports.TodoListEventRepo
import java.time.LocalDateTime
import java.util.*

class WriteDomain(
    val todoListEventRepo: TodoListEventRepo,
) {
    fun createNewTodo(todoName: String): TodoClientView {
        val newTodo =
            Todo(id = createID(), createdTimestamp = timeStamp(), lastModifiedTimestamp = timeStamp(), name = todoName)

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

    private fun createID(): String {
        return UUID.randomUUID().toString()
    }

    private fun timeStamp(): String {
        return LocalDateTime.now().toString()
    }

}