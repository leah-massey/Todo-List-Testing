package domain


import domain.models.*
import ports.TodoListEventRepo

class ReadDomain(val todoListEventRepo: TodoListEventRepo) {

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

    fun getTodoListByStatusClientView(status: String): List<TodoClientView> {
        return getTodoListByStatus(status).map { todo ->
            todoClientView(todo)
        }
    }

    private fun getTodoList(todoId: String = ""): List<Todo> {
        if (todoId == "") {
            return todoListEventRepo.getTodoList()
        } else {
            return todoListEventRepo.getTodoList().filter { todo ->
                todo.id == todoId
            }
        }
    }

    private fun getTodoListByStatus(status: String): List<Todo> {
        return getTodoList().filter { todo ->
            todo.status == status
        }
    }

}


// where should this be kept if it is used by both readDomain and writeDomain?
fun todoClientView(todo: Todo): TodoClientView {
    return TodoClientView(id = todo.id, name = todo.name, status = todo.status)
}

