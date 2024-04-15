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
        return writeDomainTodoClientView(newTodo)
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

//    fun markTodoAsDone(todoId: String): TodoStatusUpdate {
//
//        val updatedStatusTodoEvent = TodoStatusUpdatedEvent(
//            eventId = createID(),
//            eventCreatedDate = timeStamp(),
//            entityId = todoId,
//            eventDetails = TodoStatusUpdate(id = todoId, status = Status.DONE)
//        )
//
//        todoListEventRepo.addEvent(updatedStatusTodoEvent)
//        return updatedStatusTodoEvent.eventDetails
//    }

//    fun markTodoAsNotDone(todoId: String): TodoStatusUpdate {
//
//        val updatedStatusTodoEvent = TodoStatusUpdatedEvent(
//            eventId = createID(),
//            eventCreatedDate = timeStamp(),
//            entityId = todoId,
//            eventDetails = TodoStatusUpdate(id = todoId, status = Status.NOT_DONE)
//        )
//
//        todoListEventRepo.addEvent(updatedStatusTodoEvent)
//        return updatedStatusTodoEvent.eventDetails
//    }

    fun markTodoAsDone(todoId: String): TodoStatusUpdate {
        return updateTodoStatus(Status.DONE, todoId)
    }

    fun markTodoAsNotDone(todoId: String): TodoStatusUpdate {
        return updateTodoStatus(Status.NOT_DONE, todoId)
    }

    private fun updateTodoStatus(status: Status, todoId: String): TodoStatusUpdate {
        val updatedStatusTodoEvent = TodoStatusUpdatedEvent(
            eventId = createID(),
            eventCreatedDate = timeStamp(),
            entityId = todoId,
            eventDetails = TodoStatusUpdate(id = todoId, status = status)
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

    private fun writeDomainTodoClientView(todo: Todo): TodoClientView {
        return TodoClientView(id = todo.id, name = todo.name, status = todo.status)
    }

}