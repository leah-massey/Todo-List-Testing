package domain

import adapters.TodoListEventFileRepo
import adapters.TodoListFileRepo
import domain.models.TodoEssentials
import domain.models.Todo
import domain.models.TodoEssentialsByStatus
import domain.models.TodoEvent
import ports.TodoListEventRepo
import ports.TodoListRepo

class ReadDomain(val todoListRepo: TodoListRepo, val todoListEventRepo: TodoListEventRepo) {

    fun getTodoListEssentials(todoId: String = ""): List<TodoEssentials> {
        return getTodoList(todoId).map{todo ->
            todoEssentials(todo)
        }
    }

    fun getTodoListEssentialsByStatus(status: String): List<TodoEssentialsByStatus> {
        return getTodosByStatus(status).map{todo ->
            todoEssentialsByStatus(todo)
        }
    }

    fun getTodosByStatus(status: String): List<Todo> {
        val todoList: List<Todo> = getTodoList("")
        return todoList.filter { todo ->
            todo.status == status
        }
    }

    fun getTodoList(todoId: String): List<Todo>  {
        val todoList: List<Todo> = todoListRepo.getTodos()

        if (todoId == "") {
            return todoList
        } else {
            return todoList.filter {
                it.id == todoId }
        }
    }

    fun getTodoListEvents(): List<TodoEvent> {
        val todoListEvents: List<TodoEvent> = todoListEventRepo.getEvents()
        return todoListEvents
    }

    private fun todoEssentials(todo: Todo): TodoEssentials {
        return TodoEssentials(id = todo.id, name = todo.name, status = todo.status)
    }

    private fun todoEssentialsByStatus(todo: Todo): TodoEssentialsByStatus {
        return TodoEssentialsByStatus(id = todo.id, name = todo.name)
    }

}

fun main() {
    val todoListRepo: TodoListRepo = TodoListFileRepo("./src/resources/todo_list.json")
    val todoListEventRepo: TodoListEventRepo = TodoListEventFileRepo("./src/resources/todo_list_event_log.ndjson")
    val readDomain = ReadDomain(todoListRepo, todoListEventRepo)
    val writeDomain = WriteDomain(todoListRepo, todoListEventRepo, readDomain)


    println(readDomain.getTodoListEvents())
}
