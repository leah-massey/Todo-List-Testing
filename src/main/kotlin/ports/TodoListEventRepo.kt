package ports

import domain.models.TodoEvent

interface TodoListEventRepo {
    fun addEvent(event: TodoEvent)

    fun getEvents(): List<TodoEvent>
}