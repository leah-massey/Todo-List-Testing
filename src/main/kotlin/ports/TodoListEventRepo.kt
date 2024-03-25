package ports

import domain.models.Todo
import domain.models.TodoEvent

interface TodoListEventRepo {
    fun addEvent(event: TodoEvent)

    fun getEvents(): List<TodoEvent>

    fun getTodoList(): List<Todo>

}