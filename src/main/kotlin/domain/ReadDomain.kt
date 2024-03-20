package domain

import adapters.TodoListEventFileRepo
import adapters.TodoListFileRepo
import domain.models.*
import ports.TodoListEventRepo
import ports.TodoListRepo

class ReadDomain( val todoListEventRepo: TodoListEventRepo) {

    fun getTodoListEvents(): List<TodoEvent> {
        val todoListEvents: List<TodoEvent> = todoListEventRepo.getEvents()
        return todoListEvents
    }

    fun getTodoListEssentials(todoId: String = ""): List<TodoEssentials> {
        val todoListEvents: List<TodoEvent> = getTodoListEvents()

        if (todoId == "") {

            return todoListEvents.mapNotNull { todoEvent ->
                when (todoEvent) {
                    is TodoCreatedEvent -> {
                        val todo: Todo = todoEvent.eventDetails
                        todoEssentials(todo)
                    }

                    else -> null
                }
            }
        } else {
            val eventsForChosenId: List<TodoEvent> = todoListEvents.filter{ event ->
                event.entityId == todoId
            }
            return eventsForChosenId.mapNotNull { todoEvent ->
                when (todoEvent) {
                    is TodoCreatedEvent -> {
                        val todo: Todo = todoEvent.eventDetails
                        todoEssentials(todo)
                    }
                    else -> null
                }
            }
        }


    }

//    fun getTodosByStatus(status: String): List<Todo> {
//        val todoList: List<Todo> = getTodoList("")
//        return todoList.filter { todo ->
//            todo.status == status
//        }
//    }

//    fun getTodoListEssentialsByStatus(status: String): List<TodoEssentialsByStatus> {
//        return getTodosByStatus(status).map{todo ->
//            todoEssentialsByStatus(todo)
//        }
//    }



    private fun todoEssentialsByStatus(todo: Todo): TodoEssentialsByStatus {
        return TodoEssentialsByStatus(id = todo.id, name = todo.name)
    }
}

// where should this be kept if it is used by both readDomain and writeDomain?
fun todoEssentials(todo: Todo): TodoEssentials {
    return TodoEssentials(id = todo.id, name = todo.name, status = todo.status)
}

fun main() {
    val todoListRepo: TodoListRepo = TodoListFileRepo("./src/resources/todo_list.json")
    val todoListEventRepo: TodoListEventRepo = TodoListEventFileRepo("./src/resources/todo_list_event_log.ndjson")
    val readDomain = ReadDomain(todoListEventRepo)
    val writeDomain = WriteDomain(todoListRepo, todoListEventRepo, readDomain)

    println(readDomain.getTodoListEssentials("f90a4ef5-2953-4506-a0aa-38cf967c4c6d"))
}
