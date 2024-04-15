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

    fun getTodoListByStatusDoneClientView(): List<TodoClientView> {
        return getTodoListByStatusDone().map { todo ->
            todoClientView(todo)
        }
    }

    fun getTodoListByStatusNotDoneClientView(): List<TodoClientView> {
        return getTodoListByStatusNotDone().map { todo ->
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

    private fun getTodoListByStatusDone(): List<Todo> {
        return getTodoList().filter { todo ->
            todo.status == Status.DONE
        }
    }

    private fun getTodoListByStatusNotDone(): List<Todo> {
        return getTodoList().filter { todo ->
            todo.status == Status.NOT_DONE
        }
    }

    private fun todoClientView(todo: Todo): TodoClientView {
        return TodoClientView(id = todo.id, name = todo.name, status = todo.status)
    }
}


// where should this be kept if it is used by both readDomain and writeDomain?


