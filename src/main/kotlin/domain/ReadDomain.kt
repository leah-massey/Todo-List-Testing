package domain

import adapters.TodoListEventFileRepo
import adapters.TodoListFileRepo
import domain.models.*
import ports.TodoListEventRepo
import ports.TodoListRepo

class ReadDomain(val todoListEventRepo: TodoListEventRepo) {

    fun getTodoList(todoId: String = ""): List<Todo> {
        if (todoId == "") {
            return todoListEventRepo.getTodoList()
        } else {
            return todoListEventRepo.getTodoList().filter { todo ->
                todo.id == todoId
            }
        }
    }

    fun getTodoListClientView(todoId: String = ""): List<TodoClientView> {
        if (todoId == "") {
            return getTodoList().map { todo ->
                todoClientView(todo)
            }
        } else {
            return getTodoList().filter { todo ->
                todo.id == todoId
            }.map { todo ->
                todoClientView(todo)
            }
        }
    }

    fun getTodoAfterNameUpdate(todoId: String): TodoNameUpdate {
        val updatedTodo: Todo = getTodoList(todoId).find {todo ->
            todo.id == todoId
        }!!

        return TodoNameUpdate(id=updatedTodo.id, name=updatedTodo.name)
    }

    fun getTodoAfterStatusUpdate(todoId: String): TodoStatusUpdate {
        val updatedTodo: Todo = getTodoList(todoId).find { todo ->
            todo.id == todoId
        }!!
        return TodoStatusUpdate(id = updatedTodo.id, status = updatedTodo.status)
    }


    private fun todoListClientViewByStatus(todo: Todo): TodoClientViewByStatus {
        return TodoClientViewByStatus(id = todo.id, name = todo.name)
    }
}


// getTodoListEventsById

// refactor and make it neater -
//fun getTodoListClientView(todoId: String = ""): List<TodoClientView> {
//    if (todoId == "") {
//
//        // fun getAllTodos()
//        // todoListEventRepo.
//
//        return getTodoListEvents().mapNotNull { todoEvent ->
//            when (todoEvent) {
//                is TodoCreatedEvent -> {
//                    val todo: Todo = todoEvent.eventDetails
//                    todoClientView(todo)
//                }
//
//                else -> null
//            }
//        }
//    } else {
//
//        // getTodosById
//
//        val eventsForChosenId: List<TodoEvent> = getTodoListEvents().filter { event ->
//            event.entityId == todoId
//        }
//        return eventsForChosenId.mapNotNull { todoEvent ->
//            when (todoEvent) {
//                is TodoCreatedEvent -> {
//                    val todo: Todo = todoEvent.eventDetails
//                    todoClientView(todo)
//                }
//
//                else -> null
//            }
//        }
//    }
//
//
//}

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


// where should this be kept if it is used by both readDomain and writeDomain?
fun todoClientView(todo: Todo): TodoClientView {
    return TodoClientView(id = todo.id, name = todo.name, status = todo.status)
}

fun main() {
    val todoListRepo: TodoListRepo = TodoListFileRepo("./src/resources/todo_list.json")
    val todoListEventRepo: TodoListEventRepo = TodoListEventFileRepo("./src/resources/todo_list_event_log.ndjson")
    val readDomain = ReadDomain(todoListEventRepo)
    val writeDomain = WriteDomain(todoListRepo, todoListEventRepo, readDomain)

    println(readDomain.getTodoListClientView())
}
